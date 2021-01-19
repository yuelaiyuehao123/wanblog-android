package com.wanblog.base

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.wanblog.event.DummyEvent
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe

abstract class SimpleActivity : AppCompatActivity() {

    protected val mActivity = this

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(getLayout())
        App.instance.addActivity(mActivity)
        EventBus.getDefault().register(mActivity)
        initView()
        initData()
    }

    override fun onDestroy() {
        super.onDestroy()
        EventBus.getDefault().unregister(mActivity)
        App.instance.removeActivity(mActivity)
    }

    /**
     * 初始化子类布局
     */
    protected abstract fun getLayout(): Int

    /**
     * 初始化子类View
     */
    protected abstract fun initView()

    /**
     * 初始化子类一些数据
     */
    protected abstract fun initData()

    /**
     * 该方法不执行，只是让Event编译通过
     */
    @Subscribe
    fun dummy(event: DummyEvent) {
    }
}