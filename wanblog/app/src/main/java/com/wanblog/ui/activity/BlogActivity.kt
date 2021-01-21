package com.wanblog.ui.activity

import android.view.View
import com.hjq.bar.OnTitleBarListener
import com.wanblog.R
import com.wanblog.base.BaseActivity
import com.wanblog.model.bean.BlogBean
import com.wanblog.presenter.contract.BlogContract
import com.wanblog.presenter.impl.BlogPresenter
import kotlinx.android.synthetic.main.activity_blog.*

class BlogActivity : BaseActivity<BlogPresenter>(), BlogContract.View {

    private var mBlogId: Long = 0

    companion object {
        const val blog_id_key: String = "blog_id_key"
    }

    override fun getLayout(): Int = R.layout.activity_blog

    override fun initInject() {
        activityComponent.inject(this)
    }

    override fun initPresenter() {
        mPresenter.attachView(this)
    }

    override fun initView() {

        title_bar_blog.setOnTitleBarListener(object : OnTitleBarListener {
            override fun onLeftClick(v: View?) {
                finish()
            }

            override fun onTitleClick(v: View?) {
            }

            override fun onRightClick(v: View?) {
            }

        })
    }

    override fun initData() {
        mBlogId = intent.getLongExtra(blog_id_key, 0)
        mPresenter.blogDetail(mBlogId)
    }

    override fun showProgress() {
    }

    override fun showError(url: String, msg: String, code: Int) {
    }

    override fun onBlogResult(data: BlogBean) {
        title_bar_blog.title = data.title
        tv_blog.text = data.content
    }

    override fun onEditBlogResult() {
    }

}
