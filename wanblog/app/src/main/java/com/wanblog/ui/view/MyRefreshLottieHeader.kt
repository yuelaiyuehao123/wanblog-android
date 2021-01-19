package com.wanblog.ui.view

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.widget.LinearLayout
import com.scwang.smart.refresh.layout.api.RefreshHeader
import com.scwang.smart.refresh.layout.api.RefreshKernel
import com.scwang.smart.refresh.layout.api.RefreshLayout
import com.scwang.smart.refresh.layout.constant.RefreshState
import com.scwang.smart.refresh.layout.constant.SpinnerStyle
import com.wanblog.R

import kotlinx.android.synthetic.main.view_header.view.*

/**
 */
class MyRefreshLottieHeader(context: Context) : LinearLayout(context), RefreshHeader {

    /**
     * The M animation view.
     */
    lateinit var mHeaderView: View

    init {
        initView(context)
    }

    private fun initView(context: Context) {
        val layoutInflater =
            context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        mHeaderView = layoutInflater.inflate(R.layout.view_header, this)
    }

    override fun onMoving(
        isDragging: Boolean,
        percent: Float,
        offset: Int,
        height: Int,
        maxDragHeight: Int
    ) {
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

    override fun onStartAnimator(refreshLayout: RefreshLayout, height: Int, maxDragHeight: Int) {
        mHeaderView.header_lottie.playAnimation()
    }

    override fun onFinish(refreshLayout: RefreshLayout, success: Boolean): Int {
        mHeaderView.header_lottie.cancelAnimation()
        return 0
    }

    override fun onReleased(refreshLayout: RefreshLayout, height: Int, maxDragHeight: Int) {
    }

    override fun onHorizontalDrag(percentX: Float, offsetX: Int, offsetMax: Int) {

    }

    override fun isSupportHorizontalDrag(): Boolean {
        return false
    }

    override fun onStateChanged(
        refreshLayout: RefreshLayout,
        oldState: RefreshState,
        newState: RefreshState
    ) {
        when (newState) {
            RefreshState.ReleaseToRefresh -> mHeaderView.tv_header.text = "松开刷新"
            RefreshState.Refreshing -> mHeaderView.tv_header.text = "刷新中..."
            RefreshState.PullDownToRefresh -> mHeaderView.tv_header.text = "下拉刷新"
            else -> {
            }
        }

    }

}
