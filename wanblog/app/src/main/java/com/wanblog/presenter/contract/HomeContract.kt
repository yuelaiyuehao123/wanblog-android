package com.wanblog.presenter.contract

import com.wanblog.base.BaseContract
import com.wanblog.model.bean.BlogBean


interface HomeContract {

    interface View : BaseContract.BaseView {
        fun onBlogListResult(isRefresh: Boolean, blogList: MutableList<BlogBean>)
    }

    interface Presenter<in T> : BaseContract.BasePresenter<T> {
        fun getBlogList(isRefresh: Boolean)
    }

}