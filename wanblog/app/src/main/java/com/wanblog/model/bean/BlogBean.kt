package com.wanblog.model.bean

data class BlogBean(
    val blog_id: Long,
    val user_id: Long,
    val title: String,
    val description: String,
    val content: String,
    val created: String,
    val username: String,
    val avatar: String,
    val status: Int
)