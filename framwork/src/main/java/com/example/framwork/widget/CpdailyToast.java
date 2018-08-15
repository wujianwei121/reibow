package com.example.framwork.widget;

import android.animation.Animator;
import android.animation.AnimatorInflater;
import android.content.Context;
import android.graphics.PixelFormat;
import android.graphics.Point;
import android.support.annotation.DrawableRes;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.framwork.R;


/**
 * Created by superstar on 17/7/22.
 */

public class CpdailyToast {
    private static TextView mTextView;
    private static ImageView mImageView;
    private static Animator bubbleAnimator;

    public static void infoToast(ViewGroup parentView, String msg) {
        showToast(parentView, msg, R.drawable.ok_toast, R.drawable.cpdaily_toast_style_info);
    }

    public static void warnToast(ViewGroup parentView, String msg) {
        showToast(parentView, msg, R.drawable.warn, R.drawable.cpdaily_toast_style_warn);
    }

    public static void showToast(ViewGroup parentView, String msg, @DrawableRes int msgIcon, @DrawableRes int bgColor) {
        Context context = parentView.getContext();
        //加载Toast布局
        final View toastRoot = LayoutInflater.from(parentView.getContext()).inflate(R.layout.cpdaily_toast, null);
        LinearLayout toastLayout = (LinearLayout) toastRoot.findViewById(R.id.cpdaily_toast);

        //初始化布局控件
        mTextView = (TextView) toastRoot.findViewById(R.id.cpdaily_toast_message);
        mImageView = (ImageView) toastRoot.findViewById(R.id.cpdaily_toast_icon);
        //为控件设置属性
        mTextView.setText(msg);
        mImageView.setImageResource(msgIcon);
        toastLayout.setBackgroundResource(bgColor);

        //获取屏幕高度
        final WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        Point display = new Point();
        wm.getDefaultDisplay().getSize(display);


        WindowManager.LayoutParams mParams = new WindowManager.LayoutParams();
        mParams.height = WindowManager.LayoutParams.WRAP_CONTENT;
        mParams.width = WindowManager.LayoutParams.WRAP_CONTENT;
        mParams.format = PixelFormat.TRANSLUCENT;
        mParams.type = WindowManager.LayoutParams.TYPE_APPLICATION_PANEL;
        mParams.flags =
                WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL | WindowManager.LayoutParams.FLAG_WATCH_OUTSIDE_TOUCH
                        | WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE;
        int[] position = new int[2];
        parentView.getLocationOnScreen(position);
        mParams.gravity = Gravity.TOP | Gravity.CENTER_HORIZONTAL;
        mParams.y = position[1];

        toastRoot.setLayoutParams(mParams);
        wm.addView(toastRoot, mParams);


        if (null != bubbleAnimator) {
            bubbleAnimator.end();
        }
        bubbleAnimator = AnimatorInflater.loadAnimator(context, R.animator.message_bubble);
        bubbleAnimator.setTarget(toastLayout);
        bubbleAnimator.setInterpolator(new AccelerateDecelerateInterpolator());
        toastLayout.clearAnimation();
        toastLayout.setAlpha(0);
        toastLayout.setTranslationY(0);

        bubbleAnimator.start();
        bubbleAnimator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                try {
                    wm.removeView(toastRoot);
                } catch (Exception e) {
                    // 如果宿主activity已经销毁,此时removeView会抱异常,但是已经不在乎了
                }

            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
    }
}
