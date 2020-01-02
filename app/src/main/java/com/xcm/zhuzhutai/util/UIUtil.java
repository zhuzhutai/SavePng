package com.xcm.zhuzhutai.util;

import android.content.Context;
import android.view.WindowManager;

/**
 * 描述：
 * 时间： 2020/1/2.
 * 创建： 张继良
 */
public class UIUtil {

    public static int dip2px(Context mContext, float dipValue) {
        return (int) (dipValue * mContext.getResources().getDisplayMetrics().density + 0.5f);
    }

    public static int getScreenWidth(Context mContext) {
        WindowManager windowManager = (WindowManager) mContext.getSystemService(Context.WINDOW_SERVICE);
        return windowManager.getDefaultDisplay().getWidth();
    }
}
