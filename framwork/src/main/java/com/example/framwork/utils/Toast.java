package com.example.framwork.utils;

import android.app.Activity;
import android.text.TextUtils;

import com.example.framwork.widget.BamboyToast.BToast;

/**
 * Created by mc on 16/12/16.
 */

public class Toast {
    /**
     * 内部类实现单例模式
     * 延迟加载，减少内存开销
     *
     * @author xuzhaohu
     */
    private static class SingletonHolder {
        private static Toast instance = new Toast();
    }

    /**
     * 私有的构造函数
     */
    private Toast() {

    }

    public static Toast getInstance() {
        return Toast.SingletonHolder.instance;
    }


    public void showWarningToast(Activity activity, String message) {
        if (activity == null || activity.isFinishing() || TextUtils.isEmpty(message)) {
            return;
        }
        BToast.showText(activity, message, false);
    }

    public void showDefaultToast(Activity activity, String message) {
        if (activity == null || activity.isFinishing() || TextUtils.isEmpty(message)) {
            return;
        }
        BToast.showText(activity, message, 500);
    }

    public void showSuccessToast(Activity activity, String message) {
        if (activity == null || activity.isFinishing() || TextUtils.isEmpty(message)) {
            return;
        }
        BToast.showText(activity, message, true);
    }

}
