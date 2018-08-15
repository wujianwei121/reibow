package com.example.framwork.widget;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager.LayoutParams;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.framwork.R;


/**
 * 自定义弹出框
 */
public class CustomDialog extends Dialog implements DialogInterface {

    public CustomDialog(Context context) {
        super(context, R.style.custom_cancel_dialog);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
    }

    public CustomDialog(Context context, int theme) {
        super(context, theme);
    }

    public static class Builder {
        // 上下文
        private Context context;
        // 内容view
        private View contentView;

        // 确认按钮文本
        private String positiveButtonText;
        /**
         * 确定按钮字体颜色
         */
        private int positiveButtonTextColor = -1;
        /**
         * 确认按钮字体大小
         */
        private int positiveButtonTextSize = -1;

        // 取消按钮文本
        private String negativeButtonText;

        // dialog title
        private String title;
        /**
         * 设置标题显示的位置 默认Gravity.CENTER
         */
        private int titleGravity = Gravity.CENTER;
        /**
         * 标题字体颜色
         */
        private int titleColor = -1;
        /**
         * 标题字体大小
         */
        private int titleSize = -1;
        /**
         * 标题消息文本
         */
        private String message;
        /**
         * 是否可以关闭
         */
        private boolean cancelFlag = false;

        /**
         * 是否需要自动关闭dialog(考虑到点击确定按钮有检查不通过的情况，设置为false时，需自行实现点击消失对话框)
         */
        private boolean dialogAutoDismiss = true;
        /**
         * 是否隐藏标题(默认不隐藏)
         */
        private boolean titleViewGone = true;

        /**
         * 设置点击取消按钮时是否需要自动关闭dialog,默认关闭
         */
        private boolean negativeButtonClickDialogAutoDismiss = true;
        // 宽度
        private int width;
        // 高度
        private int height;

        // 按钮的监听事件
        private OnClickListener positiveButtonClickListener, negativeButtonClickListener;
        private CustomDialog dialog;
        private boolean topCancleViewShow = false;//是否显示右上角的取消按钮(默认不显示)
        private boolean isShowBtn = true;

        public Builder(Context context) {
            this.context = context;
        }

        public CustomDialog getDialog() {
            return dialog;
        }

        /**
         * 视图
         *
         * @param v
         * @return
         */
        public Builder setContentView(View v) {
            this.contentView = v;
            return this;
        }

        public Builder setShowBtn(boolean showBtn) {
            isShowBtn = showBtn;
            return this;
        }

        /**
         * 提示信息
         *
         * @param parammessage
         * @return
         */
        public Builder setMessage(String parammessage) {
            this.message = parammessage;
            return this;
        }

        public Builder setMessage(int paramtitleID) {
            this.message = this.context.getString(paramtitleID);
            return this;
        }

        /**
         * 标题
         *
         * @param paramtitle
         * @return
         */
        public Builder setTitle(String paramtitle) {
            this.title = paramtitle;
            return this;
        }

        /**
         * 标题
         *
         * @param paramtitleID
         * @return
         */
        public Builder setTitle(int paramtitleID) {
            this.title = this.context.getString(paramtitleID);
            return this;
        }

        /**
         * 设置是否隐藏标题布局
         *
         * @param titleViewGone
         * @return
         */
        public Builder setTitleViewGone(boolean titleViewGone) {
            this.titleViewGone = titleViewGone;
            return this;
        }

        /**
         * 设置标题显示的位置
         *
         * @param gravity
         * @return
         */
        public Builder setTitleGravity(int gravity) {
            titleGravity = gravity;
            return this;
        }

        public Builder setTitleColor(int color) {
            titleColor = color;
            return this;
        }

        public Builder setTitleSize(int size) {
            titleSize = size;
            return this;
        }

        public int getPositiveButtonTextColor() {
            return positiveButtonTextColor;
        }

        public Builder setPositiveButtonTextColor(int positiveButtonTextColor) {
            this.positiveButtonTextColor = positiveButtonTextColor;
            return this;
        }

        public int getPositiveButtonTextSize() {
            return positiveButtonTextSize;
        }

        public Builder setPositiveButtonTextSize(int positiveButtonTextSize) {
            this.positiveButtonTextSize = positiveButtonTextSize;
            return this;
        }

        /**
         * 取消按钮
         *
         * @param paramnegativeButtonText 取消按钮文字
         * @param listener                取消按钮点击监听
         * @return Builder
         */
        public Builder setNegativeButton(String paramnegativeButtonText, OnClickListener listener) {
            this.negativeButtonText = paramnegativeButtonText;
            this.negativeButtonClickListener = listener;
            return this;
        }

        public Builder setEnable(boolean flag) {
            return this;
        }

        /**
         * 设置可关闭
         *
         * @param flag
         * @return
         */
        public Builder setCancelable(boolean flag) {
            cancelFlag = flag;
            return this;
        }

        /**
         * 设置dialog自动消失
         *
         * @param dialogAutoDismiss 是否自动消失
         * @return
         */
        public Builder setDialogAutoDismiss(boolean dialogAutoDismiss) {
            this.dialogAutoDismiss = dialogAutoDismiss;
            return this;
        }

        /**
         * 设置右上角取消按钮是否可见
         *
         * @param topCancleViewShow
         * @return
         */
        public Builder setTopCancleViewShow(boolean topCancleViewShow) {
            this.topCancleViewShow = topCancleViewShow;
            return this;
        }

        public Builder setNegativeButtonClickDialogAutoDismiss(boolean flag) {
            this.negativeButtonClickDialogAutoDismiss = flag;
            return this;
        }

        public Builder setNegativeButton(int paramnegativeButtonRes, OnClickListener listener) {
            this.negativeButtonText = this.context.getString(paramnegativeButtonRes);
            this.negativeButtonClickListener = listener;
            return this;
        }

        public Builder setPositiveButton(String parampositiveButtonText, OnClickListener listener) {
            this.positiveButtonText = parampositiveButtonText;
            this.positiveButtonClickListener = listener;
            return this;
        }

        public Builder setPositiveButton(int parampositiveButtonRes,
                                         OnClickListener listener) {
            if (context != null) {
                this.positiveButtonText = this.context.getString(parampositiveButtonRes);
            }
            this.positiveButtonClickListener = listener;
            return this;
        }

        /**
         * 自定义Dialog大小
         */
        public Builder setDialogSize(int width, int height) {
            this.width = width;
            this.height = height;
            return this;
        }

        /**
         * Create the custom dialog
         *
         * @return CustomDialog
         */
        public CustomDialog create() {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            dialog = new CustomDialog(context);
            View layout = inflater.inflate(R.layout.dialog_custom, null);
            dialog.setContentView(layout);
            // 自定义大小
            if (0 != width || 0 != height) {
                Window dialogWindow = dialog.getWindow();
                LayoutParams _lp = dialogWindow.getAttributes();
                _lp.width = width;
                _lp.height = height;
                dialogWindow.setAttributes(_lp);
            }
            if (titleViewGone) {
                layout.findViewById(R.id.dialog_alert).setVisibility(View.GONE);
            } else {
                layout.findViewById(R.id.dialog_alert).setVisibility(View.VISIBLE);
            }
            TextView titleView = (TextView) layout.findViewById(R.id.dialog_alert_title);
            TextView tvTopCancle = (TextView) layout.findViewById(R.id.tv_top_cancle);
            tvTopCancle.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                }
            });
            if (topCancleViewShow) {
                tvTopCancle.setVisibility(View.VISIBLE);
            } else {
                tvTopCancle.setVisibility(View.GONE);
            }

            titleView.setGravity(titleGravity);
            if (titleColor != -1) {
                titleView.setTextColor(titleColor);
            }
            if (titleSize != -1) {
                titleView.setTextSize(TypedValue.COMPLEX_UNIT_SP, titleSize);
            }

            TextView msgView = (TextView) layout.findViewById(R.id.dialog_alert_message);
            titleView.setText(title);
            Button posButton = (Button) layout.findViewById(R.id.btn_alert_dialog_confirm);
            Button navButton = (Button) layout.findViewById(R.id.btn_alert_dialog_cancel);
            LinearLayout llbtn = (LinearLayout) layout.findViewById(R.id.alert_dialog_btns);
            View line = layout.findViewById(R.id.split_line_second);
            if (!isShowBtn) {
                llbtn.setVisibility(View.GONE);
                line.setVisibility(View.GONE);
            } else {
                llbtn.setVisibility(View.VISIBLE);
                line.setVisibility(View.VISIBLE);
            }
            if (positiveButtonTextColor != -1) {
                posButton.setTextColor(positiveButtonTextColor);
            }
            if (positiveButtonTextSize != -1) {
                posButton.setTextSize(TypedValue.COMPLEX_UNIT_SP, positiveButtonTextSize);
            }

            if (positiveButtonText != null) {
                posButton.setText(positiveButtonText);
                posButton.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View v) {
                        if (positiveButtonClickListener != null) {
                            positiveButtonClickListener.onClick(dialog, DialogInterface.BUTTON_POSITIVE);
                            if (dialogAutoDismiss) {
                                dialog.dismiss();
                            }
                        } else {
                            dialog.dismiss();
                        }
                    }
                });

            } else {
                // if no confirm button just set the visibility to GONE
                posButton.setVisibility(View.GONE);
                layout.findViewById(R.id.btn_split_line).setVisibility(View.GONE);
                LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
                lp.gravity = Gravity.CENTER;
                navButton.setLayoutParams(lp);
                navButton.setGravity(Gravity.CENTER);
            }

            // set the cancel button
            if (negativeButtonText != null) {
                navButton.setText(negativeButtonText);
                navButton.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View v) {
                        if (negativeButtonClickListener != null) {
                            negativeButtonClickListener.onClick(dialog, DialogInterface.BUTTON_NEGATIVE);
                            if (negativeButtonClickDialogAutoDismiss) {
                                dialog.dismiss();
                            }
                        } else {
                            dialog.dismiss();
                        }
                    }
                });
            } else {
                // 如果只设置了确认，没设置取消，则将确认按钮居中并且隐藏取消按钮的显示
                navButton.setVisibility(View.GONE);
                layout.findViewById(R.id.btn_split_line).setVisibility(View.GONE);
                LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
                lp.gravity = Gravity.CENTER;
                posButton.setLayoutParams(lp);
                posButton.setGravity(Gravity.CENTER);
            }
            if (negativeButtonText == null && positiveButtonText == null) {
                layout.findViewById(R.id.split_line_second).setVisibility(View.GONE);
                layout.findViewById(R.id.dialog_scroll__content).setBackgroundResource(R.color.transparent);
            }
            // set the content message
            if (message != null) {
                msgView.setText(message);
            } else if (contentView != null) {
                LinearLayout contentContainer = (LinearLayout) layout.findViewById(R.id.dialog_content);
                contentContainer.removeAllViews();
                contentContainer.addView(contentView);
            }
            dialog.setCancelable(cancelFlag);
            dialog.setContentView(layout);
            return dialog;
        }
    }
}
