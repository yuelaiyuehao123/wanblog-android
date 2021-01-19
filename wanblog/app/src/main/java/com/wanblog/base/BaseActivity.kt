package com.wanblog.base

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.wanblog.dagger.componet.ActivityComponent
import com.wanblog.dagger.componet.DaggerActivityComponent
import com.wanblog.dagger.module.ActivityModule
import com.wanblog.event.DummyEvent
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import javax.inject.Inject

abstract class BaseActivity<T : BaseContract.BasePresenter<*>> : AppCompatActivity(),
    BaseContract.BaseView {

    protected val mActivity: AppCompatActivity = this

    @Inject
    lateinit var mPresenter: T

    //优先使用属性
    protected val mActivityModule: ActivityModule get() = ActivityModule(this)

    protected val activityComponent: ActivityComponent
        get() = DaggerActivityComponent.builder()
            .appComponent(App.instance.appComponent)
            .activityModule(mActivityModule)
            .build()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(getLayout())
        App.instance.addActivity(mActivity)
        EventBus.getDefault().register(mActivity)
        initInject()
        initPresenter()
        initView()
        initData()
    }

    override fun onDestroy() {
        super.onDestroy()
        App.instance.removeActivity(mActivity)
        EventBus.getDefault().unregister(mActivity)
        mPresenter.detachView()
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
     * 初始化dagger2
     */
    protected abstract fun initInject()

    /**
     * 初始化Presenter
     */
    protected abstract fun initPresenter()

    /**
     * 该方法不执行，只是让Event编译通过
     */
    @Subscribe
    fun dummy(event: DummyEvent) {
    }

}