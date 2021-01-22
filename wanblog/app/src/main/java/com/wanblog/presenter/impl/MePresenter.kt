package com.wanblog.presenter.impl

import com.wanblog.base.RxPresenter
import com.wanblog.model.bean.MyHttpResponse
import com.wanblog.model.http.ApiManager
import com.wanblog.model.http.CommonSubscriber
import com.wanblog.presenter.contract.MeContract
import com.wanblog.util.RxUtil
import javax.inject.Inject

class MePresenter @Inject constructor() : RxPresenter<MeContract.View>(),
    MeContract.Presenter<MeContract.View> {

    override fun logout() {
        addSubscribe(
            ApiManager.logout()
                .compose(RxUtil.rxSchedulerHelper<MyHttpResponse<Any>>())
                .compose(RxUtil.handleResult())
                .subscribeWith(object :
                    CommonSubscriber<Any>(
                        mView,
                        isShowViewLoading = false,
                        isShowDialogLoading = true
                    ) {
                    override fun onNext(data: Any) {
                        mView?.onLogoutResult()
                    }
                })
        )
    }


}