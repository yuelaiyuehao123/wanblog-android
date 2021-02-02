package com.wanblog.ui.activity

import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.wanblog.R
import com.wanblog.base.App
import com.wanblog.base.BaseActivity
import com.wanblog.model.bean.BlogBean
import com.wanblog.model.bean.DeleteBlogBean
import com.wanblog.model.bean.EditBlogBean
import com.wanblog.model.bean.PublishBlogBean
import com.wanblog.presenter.contract.BlogContract
import com.wanblog.presenter.impl.BlogPresenter
import com.wanblog.util.UserUtil
import kotlinx.android.synthetic.main.activity_blog.*

class BlogActivity : BaseActivity<BlogPresenter>(), BlogContract.View {

    private var mBlogId: Long = 0
    private var mBlogUserId: Long = 0
    private var mIsNewBlog: Boolean = false
    private var mBlogStatus: BlogStatus = BlogStatus.READ
    private var mBlogBean: BlogBean? = null

    /**
     * 当前页面的状态
     * READ(只读):
     *      1.查看别人发布的博客
     *      2.隐藏发布按钮
     *      3.隐藏删除按钮
     * EDIT(编辑):
     *      1.用markdown语法编辑中的状态
     *      2.显示发布按钮
     *      3.显示删除按钮
     * PREVIEW(预览):
     *      1.显示markdown格式
     *      2.显示发布按钮
     *      3.显示删除按钮
     *
     * 注意：1.只读状态不能切换到编辑状态或预览状态
     *      2.编辑状态和预览状态可以互相切换
     */
    enum class BlogStatus {
        READ, PREVIEW, EDIT
    }

    companion object {
        const val blog_is_new_key: String = "blog_is_new_key"
        const val blog_id_key: String = "blog_id_key"
        const val blog_user_id_key: String = "blog_user_id_key"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        initStatus()
        super.onCreate(savedInstanceState)
    }

    override fun getLayout(): Int = R.layout.activity_blog

    override fun initInject() {
        activityComponent.inject(this)
    }

    override fun initPresenter() {
        mPresenter.attachView(this)
    }

    /**
     * 初始化当前页面状态
     * 1.新建立博客进入编辑状态
     * 2.查看别人博客进入只读状态
     * 3.查看自己博客进入预览状态
     */
    private fun initStatus() {
        mIsNewBlog = intent.getBooleanExtra(blog_is_new_key, false)
        mBlogId = intent.getLongExtra(blog_id_key, 0)
        mBlogUserId = intent.getLongExtra(blog_user_id_key, 0)
        if (mIsNewBlog) {
            mBlogStatus = BlogStatus.EDIT
        } else {
            val userId = UserUtil.getUserId(mActivity)
            if (mBlogUserId == userId) {
                mBlogStatus = BlogStatus.EDIT
            } else {
                mBlogStatus = BlogStatus.PREVIEW
            }
        }
    }

    /**
     * 根据不同状态展示不同的ui
     */
    private fun checkStatus() {
        when (mBlogStatus) {
            BlogStatus.READ -> {
                ib_blog_publish.visibility = View.GONE
                ib_blog_delete.visibility = View.GONE
                ib_blog_edit.visibility = View.GONE
                ib_blog_preview.visibility = View.GONE
                tv_blog_title.visibility = View.VISIBLE
                et_blog_title.visibility = View.GONE
                tv_blog_content.visibility = View.VISIBLE
                et_blog_content.visibility = View.GONE
            }
            BlogStatus.PREVIEW -> {
                ib_blog_publish.visibility = View.VISIBLE
                ib_blog_delete.visibility = View.VISIBLE
                ib_blog_edit.visibility = View.VISIBLE
                ib_blog_preview.visibility = View.GONE

                tv_blog_title.visibility = View.VISIBLE
                et_blog_title.visibility = View.GONE

                tv_blog_content.visibility = View.VISIBLE
                et_blog_content.visibility = View.GONE

                tv_blog_title.text = et_blog_title.text
                tv_blog_content.text = et_blog_content.text

            }
            BlogStatus.EDIT -> {
                et_blog_title.hint = "点击此处编辑标题"
                ib_blog_publish.visibility = View.VISIBLE
                ib_blog_delete.visibility = View.VISIBLE
                ib_blog_edit.visibility = View.GONE
                ib_blog_preview.visibility = View.VISIBLE
                tv_blog_title.visibility = View.GONE
                et_blog_title.visibility = View.VISIBLE
                tv_blog_content.visibility = View.GONE
                et_blog_content.visibility = View.VISIBLE
            }

        }
    }

    override fun initView() {
        ib_blog_edit.setOnClickListener {
            mBlogStatus = BlogStatus.EDIT
            checkStatus()
        }
        ib_blog_preview.setOnClickListener {
            mBlogStatus = BlogStatus.PREVIEW
            checkStatus()
        }
        ib_blog_back.setOnClickListener {
            finish()
        }
        ib_blog_publish.setOnClickListener {
            publishBlog()
        }
        ib_blog_delete.setOnClickListener {
            deleteBlog()
        }
    }

    override fun initData() {
        if (mIsNewBlog) {
            checkStatus()
        } else {
            mPresenter.blogDetail(mBlogId)
        }
    }

    override fun showProgress() {
    }

    override fun showError(url: String, msg: String, code: Int) {
    }

    override fun onBlogResult(data: BlogBean) {
        mBlogBean = data
        tv_blog_title.text = data.title
        et_blog_title.setText(data.title)
        tv_blog_content.text = data.content
        et_blog_content.setText(data.content)
        checkStatus()
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
        val description = et_blog_title.text.toString()
        val content = et_blog_content.text.toString()

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
        App.mIsRefresh = true
    }

    private fun deleteBlog() {
        val bean = DeleteBlogBean(mBlogBean!!.bid)
        mPresenter.blogDelete(bean)
        App.mIsRefresh = true
    }

}
