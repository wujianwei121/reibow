package com.example.framwork.base;

import android.app.Activity;
import android.content.DialogInterface;
import android.os.Handler;
import android.text.TextUtils;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import com.example.framwork.R;
import com.example.framwork.widget.CustomDialog;
import com.example.framwork.widget.kprogresshud.KProgressHUD;


public abstract class BasePageView {
    protected Activity mActivity;
    protected KProgressHUD progressHUD;
    protected View rootView;

    public BasePageView(Activity mActivity) {
        this.mActivity = mActivity;
    }

    public View getRootView() {
        return rootView;
    }

    public abstract void initView();

    public abstract void initData();

    /**
     * 隐藏软键盘
     */
    protected void hideSoftInput(EditText view) {
        InputMethodManager mInputMethodManager = (InputMethodManager) mActivity.getSystemService(mActivity.INPUT_METHOD_SERVICE);
        mInputMethodManager
                .hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    /**
     * 打开软键盘
     */
    protected void showSoftInput(EditText view) {
        InputMethodManager mInputMethodManager = (InputMethodManager) mActivity.getSystemService(mActivity.INPUT_METHOD_SERVICE);
        mInputMethodManager
                .showSoftInput(view, InputMethodManager.SHOW_FORCED);
    }

    public void showProgress(Boolean isCancel, String hint) {
        if (progressHUD == null) {
            progressHUD = KProgressHUD.create(mActivity).setStyle(KProgressHUD.Style.SPIN_INDETERMINATE).setDimAmount(0.5f);
        }
        if (!TextUtils.isEmpty(hint)) {
            progressHUD.setLabel(hint);
        } else {
            progressHUD.setLabel("加载中...");
        }
        progressHUD.setCancellable(isCancel);
        progressHUD.show();
    }

    public void showProgress() {
        showProgress(true, "");
    }

    public void showProgress(String hint) {
        showProgress(true, hint);
    }

    public void showProgress(boolean isCancel) {
        showProgress(isCancel, "");
    }

    public void hideProgress() {
        if (progressHUD != null) {
            progressHUD.dismiss();
        }
    }

    /**
     * 是否使用 EventBus
     *
     * @return
     */
    protected boolean isUseEventBus() {
        return false;
    }

    /**
     * 设置自动消失dialog button也自动隐藏
     *
     * @param msg
     * @param allowDismiss
     */
    public void showCustomDialog(String msg, boolean allowDismiss) {
        showCustomDialog(null, "", "", "", msg, true, !allowDismiss, allowDismiss, true, null);
    }

    public void showCustomDialog(String msg, final BaseActivity.OnPositiveButtonClickListener listener) {
        showCustomDialog(null, "", msg, "", "", true, true, false, true, listener);
    }

    public void showCustomDialog(String msg, String negativeBtn, String positiveBtn, final BaseActivity.OnPositiveButtonClickListener listener) {
        showCustomDialog(null, "", msg, negativeBtn, positiveBtn, true, true, false, true, listener);
    }

    public void showCustomDialog(String title, String msg, final BaseActivity.OnPositiveButtonClickListener listener) {
        showCustomDialog(null, title, msg, "", "", true, true, false, true, listener);
    }

    public void showCustomDialog(String msg, boolean allowDismiss, final BaseActivity.OnPositiveButtonClickListener listener) {
        showCustomDialog(null, "", msg, "", "", true, true, false, allowDismiss, listener);
    }

    public void showCustomDialogOneBtn(String msg, String btn) {
        showCustomDialog(null, "", msg, "", btn, false, true, false, true, null);
    }

    public void showCustomDialog(View view, final BaseActivity.OnPositiveButtonClickListener listener) {
        showCustomDialog(view, "", "", "", "", true, true, false, true, listener);
    }

    public void showCustomDialog(View view, String title, final BaseActivity.OnPositiveButtonClickListener listener) {
        showCustomDialog(view, title, "", "", "", true, true, false, true, listener);
    }

    public void showCustomDialog(View view, boolean allowDismiss, final BaseActivity.OnPositiveButtonClickListener listener) {
        showCustomDialog(view, "", "", "", "", true, true, false, allowDismiss, listener);
    }

    public void showCustomDialog(View view, String title, boolean allowDismiss, final BaseActivity.OnPositiveButtonClickListener listener) {
        showCustomDialog(view, title, "", "", "", true, true, false, allowDismiss, listener);
    }

    /**
     * 显示对话框
     *
     * @param view         内容view
     * @param msg          显示信息
     * @param autoDismiss  是否自动消失
     * @param allowDismiss 点确定按钮是否允许消失
     * @param showBtn      是否需要按钮
     * @param listener     确定按钮点击监听
     */
    protected void showCustomDialog(View view, String title, String msg, String negativeBtn, String positiveBtn, boolean negativeBtnVisible, boolean showBtn, boolean autoDismiss, final boolean allowDismiss, final BaseActivity.OnPositiveButtonClickListener listener) {
        final CustomDialog.Builder builder = new CustomDialog.Builder(mActivity);
        builder.setShowBtn(showBtn);
        if (!TextUtils.isEmpty(title)) {
            builder.setTitle(title);
        } else {
            builder.setTitle("提示");//默认"操作提醒"
        }
        if (view != null) {
            builder.setContentView(view);
        }
        builder.setDialogAutoDismiss(allowDismiss);
        if (!TextUtils.isEmpty(msg)) {
            builder.setMessage(msg);
        }
        if (negativeBtnVisible)
            builder.setNegativeButton(TextUtils.isEmpty(negativeBtn) ? mActivity.getString(R.string.cancel) : negativeBtn, null);
        else builder.setNegativeButton(null, null);
        builder.setPositiveButton(TextUtils.isEmpty(positiveBtn) ? mActivity.getString(R.string.ok) : positiveBtn,
                new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (allowDismiss) {
                            dialog.dismiss();
                        }
                        if (listener != null) {
                            dialog.dismiss();
                            listener.positiveButtonClick(dialog, which);
                        }
                    }
                });
        builder.create().show();
        if (autoDismiss) {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    if (builder.getDialog() != null && builder.getDialog().isShowing()) {
                        builder.getDialog().dismiss();
                    }
                }
            }, 2000);
        }
    }

    /**
     * 对话框确定按钮点击
     */
    public interface OnPositiveButtonClickListener {
        void positiveButtonClick(DialogInterface dialog, int which);
    }
}

