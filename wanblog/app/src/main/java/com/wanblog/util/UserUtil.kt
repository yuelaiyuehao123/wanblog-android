package com.wanblog.util

import android.content.Context
import android.text.TextUtils
import com.wanblog.util.SharedPreferencesUtil


object UserUtil {

    private val TOKEN: String = "token"
    private val USER_NAME: String = "user_name"

    fun isLogin(context: Context): Boolean {
        val token = getToken(context);
        return !TextUtils.isEmpty(token);
    }

    /**
     * 保存Token
     */
    fun saveToken(context: Context, token: String) {
        SharedPreferencesUtil.getInstance(context).putString(TOKEN, token)
    }

    /**
     * 得到Token
     */
    fun getToken(context: Context): String =
        SharedPreferencesUtil.getInstance(context).getString(TOKEN)

    /**
     * 得到用户名
     */
    fun getUserName(context: Context): String =
        SharedPreferencesUtil.getInstance(context).getString(USER_NAME)

    /**
     * 保存用户名
     */
    fun saveUserName(context: Context, name: String) {
        SharedPreferencesUtil.getInstance(context).putString(USER_NAME, name)
    }

    fun logout(context: Context) {
        SharedPreferencesUtil.getInstance(context).clearSp()
    }
}