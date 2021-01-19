package com.wanblog.ui.view

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.widget.LinearLayout
import com.scwang.smart.refresh.layout.api.RefreshFooter
import com.scwang.smart.refresh.layout.api.RefreshKernel
import com.scwang.smart.refresh.layout.api.RefreshLayout
import com.scwang.smart.refresh.layout.constant.RefreshState
import com.scwang.smart.refresh.layout.constant.SpinnerStyle
import com.wanblog.R
import kotlinx.android.synthetic.main.view_footer.view.*

class MyRefreshLottieFooter(context: Context) : LinearLayout(context), RefreshFooter {

    /**
     * The M animation view.
     */
    lateinit var mFooterView: View

    init {
        initView(context)
    }

    private fun initView(context: Context) {
        val layoutInflater =
            context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        mFooterView = layoutInflater.inflate(R.layout.view_footer, this)
    }

    /**
     * 注意不能为null
     * @return
     */
    override fun getView(): View {
        return this
    }

    override fun getSpinnerStyle(): SpinnerStyle {
        return SpinnerStyle.Translate
    }


    override fun setPrimaryColors(vararg colors: Int) {

    }

    override fun onInitialized(kernel: RefreshKernel, height: Int, extendHeight: Int) {

    }

    override fun onMoving(
        isDragging: Boolean,
        percent: Float,
        offset: Int,
        height: Int,
        maxDragHeight: Int
    ) {
    }

    override fun onReleased(refreshLayout: RefreshLayout, height: Int, maxDragHeight: Int) {
    }

    override fun onHorizontalDrag(percentX: Float, offsetX: Int, offsetMax: Int) {

    }

    override fun onStartAnimator(layout: RefreshLayout, height: Int, extendHeight: Int) {
        mFooterView.footer_lottie.playAnimation()
    }

    override fun onFinish(layout: RefreshLayout, success: Boolean): Int {
        mFooterView.footer_lottie.cancelAnimation()
        return 0
    }

    override fun isSupportHorizontalDrag(): Boolean {
        return false
    }

    override fun onStateChanged(
        refreshLayout: RefreshLayout,
        oldState: RefreshState,
        newState: RefreshState
    ) {
    }

    override fun setNoMoreData(noMoreData: Boolean): Boolean {
        return true
    }
}
