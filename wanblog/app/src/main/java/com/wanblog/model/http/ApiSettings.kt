package com.wanblog.model.http

object ApiSettings {

    //token的key
    const val tokenKey = "Authorization"

    //注册
    const val signUp = "api/user/signUp"

    //登录
    const val login = "api/user/login"

    //退出登录
    const val logout = "api/user/logout"

    //首页博客列表
    const val blog_list = "api/blog/list"

    //博客详情
    const val blog = "api/blog/{id}"

}