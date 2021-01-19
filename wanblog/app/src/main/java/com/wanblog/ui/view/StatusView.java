package com.wanblog.ui.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.RelativeLayout;

import com.airbnb.lottie.LottieAnimationView;
import com.wanblog.R;

public class StatusView extends RelativeLayout {

    private View ll_status_loading;
    private LottieAnimationView animation_view;
    private View ll_status_data_error;
    private View ll_status_net_error;
    private View ll_status_data_empty;

    public StatusView(Context context) {
        this(context, null);
    }

    public StatusView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public StatusView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);
    }

    private void initView(Context context) {
        View statusView = inflate(context, R.layout.view_status, this);
        ll_status_loading = statusView.findViewById(R.id.ll_status_loading);
        animation_view = statusView.findViewById(R.id.animation_view);
        ll_status_data_error = statusView.findViewById(R.id.ll_status_data_error);
        ll_status_net_error = statusView.findViewById(R.id.ll_status_net_error);
        ll_status_data_empty = statusView.findViewById(R.id.ll_status_data_empty);
    }

    /**
     * 显示加载中
     */
    public void showLoading() {
        setVisibility(VISIBLE);
        ll_status_loading.setVisibility(VISIBLE);
        animation_view.setAnimation("bounching_ball.json");
        animation_view.setRepeatCount(0);
        animation_view.playAnimation();
        ll_status_data_error.setVisibility(GONE);
        ll_status_net_error.setVisibility(GONE);
        ll_status_data_empty.setVisibility(GONE);
    }

    /**
     * 显示网络错误
     */
    public void showNetError() {
        setVisibility(VISIBLE);
        animation_view.cancelAnimation();
        ll_status_net_error.setVisibility(VISIBLE);
        ll_status_loading.setVisibility(GONE);
        ll_status_data_error.setVisibility(GONE);
        ll_status_data_empty.setVisibility(GONE);
    }

    /**
     * 显示没有数据
     */
    public void showDataEmpty() {
        setVisibility(VISIBLE);
        animation_view.cancelAnimation();
        ll_status_data_empty.setVisibility(VISIBLE);
        ll_status_data_error.setVisibility(GONE);
        ll_status_loading.setVisibility(GONE);
        ll_status_net_error.setVisibility(GONE);
    }

    /**
     * 显示数据错误
     */
    public void showDataError() {
        setVisibility(VISIBLE);
        animation_view.cancelAnimation();
        ll_status_data_error.setVisibility(VISIBLE);
        ll_status_loading.setVisibility(GONE);
        ll_status_net_error.setVisibility(GONE);
        ll_status_data_empty.setVisibility(GONE);
    }

    public void hide() {
        animation_view.cancelAnimation();
        setVisibility(GONE);
    }
}
