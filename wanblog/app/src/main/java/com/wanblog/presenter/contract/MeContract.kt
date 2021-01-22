package com.wanblog.presenter.contract

import com.wanblog.base.BaseContract

interface MeContract {

    interface View : BaseContract.BaseView {
        fun onLogoutResult()
    }

    interface Presenter<in T> : BaseContract.BasePresenter<T> {
        fun logout()
    }

}