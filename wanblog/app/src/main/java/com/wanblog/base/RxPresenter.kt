package com.wanblog.base

import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.disposables.Disposable

open class RxPresenter<T : BaseContract.BaseView> : BaseContract.BasePresenter<T> {

    var mView: T? = null
    var mCompositeDisposable: CompositeDisposable? = null

    override fun attachView(view: T) {
        this.mView = view
    }

    override fun detachView() {
        this.mView = null
        unSubscribe()
    }

    fun addSubscribe(disposable: Disposable) {
        if (mCompositeDisposable == null) {
            mCompositeDisposable = CompositeDisposable()
        }
        mCompositeDisposable?.add(disposable)
    }

    fun unSubscribe() {
        mCompositeDisposable?.dispose()
    }


}