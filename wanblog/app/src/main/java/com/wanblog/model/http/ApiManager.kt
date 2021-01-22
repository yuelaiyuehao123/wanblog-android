package com.wanblog.model.http

import com.wanblog.base.App
import com.wanblog.log.okHttpLog.HttpLoggingInterceptorM
import com.wanblog.log.okHttpLog.LogInterceptor
import com.wanblog.model.bean.BlogBean
import com.wanblog.model.bean.LoginResultBean
import com.wanblog.model.bean.MyHttpResponse
import com.wanblog.util.UserUtil
import io.reactivex.rxjava3.core.Flowable
import okhttp3.OkHttpClient
import okhttp3.RequestBody
import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import java.util.concurrent.TimeUnit


object ApiManager {

    val SERVER: String = "http://59.110.44.147:9528"

    private val HTTP_LOG_TAG: String = "http"

    private lateinit var mApiService: ApiService

    init {
        val retrofit = initRetrofit()
        initServices(retrofit)
    }

    private fun initRetrofit(): Retrofit {
        val builder = OkHttpClient.Builder()
        //http log
        val interceptor = HttpLoggingInterceptorM(LogInterceptor(HTTP_LOG_TAG))
        interceptor.level = HttpLoggingInterceptorM.Level.BODY
        builder.addInterceptor(interceptor)
        //设置超时
        builder.connectTimeout(10, TimeUnit.SECONDS)
        builder.readTimeout(20, TimeUnit.SECONDS)
        builder.writeTimeout(20, TimeUnit.SECONDS)
        //无代理模式防抓包
//        builder.proxy(Proxy.NO_PROXY)
        val okHttpClient = builder.build()
        return Retrofit.Builder().baseUrl(SERVER)
            .client(okHttpClient)
            .addConverterFactory(CheckGsonConverterFactory.create())
            .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
            .build()
    }

    private fun initServices(retrofit: Retrofit) {
        mApiService = retrofit.create(ApiService::class.java)
    }

    fun signUp(body: RequestBody): Flowable<MyHttpResponse<Any>> =
        mApiService.signUp(body)

    fun login(body: RequestBody): Flowable<MyHttpResponse<LoginResultBean>> =
        mApiService.login(body)

    fun logout(): Flowable<MyHttpResponse<Any>> = mApiService.logout(UserUtil.getToken(App.instance))

    fun getBlogList(currentPage: Int, size: Int): Flowable<MyHttpResponse<MutableList<BlogBean>>> =
        mApiService.getBlogList(UserUtil.getToken(App.instance), currentPage, size)

    fun getBlogDetail(id: Long): Flowable<MyHttpResponse<BlogBean>> =
        mApiService.getBlogDetail(UserUtil.getToken(App.instance), id)

}