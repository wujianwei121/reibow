package com.example.framwork.base;

import android.app.Activity;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import com.example.framwork.R;
import com.example.framwork.noHttp.CallServer;
import com.example.framwork.widget.CustomDialog;
import com.example.framwork.widget.kprogresshud.KProgressHUD;

import org.greenrobot.eventbus.EventBus;


public abstract class BaseFragment extends Fragment implements View.OnClickListener {
    protected Activity mActivity = null;
    protected View rootView = null;
    protected KProgressHUD progressHUD;

    /**
     * 此方法可以得到上下文对象
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mActivity = getActivity();
        setStatusBar();
        rootView = inflater.inflate(getContentLayout(), container, false);
        initData();
        initButterKnife(rootView);
        return rootView;
    }


    protected void setStatusBar() {
    }

    //initData 在initview之前执行
    protected abstract void initData();


    protected void initButterKnife(View view) {
    }

    /**
     * 初始化layout
     */
    protected abstract int getContentLayout();

    /**
     * 点击事件
     */
    public abstract void onClickListener(View v);

    @Override
    public void onClick(View v) {
        onClickListener(v);
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
     * 隐藏软键盘
     */
    protected void hideSoftInput(EditText view) {
        InputMethodManager mInputMethodManager =
                (InputMethodManager) getActivity().getSystemService(getActivity().INPUT_METHOD_SERVICE);
        mInputMethodManager
                .hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    /**
     * 打开软键盘
     */
    protected void showSoftInput(EditText view) {
        InputMethodManager mInputMethodManager =
                (InputMethodManager) getActivity().getSystemService(getActivity().INPUT_METHOD_SERVICE);
        mInputMethodManager
                .showSoftInput(view, InputMethodManager.SHOW_FORCED);
    }

    @Override
    public void onDestroyView() {

        if (isUseEventBus() && EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().unregister(this);
        }
        super.onDestroyView();
    }

    @Override
    public void onAttach(Activity activity) {
        if (isUseEventBus() && !EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }
        super.onAttach(activity);
    }

    @Override
    public void onDestroy() {
        if (mActivity != null) {
            CallServer.getRequestInstance().cancelBySign(mActivity);
        }
        if (isUseEventBus() && EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().unregister(this);
        }
        super.onDestroy();
    }

    /**
     * 是否使用 EventBus
     *
     * @return
     */
    protected boolean isUseEventBus() {
        return false;
    }

    protected void showCustomDialog(String msg, final BaseActivity.OnPositiveButtonClickListener listener) {
        showCustomDialog(null, "", msg, true, listener);
    }

    protected void showCustomDialog(String title, String msg, final BaseActivity.OnPositiveButtonClickListener listener) {
        showCustomDialog(null, title, msg, true, listener);
    }

    protected void showCustomDialog(String msg, boolean autoDismiss, final BaseActivity.OnPositiveButtonClickListener listener) {
        showCustomDialog(null, "", msg, autoDismiss, listener);
    }

    protected void showCustomDialog(View view, final BaseActivity.OnPositiveButtonClickListener listener) {
        showCustomDialog(view, "", "", true, listener);
    }

    protected void showCustomDialog(View view, String title, final BaseActivity.OnPositiveButtonClickListener listener) {
        showCustomDialog(view, title, "", true, listener);
    }

    protected void showCustomDialog(View view, boolean autoDismiss, final BaseActivity.OnPositiveButtonClickListener listener) {
        showCustomDialog(view, "", "", autoDismiss, listener);
    }

    protected void showCustomDialog(View view, String title, boolean autoDismiss, final BaseActivity.OnPositiveButtonClickListener listener) {
        showCustomDialog(view, title, "", autoDismiss, listener);
    }


    /**
     * 显示对话框
     *
     * @param view        内容view
     * @param msg         显示信息
     * @param autoDismiss 是否自动消失
     * @param listener    确定按钮点击监听
     */
    protected void showCustomDialog(View view, String title, String msg, boolean autoDismiss, final BaseActivity.OnPositiveButtonClickListener listener) {
        CustomDialog.Builder builder = new CustomDialog.Builder(mActivity);
        if (!TextUtils.isEmpty(title)) {
            builder.setTitle(title);
        } else {
            builder.setTitle("提示");//默认"操作提醒"
        }
        if (view != null) {
            builder.setContentView(view);
        }
        builder.setDialogAutoDismiss(autoDismiss);
        if (!TextUtils.isEmpty(msg)) {
            builder.setMessage(msg);
        }
        builder.setNegativeButton(R.string.cancel, null);
        builder.setPositiveButton(R.string.ok,
                new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        listener.positiveButtonClick(dialog, which);
                    }
                });
        builder.create().show();
    }

    /**
     * 对话框确定按钮点击
     */
    public interface OnPositiveButtonClickListener {
        void positiveButtonClick(DialogInterface dialog, int which);
    }
}
