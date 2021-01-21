package com.wanblog.model.http

import com.wanblog.log.okHttpLog.HttpLoggingInterceptorM
import com.wanblog.log.okHttpLog.LogInterceptor
import com.wanblog.model.bean.BlogBean
import com.wanblog.model.bean.LoginResultBean
import com.wanblog.model.bean.MyHttpResponse
import com.wanblog.util.Utils
import io.reactivex.rxjava3.core.Flowable
import okhttp3.*
import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import java.io.File
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
//
        val cachePath = Utils.getOKHttpCachePath()
        val cacheFile = File(cachePath)
        val cache = Cache(cacheFile, (1024 * 1024 * 50).toLong())
        val cacheInterceptor = Interceptor { chain ->
            var request = chain.request()
            if (!Utils.isNetworkConnected()) {
                request = request.newBuilder()
                    .cacheControl(CacheControl.FORCE_CACHE)
                    .build()
            }
            val response = chain.proceed(request)
            if (Utils.isNetworkConnected()) {
                val maxAge = 0
                // 有网络时, 不缓存, 最大保存时长为0
                response.newBuilder()
                    .header("Cache-Control", "public, max-age=" + maxAge)
                    .removeHeader("Pragma")
                    .build()
            } else {
                // 无网络时，设置超时为4周
                val maxStale = 60 * 60 * 24 * 28
                response.newBuilder()
                    .header("Cache-Control", "public, only-if-cached, max-stale=" + maxStale)
                    .removeHeader("Pragma")
                    .build()
            }
        }
//        val apikey = Interceptor { chain ->
//            var request = chain.request()
//            request = request.newBuilder()
//                    .addHeader("apikey", "header")
//                    .build()
//            chain.proceed(request)
//        }
//        //设置统一的请求头部参数
//        builder.addInterceptor(apikey)
        builder.addNetworkInterceptor(cacheInterceptor)
        builder.addInterceptor(cacheInterceptor)
        //设置缓存
        builder.cache(cache)
        //设置超时
        builder.connectTimeout(10, TimeUnit.SECONDS)
        builder.readTimeout(20, TimeUnit.SECONDS)
        builder.writeTimeout(20, TimeUnit.SECONDS)
        //错误重连
        builder.retryOnConnectionFailure(true)
        //无代理模式防抓包
//        builder.proxy(Proxy.NO_PROXY)
        val okHttpClient = builder.build()

//        统一校验code码
        return Retrofit.Builder().baseUrl(SERVER)
            .client(okHttpClient)
            .addConverterFactory(CheckGsonConverterFactory.create())
            .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
            .build()
    }

    private fun initServices(retrofit: Retrofit) {
        mApiService = retrofit.create(ApiService::class.java)
    }

    fun login(body: RequestBody): Flowable<MyHttpResponse<LoginResultBean>> =
        mApiService.login(body)

    fun signUp(body: RequestBody): Flowable<MyHttpResponse<Any>> =
        mApiService.signUp(body)

    fun getBlogList(currentPage: Int, size: Int): Flowable<MyHttpResponse<MutableList<BlogBean>>> =
        mApiService.getBlogList(currentPage, size)

    fun getBlogDetail(id: Long): Flowable<MyHttpResponse<BlogBean>> =
        mApiService.getBlogDetail(id)


}