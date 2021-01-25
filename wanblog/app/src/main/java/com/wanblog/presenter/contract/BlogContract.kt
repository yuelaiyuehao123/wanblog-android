package com.wanblog.presenter.contract

import com.wanblog.base.BaseContract
import com.wanblog.model.bean.*


interface BlogContract {

    interface View : BaseContract.BaseView {
        fun onBlogResult(data: BlogBean)
        fun onBlogEditResult()
        fun onBlogPublishResult()
        fun onBlogDeleteResult()
    }

    interface Presenter<in T> : BaseContract.BasePresenter<T> {
        fun blogDetail(bid: Long)
        fun blogEdit(blog: EditBlogBean)
        fun blogPublish(blog: PublishBlogBean)
        fun blogDelete(blog: DeleteBlogBean)
    }

}