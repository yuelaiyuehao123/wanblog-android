package com.wanblog.presenter.impl

import com.wanblog.base.RxPresenter
import com.wanblog.model.bean.MyHttpResponse
import com.wanblog.model.bean.SignUpBean
import com.wanblog.model.http.ApiManager
import com.wanblog.model.http.ApiSettings
import com.wanblog.model.http.CommonSubscriber
import com.wanblog.util.RequsetUtil
import com.wanblog.util.RxUtil
import com.wanblog.presenter.contract.SignUpContract
import org.json.JSONObject
import javax.inject.Inject

class SignUpPresenter @Inject constructor() : RxPresenter<SignUpContract.View>(),
    SignUpContract.Presenter<SignUpContract.View> {

    override fun signUp(data: SignUpBean) {

        val jsonObject = JSONObject()
        jsonObject.put("username", data.username)
        jsonObject.put("password", data.password)
        val body = RequsetUtil.getRequestBody(jsonObject)
        addSubscribe(
            ApiManager.signUp(body)
                .compose(RxUtil.rxSchedulerHelper<MyHttpResponse<Any>>())
                .compose(RxUtil.handleResult())
                .subscribeWith(object :
                    CommonSubscriber<Any>(
                        mView,
                        isShowViewLoading = false,
                        isShowDialogLoading = true,
                        url = ApiSettings.signUp
                    ) {
                    override fun onNext(data: Any) {
                        mView?.onSignUpResult()
                    }
                })
        )

    }


}