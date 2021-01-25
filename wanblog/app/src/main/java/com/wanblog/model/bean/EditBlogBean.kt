package com.wanblog.model.bean

data class EditBlogBean(
    val bid: Long,
    val title: String,
    val description: String,
    val content: String
)