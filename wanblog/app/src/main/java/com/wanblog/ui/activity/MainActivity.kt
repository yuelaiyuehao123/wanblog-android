package com.wanblog.ui.activity

import android.view.KeyEvent
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.wanblog.R
import com.wanblog.base.App
import com.wanblog.base.SimpleActivity
import com.wanblog.ext.loadFragments
import com.wanblog.ext.showHideFragment
import com.wanblog.ui.fragment.HomeFragment
import com.wanblog.ui.fragment.MeFragment
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : SimpleActivity() {

    var mTabPosition: Int = 0
    var mExitTime: Long = 0

    val FRAGMENT_A = 0
    val FRAGMENT_B = 1
    private lateinit var fragmentsMap: Map<Int, Fragment>

    override fun getLayout(): Int = R.layout.activity_main

    override fun initView() {
        initBottomNavigationView()
        fragmentsMap = generateABFragments()
        loadFragments(R.id.fl_main_container, 0, *fragmentsMap.values.toTypedArray())
    }

    override fun initData() {
    }

    /**
     * 初始化底部导航栏
     */
    private fun initBottomNavigationView() {
        bnve_main.setOnNavigationItemSelectedListener { item ->
            val position = bnve_main.getMenuItemPosition(item)
            showHideFragment(fragmentsMap.getValue(position))
            mTabPosition = position
            true
        }
    }

    fun generateABFragments() = mapOf(
        FRAGMENT_A to HomeFragment.newInstance(),
        FRAGMENT_B to MeFragment.newInstance()
    )

    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        if (keyCode == KeyEvent.KEYCODE_BACK && event?.repeatCount == 0) {
            if ((System.currentTimeMillis() - mExitTime) > 2000) {
                Toast.makeText(mActivity, "再按一次返回键退出程序", Toast.LENGTH_SHORT).show()
                mExitTime = System.currentTimeMillis()
            } else {
                App.instance.exitApp()
            }
            return true
        }
        return super.onKeyDown(keyCode, event)
    }
}
