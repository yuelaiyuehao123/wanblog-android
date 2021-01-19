package com.wanblog.util;

import android.content.Context;
import android.util.DisplayMetrics;
import android.util.TypedValue;

public class UiUtil {

    /**
     * 将 dp 转换成 px
     *
     * @param dp
     * @return
     */
    public static int dp2px(Context context, int dp) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, context.getResources().getDisplayMetrics());
    }

    /**
     * 得到屏幕的宽
     */
    public static int getScreenWidth(Context context) {
        DisplayMetrics dm = context.getResources().getDisplayMetrics();
        return dm.widthPixels;
    }

    /**
     * 得到屏幕的高
     */
    public static int getScreenHeight(Context context) {
        DisplayMetrics dm = context.getResources().getDisplayMetrics();
        return dm.heightPixels;
    }

}
