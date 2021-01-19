package com.wanblog.util

import android.app.Activity
import android.app.ActivityManager
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.content.pm.ResolveInfo
import android.net.ConnectivityManager
import android.net.Uri
import android.os.Build
import android.os.Process
import androidx.core.content.FileProvider
import com.wanblog.base.App
import org.jetbrains.anko.doAsync
import java.io.BufferedWriter
import java.io.File
import java.io.FileWriter
import java.util.*

object Utils {

    /**
     * 判断程序是否重复启动
     */
    fun isApplicationRepeat(applicationContext: Context): Boolean {

        val pid = android.os.Process.myPid()
        var processName: String? = null
        val am = applicationContext.getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
        val l = am.runningAppProcesses
        val i = l.iterator()
        while (i.hasNext()) {
            val info = i.next()
            try {
                if (info.pid == pid) {
                    processName = info.processName
                }
            } catch (e: Exception) {
            }

        }
        return processName == null || !processName.equals(
            applicationContext.packageName,
            ignoreCase = true
        )
    }

    /**
     * 判断网络是否好用
     */
    fun isNetworkConnected(): Boolean {
        val connectivityManager =
            App.instance.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        return connectivityManager.getActiveNetworkInfo() != null
    }

    /**
     * 得到okhttp 缓存目录
     * /Android/data/package-name/cache/okhttp
     */
    fun getOKHttpCachePath(): String =
        App.instance.externalCacheDir!!.canonicalPath + File.separator + "okhttp"

    /**
     * 得到crash log目录
     * /Android/data/package-name/files/log/crashLog
     */
    fun getCrashLogPath(): String {
        val path =
            App.instance.getExternalFilesDir("log")!!.canonicalPath + File.separator + "crashLog"
        val file = File(path)
        if (!file.exists()) {
            file.mkdirs()
        }
        return file.canonicalPath
    }

    /**
     * 得到http log目录
     * /Android/data/package-name/files/log/httpLog
     */
    fun getHttpLogPath(): String {
        val path =
            App.instance.getExternalFilesDir("log")!!.canonicalPath + File.separator + "httpLog"
        val file = File(path)
        if (!file.exists()) {
            file.mkdirs()
        }
        return file.canonicalPath
    }

    /**
     * 下载更新apk包地址
     * /Android/data/package-name/files/apk
     */
    fun getApkPath(): String {
        val path = App.instance.getExternalFilesDir("apk")!!.canonicalPath
        val file = File(path)
        if (!file.exists()) {
            file.mkdirs()
        }
        return file.canonicalPath
    }

    /**
     * 写入log到文本文件中
     */
    fun writerLog(log: String) {

        doAsync {
            try {
                val path = getHttpLogPath()
                val file = File(path, "http.txt")
                if (!file.exists()) {
                    file.createNewFile()
                } else {
                    //文件超过100KB就清空重新写
                    val length = file.length()
                    val kb = length / 1024
                    if (kb > 100) {
                        val fileWriter = FileWriter(file)
                        fileWriter.write("")
                        fileWriter.flush()
                        fileWriter.close()
                    }
                }
                val out = FileWriter(file, true)
                val bw = BufferedWriter(out)
                bw.newLine()
                bw.write(log)
                bw.flush()
                bw.close()
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    /**
     * 安装apk
     */
    fun installApk(activity: Activity, targetFilePath: String) {
        val apkFile = File(targetFilePath)
        if (!apkFile.exists()) {
            return
        }
        val intent = Intent(Intent.ACTION_VIEW)
        // 由于没有在Activity环境下启动Activity,设置下面的标签
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
        if (Build.VERSION.SDK_INT >= 24) { //判读版本是否在7.0以上
            //参数1 上下文, 参数2 Provider主机地址 和配置文件中保持一致   参数3  共享的文件
            val apkUri =
                FileProvider.getUriForFile(activity, "com.fqb.fqbang.fileprovider", apkFile)
            //添加这一句表示对目标应用临时授权该Uri所代表的文件
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
            intent.setDataAndType(apkUri, "application/vnd.android.package-archive")
            val resInfoList = activity.packageManager.queryIntentActivities(
                intent,
                PackageManager.MATCH_DEFAULT_ONLY
            ) as List<ResolveInfo>
            //然后全部授权
            for (resolveInfo in resInfoList) {
                val packageName = resolveInfo.activityInfo.packageName
                activity.grantUriPermission(
                    packageName,
                    apkUri,
                    Intent.FLAG_GRANT_WRITE_URI_PERMISSION or Intent.FLAG_GRANT_READ_URI_PERMISSION
                )
            }
        } else {
            intent.setDataAndType(Uri.fromFile(apkFile), "application/vnd.android.package-archive")
        }
        activity.startActivity(intent)
        Process.killProcess(Process.myPid())
    }

    /**
     * Return pseudo unique ID
     * @return ID
     */
    fun getUniquePsuedoID(): String {
        // If all else fails, if the user does have lower than API 9 (lower
        // than Gingerbread), has reset their device or 'Secure.ANDROID_ID'
        // returns 'null', then simply the ID returned will be solely based
        // off their Android device information. This is where the collisions
        // can happen.
        // Thanks http://www.pocketmagic.net/?p=1662!
        // Try not to use DISPLAY, HOST or ID - these items could change.
        // If there are collisions, there will be overlapping data
        val m_szDevIDShort = "35" + Build.BOARD.length % 10 +
                Build.BRAND.length % 10 +
                Build.CPU_ABI.length % 10 +
                Build.DEVICE.length % 10 +
                Build.MANUFACTURER.length % 10 +
                Build.MODEL.length % 10 +
                Build.PRODUCT.length % 10

        // Thanks to @Roman SL!
        // http://stackoverflow.com/a/4789483/950427
        // Only devices with API >= 9 have android.os.Build.SERIAL
        // http://developer.android.com/reference/android/os/Build.html#SERIAL
        // If a user upgrades software or roots their device, there will be a duplicate entry
        var serial: String? = null
        try {
            serial = android.os.Build::class.java.getField("SERIAL").get(null).toString()

            // Go ahead and return the serial for api => 9
            return UUID(m_szDevIDShort.hashCode().toLong(), serial.hashCode().toLong()).toString()
        } catch (exception: Exception) {
            // String needs to be initialized
            serial = "serial" // some value
        }

        // Thanks @Joe!
        // http://stackoverflow.com/a/2853253/950427
        // Finally, combine the values we have found by using the UUID class to create a unique identifier
        return UUID(m_szDevIDShort.hashCode().toLong(), serial!!.hashCode().toLong()).toString()
    }


    /**
     * 获取当前应用的版本号
     */
    fun getVersion(context: Context): String {
        try {
            val manager = context.packageManager
            val info = manager.getPackageInfo(context.packageName, 0)
            return info.versionName
        } catch (e: Exception) {
            e.printStackTrace()
            return "1.0.0"
        }
    }

}