package com.wanblog.presenter.contract

import com.wanblog.base.BaseContract
import com.wanblog.model.bean.BlogBean
import com.wanblog.model.bean.Top3Bean


interface HomeContract {

    interface View : BaseContract.BaseView {
        fun onBlogListResult(isRefresh: Boolean, blogList: MutableList<BlogBean>)
        fun onTop3ListResult(top3List: MutableList<Top3Bean>)
    }

    interface Presenter<in T> : BaseContract.BasePresenter<T> {
        fun getBlogList(isRefresh: Boolean)
        fun getTop3List()
    }

}