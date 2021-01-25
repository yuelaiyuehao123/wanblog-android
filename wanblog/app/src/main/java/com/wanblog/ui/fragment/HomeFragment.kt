package com.wanblog.ui.fragment

import android.os.Bundle
import android.widget.LinearLayout
import android.widget.Toast
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import androidx.recyclerview.widget.RecyclerView.RecycledViewPool
import com.alibaba.android.vlayout.DelegateAdapter
import com.alibaba.android.vlayout.VirtualLayoutManager
import com.alibaba.android.vlayout.layout.LinearLayoutHelper
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.RequestOptions
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.jakewharton.rxbinding4.view.clicks
import com.wanblog.R
import com.wanblog.base.BaseFragment
import com.wanblog.model.bean.BlogBean
import com.wanblog.model.http.ApiCode
import com.wanblog.presenter.contract.HomeContract
import com.wanblog.presenter.impl.HomePresenter
import com.wanblog.ui.activity.BlogActivity
import com.wanblog.ui.adapter.BaseDelegateAdapter
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.android.synthetic.main.fragment_home_page.refreshLayout_home_page
import kotlinx.android.synthetic.main.fragment_home_page.rv_home_page
import kotlinx.android.synthetic.main.fragment_home_page.status_view_home_page
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.support.v4.startActivity
import org.jetbrains.anko.uiThread
import java.util.concurrent.TimeUnit

class HomeFragment : BaseFragment<HomePresenter>(), HomeContract.View {

    //vLayout
    val VLAYOUT_LIST = 1           //普通列表

    //总适配器
    var mDelegateAdapter: DelegateAdapter? = null

    // 存放各个模块的适配器
    private var mAdapters: MutableList<DelegateAdapter.Adapter<*>> = mutableListOf()

    private var mBlogList: MutableList<BlogBean> = mutableListOf()

    companion object {
        fun newInstance(): HomeFragment {
            val bundle = Bundle()
            val fragment = HomeFragment()
            fragment.arguments = bundle
            return fragment
        }
    }

    override fun getLayout(): Int = R.layout.fragment_home

    override fun initView() {
        initRecyclerView()

        fab_home.setOnClickListener {
            startActivity<BlogActivity>(
                BlogActivity.blog_is_new_key to true
            )
        }

        status_view_home_page.setOnClickListener {
            initData()
        }
    }

    private fun initRecyclerView() {

        //初始化
        //创建VirtualLayoutManager对象
        val layoutManager = VirtualLayoutManager(activity!!)
        rv_home_page.layoutManager = layoutManager

        //设置回收复用池大小，（如果一屏内相同类型的 View 个数比较多，需要设置一个合适的大小，防止来回滚动时重新创建 View）
        val viewPool = RecycledViewPool()
        viewPool.setMaxRecycledViews(0, 20)
        rv_home_page.setRecycledViewPool(viewPool)

        //设置适配器
        mDelegateAdapter = DelegateAdapter(layoutManager, true)
        rv_home_page.adapter = mDelegateAdapter

        refreshLayout_home_page.setEnableLoadMore(true)
        refreshLayout_home_page.setEnableRefresh(true)
        refreshLayout_home_page.setOnRefreshListener {
            mPresenter.getBlogList(true)
        }

        refreshLayout_home_page.setOnLoadMoreListener {
            mPresenter.getBlogList(false)
        }
    }

    override fun initData() {
        refreshLayout_home_page.autoRefresh()
    }

    override fun initInject() {
        fragmentComponent.inject(this)
    }

    override fun initPresenter() {
        mPresenter.attachView(this)
    }

    override fun showProgress() {
    }

    override fun showError(url: String, msg: String, code: Int) {
        if (code == ApiCode.NET_ERROR) {
            status_view_home_page.showNetError()
        } else {
            status_view_home_page.showDataError()
        }
        refreshLayout_home_page.finishRefresh()
        refreshLayout_home_page.finishLoadMore()
    }

    override fun onBlogListResult(isRefresh: Boolean, blogList: MutableList<BlogBean>) {

        if (isRefresh) {
            doAsync {
                Thread.sleep(1000)
                uiThread {
                    fab_home.show(true)
                }
            }
        }

        if (blogList.isEmpty()) {
            if (isRefresh) {
                status_view_home_page.showDataEmpty()
            } else {
                Toast.makeText(mContext, "没有更多数据了", Toast.LENGTH_LONG).show()
            }
        } else {
            if (isRefresh) {
                mBlogList.clear()
                status_view_home_page.hide()
            }
            mBlogList.addAll(blogList)
            mAdapters.clear()
            val adapter = initListAdapter(mBlogList)
            mAdapters.add(adapter)
            mDelegateAdapter!!.setAdapters(mAdapters)
            mDelegateAdapter!!.notifyDataSetChanged()
        }
        refreshLayout_home_page.finishRefresh()
        refreshLayout_home_page.finishLoadMore()
    }

    /**
     * 普通列表item
     */
    private fun initListAdapter(blogList: MutableList<BlogBean>): BaseDelegateAdapter {
        return object : BaseDelegateAdapter(
            mContext,
            LinearLayoutHelper(),
            R.layout.item_home_list,
            blogList.size,
            VLAYOUT_LIST
        ) {
            override fun onBindViewHolder(holder: BaseViewHolder, position: Int) {
                super.onBindViewHolder(holder, position)
                val blog = blogList[position]
                val ll_home_list_item = holder.getView<LinearLayout>(R.id.ll_home_list_item)
                val tv_home_list_item_title = holder.getView<AppCompatTextView>(R.id.tv_home_list_item_title)
                val tv_home_list_item_description = holder.getView<AppCompatTextView>(R.id.tv_home_list_item_description)
                val iv_home_list_item_avatar = holder.getView<AppCompatImageView>(R.id.iv_home_list_item_avatar)
                val tv_home_list_item_username = holder.getView<AppCompatTextView>(R.id.tv_home_list_item_username)

                Glide.with(mContext!!)
                    .load(blog.avatar)
                    .apply(RequestOptions.bitmapTransform(CircleCrop()))
                    .transition(DrawableTransitionOptions.withCrossFade(500))
                    .into(iv_home_list_item_avatar)

                tv_home_list_item_title.text = blog.title
                tv_home_list_item_description.text = blog.description
                tv_home_list_item_username.text = blog.username

                //1秒钟之内禁用重复点击
                ll_home_list_item.clicks()
                    .throttleFirst(1, TimeUnit.SECONDS)
                    .subscribe {
                        startActivity<BlogActivity>(
                            BlogActivity.blog_is_new_key to false,
                            BlogActivity.blog_id_key to blog.bid,
                            BlogActivity.blog_user_id_key to blog.uid
                        )
                    }
            }
        }
    }

}