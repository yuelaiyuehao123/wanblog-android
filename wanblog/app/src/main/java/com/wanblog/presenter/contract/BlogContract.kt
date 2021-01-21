package com.wanblog.presenter.contract

import com.wanblog.base.BaseContract
import com.wanblog.model.bean.BlogBean
import com.wanblog.model.bean.LoginBean
import com.wanblog.model.bean.LoginResultBean


interface BlogContract {

    interface View : BaseContract.BaseView {
        fun onBlogResult(data: BlogBean)
        fun onEditBlogResult()
    }

    interface Presenter<in T> : BaseContract.BasePresenter<T> {
        fun blogDetail(blogId: Int)
        fun editBlog(blog: BlogBean)
    }

}