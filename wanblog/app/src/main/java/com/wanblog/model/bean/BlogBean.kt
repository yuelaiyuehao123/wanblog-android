package com.wanblog.model.bean

data class BlogBean(
    val id: Long,
    val userId: Long,
    val title: String,
    val description: String,
    val content: String,
    val created: String,
    val username: String,
    val avatar: String,
    val status: Int
)