package com.wanblog.presenter.impl

import com.wanblog.base.RxPresenter
import com.wanblog.model.bean.*
import com.wanblog.model.http.ApiManager
import com.wanblog.model.http.ApiSettings
import com.wanblog.model.http.CommonSubscriber
import com.wanblog.presenter.contract.BlogContract
import com.wanblog.util.RequsetUtil
import com.wanblog.util.RxUtil
import org.json.JSONObject
import javax.inject.Inject

class BlogPresenter @Inject constructor() : RxPresenter<BlogContract.View>(),
    BlogContract.Presenter<BlogContract.View> {

    override fun blogDetail(blogId: Long) {
        addSubscribe(
            ApiManager.getBlogDetail(blogId)
                .compose(RxUtil.rxSchedulerHelper<MyHttpResponse<BlogBean>>())
                .compose(RxUtil.handleResult())
                .subscribeWith(object :
                    CommonSubscriber<BlogBean>(
                        mView,
                        isShowViewLoading = false,
                        isShowDialogLoading = true,
                        url = ApiSettings.login
                    ) {
                    override fun onNext(data: BlogBean) {
                        mView?.onBlogResult(data)
                    }
                })
        )
    }

    override fun blogEdit(blog: EditBlogBean) {
        val jsonObject = JSONObject()
        jsonObject.put("id", blog.id)
        jsonObject.put("title", blog.title)
        jsonObject.put("description", blog.description)
        jsonObject.put("content", blog.content)
        val body = RequsetUtil.getRequestBody(jsonObject)
        addSubscribe(
            ApiManager.blogEdit(body)
                .compose(RxUtil.rxSchedulerHelper<MyHttpResponse<Any>>())
                .compose(RxUtil.handleResult())
                .subscribeWith(object :
                    CommonSubscriber<Any>(
                        mView,
                        isShowViewLoading = false,
                        isShowDialogLoading = true
                    ) {
                    override fun onNext(data: Any) {
                        mView?.onBlogEditResult()
                    }
                })
        )

    }

    override fun blogPublish(blog: PublishBlogBean) {
        val jsonObject = JSONObject()
        jsonObject.put("title", blog.title)
        jsonObject.put("description", blog.description)
        jsonObject.put("content", blog.content)
        val body = RequsetUtil.getRequestBody(jsonObject)
        addSubscribe(
            ApiManager.blogPublish(body)
                .compose(RxUtil.rxSchedulerHelper<MyHttpResponse<Any>>())
                .compose(RxUtil.handleResult())
                .subscribeWith(object :
                    CommonSubscriber<Any>(
                        mView,
                        isShowViewLoading = false,
                        isShowDialogLoading = true
                    ) {
                    override fun onNext(data: Any) {
                        mView?.onBlogPublishResult()
                    }
                })
        )
    }

    override fun blogDelete(blog: DeleteBlogBean) {
        val jsonObject = JSONObject()
        jsonObject.put("id", blog.id)
        val body = RequsetUtil.getRequestBody(jsonObject)
        addSubscribe(
            ApiManager.blogDelete(body)
                .compose(RxUtil.rxSchedulerHelper<MyHttpResponse<Any>>())
                .compose(RxUtil.handleResult())
                .subscribeWith(object :
                    CommonSubscriber<Any>(
                        mView,
                        isShowViewLoading = false,
                        isShowDialogLoading = true
                    ) {
                    override fun onNext(data: Any) {
                        mView?.onBlogDeleteResult()
                    }
                })
        )
    }

}