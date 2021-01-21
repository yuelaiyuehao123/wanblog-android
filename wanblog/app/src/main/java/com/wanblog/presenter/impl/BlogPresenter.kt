package com.wanblog.presenter.impl

import com.wanblog.base.RxPresenter
import com.wanblog.model.bean.BlogBean
import com.wanblog.model.bean.MyHttpResponse
import com.wanblog.model.http.ApiManager
import com.wanblog.model.http.ApiSettings
import com.wanblog.model.http.CommonSubscriber
import com.wanblog.presenter.contract.BlogContract
import com.wanblog.util.RxUtil
import javax.inject.Inject

class BlogPresenter @Inject constructor() : RxPresenter<BlogContract.View>(),
    BlogContract.Presenter<BlogContract.View> {

    override fun blogDetail(blogId: Int) {
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

    override fun editBlog(blog: BlogBean) {

    }

}