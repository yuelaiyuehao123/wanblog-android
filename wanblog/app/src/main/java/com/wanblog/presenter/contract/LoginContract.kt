package com.wanblog.presenter.contract

import com.wanblog.base.BaseContract
import com.wanblog.model.bean.LoginBean
import com.wanblog.model.bean.LoginResultBean


interface LoginContract {

    interface View : BaseContract.BaseView {
        fun onLoginInResult(data: LoginResultBean)
    }

    interface Presenter<in T> : BaseContract.BasePresenter<T> {
        fun login(data: LoginBean)
    }

}