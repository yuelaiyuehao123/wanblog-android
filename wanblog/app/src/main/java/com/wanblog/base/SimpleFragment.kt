package com.wanblog.base

import android.content.Context
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import com.wanblog.event.DummyEvent
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe

abstract class SimpleFragment : BaseLazyFragment() {

    protected var mContext: Context? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mContext = context
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        EventBus.getDefault().register(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(getLayout(), null)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            val localLayoutParams = activity?.window?.attributes
            if (localLayoutParams != null) {
                localLayoutParams.flags =
                    WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS or localLayoutParams.flags
            }
        }
        return view
    }

    override fun lazyInit() {
        initView()
        initData()
    }

    override fun onDestroy() {
        super.onDestroy()
        EventBus.getDefault().unregister(this)
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