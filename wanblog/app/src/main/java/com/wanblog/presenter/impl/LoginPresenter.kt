package com.wanblog.presenter.impl

import com.wanblog.base.RxPresenter
import com.wanblog.model.bean.LoginBean
import com.wanblog.model.bean.LoginResultBean
import com.wanblog.model.bean.MyHttpResponse
import com.wanblog.model.http.ApiManager
import com.wanblog.model.http.ApiSettings
import com.wanblog.model.http.CommonSubscriber
import com.wanblog.util.RequsetUtil
import com.wanblog.util.RxUtil
import com.wanblog.presenter.contract.LoginContract
import org.json.JSONObject
import javax.inject.Inject

class LoginPresenter @Inject constructor() : RxPresenter<LoginContract.View>(),

    LoginContract.Presenter<LoginContract.View> {

    override fun login(data: LoginBean) {
        val jsonObject = JSONObject()
        jsonObject.put("username", data.username)
        jsonObject.put("password", data.password)
        val body = RequsetUtil.getRequestBody(jsonObject)
        addSubscribe(
            ApiManager.login(body)
                .compose(RxUtil.rxSchedulerHelper<MyHttpResponse<LoginResultBean>>())
                .compose(RxUtil.handleResult())
                .subscribeWith(object :
                    CommonSubscriber<LoginResultBean>(
                        mView,
                        isShowViewLoading = false,
                        isShowDialogLoading = true,
                        url = ApiSettings.login
                    ) {
                    override fun onNext(data: LoginResultBean) {
                        mView?.onLoginInResult(data)
                    }
                })
        )
    }


}