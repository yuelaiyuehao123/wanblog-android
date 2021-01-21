package com.wanblog.ui.activity

import com.wanblog.R
import com.wanblog.base.BaseActivity
import com.wanblog.model.bean.BlogBean
import com.wanblog.presenter.contract.BlogContract
import com.wanblog.presenter.impl.BlogPresenter
import kotlinx.android.synthetic.main.activity_blog.*

class BlogActivity : BaseActivity<BlogPresenter>(), BlogContract.View {

    override fun getLayout(): Int = R.layout.activity_blog

    override fun initInject() {
        activityComponent.inject(this)
    }

    override fun initPresenter() {
        mPresenter.attachView(this)
    }

    override fun initView() {
        bt_blog.setOnClickListener {
            mPresenter.blogDetail(1)
        }
    }

    override fun initData() {
    }

    override fun showProgress() {
    }

    override fun showError(url: String, msg: String, code: Int) {
    }

    override fun onBlogResult(data: BlogBean) {
    }

    override fun onEditBlogResult() {
    }

}
