package com.wanblog.base

import android.app.Application
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.multidex.MultiDex
import com.balsikandar.crashreporter.CrashReporter
import com.scwang.smart.refresh.layout.SmartRefreshLayout
import com.wanblog.BuildConfig
import com.wanblog.dagger.componet.AppComponent
import com.wanblog.dagger.componet.DaggerAppComponent
import com.wanblog.dagger.module.AppModule
import com.wanblog.log.LogUtil
import com.wanblog.ui.view.MyRefreshLottieFooter
import com.wanblog.ui.view.MyRefreshLottieHeader
import com.wanblog.util.Utils

class App : Application() {

    val mAllActivities: LinkedHashSet<AppCompatActivity> = LinkedHashSet()

    companion object {
        lateinit var instance: App

        var mIsRefresh: Boolean = false
    }

    val appComponent: AppComponent by lazy {
        DaggerAppComponent.builder()
            .appModule(AppModule(this))
            .build()
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
        //初始化Logger的TAG
        LogUtil.init(BuildConfig.DEBUG)
        // dex突破65535的限制
        MultiDex.install(this)
        //初始化下拉刷新View
        initSmartRefreshLayout()
        //APP Crash后保存log日志
        CrashReporter.initialize(this, Utils.getCrashLogPath())
    }

    /**
     * 保存Activity的引用
     */
    fun addActivity(act: AppCompatActivity) {
        mAllActivities.add(act)
    }

    /**
     * 清除Activity的引用
     */
    fun removeActivity(act: AppCompatActivity) {
        mAllActivities.remove(act)
    }

    /**
     * 得到当前栈顶的Activity
     */
    fun getCurrentActivity(): AppCompatActivity {
        return mAllActivities.last()
    }

    private fun initSmartRefreshLayout() {
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);
        SmartRefreshLayout.setDefaultRefreshHeaderCreator { context, _ ->
            //Lottie动画的header
            MyRefreshLottieHeader(context)
            //普通风格的header
//            ClassicsHeader(context).setSpinnerStyle(SpinnerStyle.Translate)
        }
        SmartRefreshLayout.setDefaultRefreshFooterCreator { context, _ ->
            //Lottie动画的footer
            MyRefreshLottieFooter(context)
            //普通风格的footer
//            ClassicsFooter(context).setSpinnerStyle(SpinnerStyle.Translate)
        }
    }

    /**
     * 退出App
     */
    fun exitApp() {
        synchronized(mAllActivities) {
            for (act in mAllActivities) {
                act.finish()
            }
        }
    }
}