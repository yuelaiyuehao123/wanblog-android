package com.wanblog.model.http

import com.wanblog.base.App
import com.wanblog.base.BaseContract
import com.wanblog.util.LoadingDialogUtil
import io.reactivex.rxjava3.subscribers.ResourceSubscriber
import org.jetbrains.anko.toast
import org.json.JSONObject
import retrofit2.HttpException
import java.net.ConnectException
import java.net.NoRouteToHostException
import java.net.SocketTimeoutException
import java.net.UnknownHostException

abstract class CommonSubscriber<T>(private val mView: BaseContract.BaseView?) :
    ResourceSubscriber<T>() {

    //错误信息
    private var mErrorMsg = ""

    private var mErrorCode = 0

    //请求的url
    private var mUrl = ""

    //是否显示页面中的loading(页面中的loadingView需要预埋到各个页面中)
    private var mIsShowViewLoading: Boolean = false

    //是否显示dialog的loading
    private var mIsShowDialogLoading: Boolean = false

    constructor(
        view: BaseContract.BaseView?, isShowViewLoading: Boolean = false,
        isShowDialogLoading: Boolean = false, url: String = ""
    ) : this(view) {
        this.mIsShowViewLoading = isShowViewLoading;
        this.mIsShowDialogLoading = isShowDialogLoading
        this.mUrl = url
    }

    override fun onStart() {
        super.onStart()
        if (mIsShowViewLoading) {
            mView?.showProgress()
        }

        if (mIsShowDialogLoading) {
            LoadingDialogUtil.showProgressDialog(App.instance.getCurrentActivity(), "加载中...", false)
        }

    }

    override fun onComplete() {

//        if (mIsShowViewLoading) {
//            mView?.hideProgress()
//        }

        if (mIsShowDialogLoading) {
            LoadingDialogUtil.dismissProgressDialog()
            mIsShowDialogLoading = false
        }
    }

    override fun onError(e: Throwable?) {
        if (mView == null) {
            return
        }
        var responseData = ""

        //判断是哪种异常
        when (e) {
            // 自定义异常
            is ApiException -> {
                try {
                    responseData = e.getResponseData()
                    val json = JSONObject(responseData)
                    mErrorMsg = json.optString("msg")
                    mErrorCode = json.optInt("code")
                    //判断自定义的状态码
                    when (mErrorCode) {
                        //token 错误
                        ApiCode.TOKEN_ERROR -> {
                            mErrorMsg = ""
                        }
                        //检查更新
                        ApiCode.UPDATE -> {
                            mErrorMsg = ""
                        }
                        //系统维护
                        ApiCode.ERROR -> {
                        }
                    }
                } catch (e: Exception) {
                    mErrorMsg = "服务器数据错误"
                    e.printStackTrace();

                    //上报服务器错误日志
//                    BuglyLog.d("http", mUrl + responseData)
//                    CrashReport.postCatchedException(ApiException())
                }

            }
            //网络异常
            is HttpException, is ConnectException, is UnknownHostException,
            is SocketTimeoutException, is NoRouteToHostException -> {
                mErrorMsg = "网络不给力"
                mErrorCode = ApiCode.NET_ERROR
                //上报服务器错误日志
//                BuglyLog.d("http", mUrl + responseData)
//                CrashReport.postCatchedException(ApiException())
            }
            //其他错误
            else -> {
                mErrorMsg = "未知错误"

                //上报服务器错误日志
//                BuglyLog.d("http", mUrl + responseData)
//                CrashReport.postCatchedException(ApiException())
            }

        }
        //显示toast
        App.instance.toast(mErrorMsg)

        //隐藏dialog样式的loading
        if (mIsShowDialogLoading) {
            LoadingDialogUtil.dismissProgressDialog()
            mIsShowDialogLoading = false
        }

        //回调view层
        mView.showError(mUrl, mErrorMsg, mErrorCode)
    }


}