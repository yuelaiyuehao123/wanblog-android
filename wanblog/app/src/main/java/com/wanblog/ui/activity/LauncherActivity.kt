package com.wanblog.ui.activity

import com.wanblog.R
import com.wanblog.base.SimpleActivity
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.uiThread

class LauncherActivity : SimpleActivity() {

    override fun getLayout(): Int = R.layout.activity_launcher

    override fun initView() {
    }

    override fun initData() {
        doAsync {
            Thread.sleep(1500)
            uiThread {
                startActivity<MainActivity>()
                finish()
            }
        }
    }
}