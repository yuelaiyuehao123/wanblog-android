package com.wanblog.presenter.impl

import com.wanblog.base.RxPresenter
import com.wanblog.model.bean.BlogBean
import com.wanblog.model.bean.MyHttpResponse
import com.wanblog.model.http.ApiManager
import com.wanblog.model.http.ApiSettings
import com.wanblog.model.http.CommonSubscriber
import com.wanblog.presenter.contract.HomeContract
import com.wanblog.util.RxUtil
import javax.inject.Inject

class HomePresenter @Inject constructor() : RxPresenter<HomeContract.View>(),
    HomeContract.Presenter<HomeContract.View> {

    private val mSize = 10
    private var mCurrentPage = 1

    override fun getBlogList(isRefresh: Boolean) {

        if (isRefresh) {
            mCurrentPage = 1;
        } else {
            mCurrentPage++;
        }

        addSubscribe(
            ApiManager.getBlogList(mCurrentPage, mSize)
                .compose(RxUtil.rxSchedulerHelper<MyHttpResponse<MutableList<BlogBean>>>())
                .compose(RxUtil.handleResult())
                .subscribeWith(object :
                    CommonSubscriber<MutableList<BlogBean>>(
                        mView,
                        isShowViewLoading = true,
                        isShowDialogLoading = false,
                        url = ApiSettings.blog_list
                    ) {
                    override fun onNext(data: MutableList<BlogBean>) {
                        mView?.onBlogListResult(isRefresh, data)
                    }
                })
        )
    }

}