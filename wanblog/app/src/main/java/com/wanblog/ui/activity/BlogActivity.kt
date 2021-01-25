package com.wanblog.ui.activity

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import com.hjq.bar.OnTitleBarListener
import com.wanblog.R
import com.wanblog.base.BaseActivity
import com.wanblog.model.bean.BlogBean
import com.wanblog.model.bean.EditBlogBean
import com.wanblog.model.bean.PublishBlogBean
import com.wanblog.presenter.contract.BlogContract
import com.wanblog.presenter.impl.BlogPresenter
import com.wanblog.util.UserUtil
import kotlinx.android.synthetic.main.activity_blog.*

/**
 * 博客详情页面，会有三种状态:
 *  1.新创建博客
 *    - 显示发布按钮
 *  2.查看自己发表的博客
 *    - 支持修改
 *    - 显示发布按钮
 *  3.查看别人发表的博客
 *    - 不支持修改
 *    - 隐藏发布按钮
 */
class BlogActivity : BaseActivity<BlogPresenter>(), BlogContract.View {

    private var mBlogId: Long = 0
    private var mBlogUserId: Long = 0
    private var mIsNewBlog: Boolean = false
    private var mBlogBean: BlogBean? = null

    companion object {
        const val blog_is_new_key: String = "blog_is_new_key"
        const val blog_id_key: String = "blog_id_key"
        const val blog_user_id_key: String = "blog_user_id_key"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        mIsNewBlog = intent.getBooleanExtra(blog_is_new_key, false)
        mBlogId = intent.getLongExtra(blog_id_key, 0)
        mBlogUserId = intent.getLongExtra(blog_user_id_key, 0)

        Log.d("abc", "get--mBlogId-->" + mBlogId)
        Log.d("abc", "get--blog.userId-->" + mBlogUserId)
        Log.d("abc", "get--mIsNewBlog-->" + mIsNewBlog)


        super.onCreate(savedInstanceState)
    }

    override fun getLayout(): Int = R.layout.activity_blog

    override fun initInject() {
        activityComponent.inject(this)
    }

    override fun initPresenter() {
        mPresenter.attachView(this)
    }

    override fun initView() {

        checkStatus();

        ib_blog_back.setOnClickListener {
            finish()
        }

        ib_blog_publish.setOnClickListener {
            publishBlog()
        }

    }

    /**
     * 根据intent传进来的值，显示不同的状态
     */
    private fun checkStatus() {

        if (mIsNewBlog) {
            // 新建博客
            et_blog_title.hint = "点击此处编辑标题"
            et_blog.isEnabled = true
            ib_blog_publish.visibility = View.VISIBLE
            ib_blog_publish.setImageDrawable(resources.getDrawable(R.drawable.ic_publish))
        } else {
            val userId = UserUtil.getUserId(mActivity)
            if (mBlogUserId == userId) {
                // 查看自己发布的博客
                et_blog.isEnabled = true
                et_blog_title.isEnabled = true
                ib_blog_publish.visibility = View.VISIBLE
                ib_blog_publish.setImageDrawable(resources.getDrawable(R.drawable.ic_publish))
            } else {
                // 查看别人发布的博客
                et_blog.isEnabled = false
                et_blog_title.isEnabled = false
                ib_blog_publish.visibility = View.GONE
            }
        }

    }

    override fun initData() {
        if (!mIsNewBlog) {
            mPresenter.blogDetail(mBlogId)
        }
    }

    override fun showProgress() {
    }

    override fun showError(url: String, msg: String, code: Int) {
    }

    override fun onBlogResult(data: BlogBean) {
        mBlogBean = data
        et_blog_title.setText(data.title)
        et_blog.setText(data.content)
    }

    override fun onBlogEditResult() {
        finish()
    }

    override fun onBlogPublishResult() {
        finish()
    }

    override fun onBlogDeleteResult() {
        finish()
    }

    /**
     * 发布博客
     */
    private fun publishBlog() {
        val title = et_blog_title.text.toString()
        val description = et_blog.text.toString()
        val content = et_blog.text.toString()

        if (title.isEmpty()) {
            Toast.makeText(mActivity, "标题不能为空", Toast.LENGTH_SHORT).show()
            return
        }
        if (description.isEmpty()) {
            Toast.makeText(mActivity, "描述不能为空", Toast.LENGTH_SHORT).show()
            return
        }
        if (content.isEmpty()) {
            Toast.makeText(mActivity, "内容不能为空", Toast.LENGTH_SHORT).show()
            return
        }

        if (mIsNewBlog) {
            val bean = PublishBlogBean(title, description, content)
            mPresenter.blogPublish(bean)
        } else {
            val bean = EditBlogBean(mBlogBean!!.bid, title, description, content)
            mPresenter.blogEdit(bean)
        }

    }

}
