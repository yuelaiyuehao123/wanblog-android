package com.wanblog.util

import android.content.Context
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.wanblog.R

object ImageUtils {

    fun load(context: Context, iv: ImageView, url: String) {
        Glide.with(context).load(url).placeholder(R.drawable.ic_place_holder).into(iv)
    }

}