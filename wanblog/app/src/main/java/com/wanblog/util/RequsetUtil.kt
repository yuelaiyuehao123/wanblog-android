package com.wanblog.util

import okhttp3.MediaType.Companion.toMediaType
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import org.json.JSONObject

object RequsetUtil {

    fun getRequestBody(jsonObject: JSONObject): RequestBody {
        val strEntity = jsonObject.toString()
        val stringBody = strEntity.toRequestBody("application/json; charset=utf-8".toMediaType())
        return stringBody
    }
}