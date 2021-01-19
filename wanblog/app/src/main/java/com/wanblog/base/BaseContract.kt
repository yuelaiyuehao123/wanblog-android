package com.wanblog.base

interface BaseContract {

    interface BaseView {

        fun showProgress()

//        fun hideProgress()

        fun showError(url: String, msg: String, code: Int)

    }

    interface BasePresenter<in T> {

        /**
         * 绑定

         * @param view view
         */
        fun attachView(view: T)

        /**
         * 解绑
         */
        fun detachView()
    }

}
