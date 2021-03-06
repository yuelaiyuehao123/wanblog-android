package com.wanblog.ui.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import com.wanblog.R;

/**
 * 一个半透明窗口,包含一个Progressbar 和 Message部分. 其中Message部分可选. 可单独使用,也可以使用
 *
 * @author Qijun
 */
public class EasyProgressDialog extends Dialog {
    private Context mContext;

    private String mMessage;

    private int mLayoutId;

    private TextView message;

    public EasyProgressDialog(Context context, int style, int layout) {
        super(context, style);
        mContext = context;
        WindowManager.LayoutParams Params = getWindow().getAttributes();
        Params.width = WindowManager.LayoutParams.MATCH_PARENT;
        Params.height = WindowManager.LayoutParams.MATCH_PARENT;
        getWindow().setAttributes(Params);
        mLayoutId = layout;
    }

    public EasyProgressDialog(Context context, int layout, String msg) {
        this(context, R.style.easy_dialog_style, layout);
        setMessage(msg);
    }

    public EasyProgressDialog(Context context, String msg) {
        this(context, R.style.easy_dialog_style, R.layout.dialog_loading);
        setMessage(msg);
    }

    public EasyProgressDialog(Context context) {
        this(context, R.style.easy_dialog_style, R.layout.dialog_loading);
    }

    public void setMessage(String msg) {
        mMessage = msg;
    }

    public void updateLoadingMessage(String msg) {
        mMessage = msg;
        showMessage();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(mLayoutId);
        message = (TextView) findViewById(R.id.easy_progress_dialog_message);
        showMessage();
    }

    private void showMessage() {
        if (message != null && !TextUtils.isEmpty(mMessage)) {
            message.setVisibility(View.VISIBLE);
            message.setText(mMessage);
        }
    }
}