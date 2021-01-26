package com.wanblog.presenter.impl

import com.wanblog.base.RxPresenter
import com.wanblog.model.bean.BlogBean
import com.wanblog.model.bean.MyHttpResponse
import com.wanblog.model.bean.Top3Bean
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

    override fun getTop3List() {
        ApiManager.getTop3List()
            .compose(RxUtil.rxSchedulerHelper<MyHttpResponse<MutableList<Top3Bean>>>())
            .compose(RxUtil.handleResult())
            .subscribeWith(object :
                CommonSubscriber<MutableList<Top3Bean>>(
                    mView,
                    isShowViewLoading = false,
                    isShowDialogLoading = false,
                    url = ApiSettings.blog_top3_list
                ) {
                override fun onNext(data: MutableList<Top3Bean>) {
                    mView?.onTop3ListResult(data)
                }
            })
    }

}