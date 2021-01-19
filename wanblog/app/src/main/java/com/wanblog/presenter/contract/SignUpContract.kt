package com.wanblog.presenter.contract

import com.wanblog.base.BaseContract
import com.wanblog.model.bean.SignUpBean

interface SignUpContract {

    interface View : BaseContract.BaseView {
        fun onSignUpResult()
    }

    interface Presenter<in T> : BaseContract.BasePresenter<T> {
        fun signUp(data: SignUpBean)
    }

}