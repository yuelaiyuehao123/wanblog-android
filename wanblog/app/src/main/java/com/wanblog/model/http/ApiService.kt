package com.wanblog.model.http

import com.wanblog.model.bean.BlogBean
import com.wanblog.model.bean.LoginResultBean
import com.wanblog.model.bean.MyHttpResponse
import io.reactivex.rxjava3.core.Flowable
import okhttp3.RequestBody
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface ApiService {

    @POST(ApiSettings.signUp)
    fun signUp(@Body body: RequestBody): Flowable<MyHttpResponse<Any>>

    @POST(ApiSettings.login)
    fun login(@Body body: RequestBody): Flowable<MyHttpResponse<LoginResultBean>>

    @GET(ApiSettings.blog_list)
    fun getBlogList(@Query("currentPage") currentPage: Int, @Query("size") size: Int):
            Flowable<MyHttpResponse<MutableList<BlogBean>>>


}