package com.example.framwork.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;

import com.example.framwork.WebViewActivity;
import com.example.framwork.zxing.ui.CaptureActivity;

import java.io.Serializable;
import java.util.List;

/**
 * Created by lenovo on 2017/3/7.
 */

public class BaseGoto {
    /**
     * 去到拨号界面
     *
     * @param mobile
     */
    public static void toDialMobile(Context context, String mobile) {
        if (TextUtils.isEmpty(mobile)) {
            return;
        }
        Intent intent = new Intent(Intent.ACTION_DIAL);
        Uri data = Uri.parse("tel:" + mobile);
        intent.setData(data);
        context.startActivity(intent);
    }

    /**
     * 跳转webview
     *
     * @param context
     * @param title
     * @param url
     * @param bgId    标题栏背景id 如果不需要传0
     */

    public static void toWebView(Context context, String title, String url, int bgId) {
        Intent intent = new Intent(context, WebViewActivity.class);
        intent.putExtra("title", title);
        intent.putExtra("url", url);
        intent.putExtra("title_bg", bgId);
        context.startActivity(intent);
    }

    public static void toQRCode(Context context, int bgId) {
        Intent intent = new Intent(context, CaptureActivity.class);
        intent.putExtra("title_bg", bgId);
        context.startActivity(intent);
    }
}
