package com.wanblog.model.http

import com.wanblog.model.bean.BlogBean
import com.wanblog.model.bean.LoginResultBean
import com.wanblog.model.bean.MyHttpResponse
import io.reactivex.rxjava3.core.Flowable
import okhttp3.RequestBody
import retrofit2.http.*

interface ApiService {

    @POST(ApiSettings.signUp)
    fun signUp(@Body body: RequestBody): Flowable<MyHttpResponse<Any>>

    @POST(ApiSettings.login)
    fun login(@Body body: RequestBody): Flowable<MyHttpResponse<LoginResultBean>>

    @GET(ApiSettings.logout)
    fun logout(@Header(ApiSettings.tokenKey) token: String): Flowable<MyHttpResponse<Any>>

    @GET(ApiSettings.blog_list)
    fun getBlogList(@Header(ApiSettings.tokenKey) token: String, @Query("currentPage") currentPage: Int, @Query("size") size: Int):
            Flowable<MyHttpResponse<MutableList<BlogBean>>>

    @GET(ApiSettings.blog)
    fun getBlogDetail(@Header(ApiSettings.tokenKey) token: String, @Path("bid") id: Long): Flowable<MyHttpResponse<BlogBean>>

    @POST(ApiSettings.blog_edit)
    fun blogEdit(@Header(ApiSettings.tokenKey) token: String, @Body body: RequestBody): Flowable<MyHttpResponse<Any>>

    @POST(ApiSettings.blog_publish)
    fun blogPublish(@Header(ApiSettings.tokenKey) token: String, @Body body: RequestBody): Flowable<MyHttpResponse<Any>>

    @POST(ApiSettings.blog_delete)
    fun blogDelete(@Header(ApiSettings.tokenKey) token: String, @Body body: RequestBody): Flowable<MyHttpResponse<Any>>

}