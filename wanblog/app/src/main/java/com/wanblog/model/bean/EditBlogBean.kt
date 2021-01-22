package com.wanblog.model.bean

data class EditBlogBean(
    val id: Long,
    val title: String,
    val description: String,
    val content: String
)