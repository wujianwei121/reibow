package com.example.framwork.base;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.webkit.WebView;
import android.widget.EditText;

import com.example.framwork.R;
import com.example.framwork.baseapp.AppManager;
import com.example.framwork.noHttp.CallServer;
import com.example.framwork.utils.Toast;
import com.example.framwork.widget.CustomDialog;
import com.example.framwork.widget.kprogresshud.KProgressHUD;

import org.greenrobot.eventbus.EventBus;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Date;


public abstract class BaseActivity extends AppCompatActivity implements OnClickListener {
    protected Activity mActivity;
    private String imgurl = "";
    protected KProgressHUD progressHUD;
    protected Bundle bundle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        hideStatusBar();
        bundle = savedInstanceState;
        if (isUseEventBus() && !EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }
        mActivity = this;
        if (getContentLayout() != 0) {
            setContentView(getContentLayout());
        }
        initButterKnife();
        AppManager.getAppManager().addActivity(this);
        initData();
    }


    public void hideStatusBar() {

    }

    @Override
    public void setContentView(int layoutResID) {
        super.setContentView(layoutResID);
        setStatusBar();
    }


    protected void setStatusBar() {
    }


    /**
     * 设置点击事件
     */
    protected void setClickListener() {
    }

    /**
     * 隐藏软键盘
     */
    protected void hideSoftInput(EditText view) {
        InputMethodManager mInputMethodManager = (InputMethodManager) getSystemService(this.INPUT_METHOD_SERVICE);
        mInputMethodManager
                .hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    /**
     * 打开软键盘
     */
    protected void showSoftInput(EditText view) {
        InputMethodManager mInputMethodManager = (InputMethodManager) getSystemService(this.INPUT_METHOD_SERVICE);
        mInputMethodManager
                .showSoftInput(view, InputMethodManager.SHOW_FORCED);
    }

    /**
     * 获取布局文件
     *
     * @return 布局文件ID
     */
    protected abstract int getContentLayout();

    /**
     * 按需重写   如果添加头部无需重写
     */
    protected void initButterKnife() {
    }


    protected abstract void initData();


    /**
     * 点击事件
     */
    public abstract void onClickListener(View v);

    public void showProgress(Boolean isCancel, String hint) {
        if (progressHUD == null) {
            progressHUD = KProgressHUD.create(this).setStyle(KProgressHUD.Style.SPIN_INDETERMINATE).setDimAmount(0.5f);
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

    @Override
    public void onClick(View v) {
        onClickListener(v);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        CallServer.getRequestInstance().cancelBySign(mActivity);
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

    /**
     * 设置自动消失dialog button也自动隐藏
     *
     * @param msg
     * @param allowDismiss
     */
    public void showCustomDialog(String msg, boolean allowDismiss) {
        showCustomDialog(null, "", "", "", msg, !allowDismiss, allowDismiss, true, null, null);
    }

    public void showCustomDialog(String msg, final OnPositiveButtonClickListener listener) {
        showCustomDialog(null, "", msg, "", "", true, false, true, listener, null);
    }

    public void showCustomDialog(String msg, String negativeBtn, String positiveBtn, final OnPositiveButtonClickListener listener) {
        showCustomDialog(null, "", msg, negativeBtn, positiveBtn, true, false, true, listener, null);
    }

    public void showCustomDialog(String title, String msg, final OnPositiveButtonClickListener listener) {
        showCustomDialog(null, title, msg, "", "", true, false, true, listener, null);
    }

    public void showCustomDialog(String msg, boolean allowDismiss, final OnPositiveButtonClickListener listener) {
        showCustomDialog(null, "", msg, "", "", true, false, allowDismiss, listener, null);
    }

    public void showCustomDialog(View view, final OnPositiveButtonClickListener listener) {
        showCustomDialog(view, "", "", "", "", true, false, true, listener, null);
    }

    public void showCustomDialog(View view, String title, final OnPositiveButtonClickListener listener) {
        showCustomDialog(view, title, "", "", "", true, false, true, listener, null);
    }

    public void showCustomDialog(View view, boolean allowDismiss, final OnPositiveButtonClickListener listener) {
        showCustomDialog(view, "", "", "", "", true, false, allowDismiss, listener, null);
    }

    public void showCustomDialog(View view, String title, boolean allowDismiss, final OnPositiveButtonClickListener listener) {
        showCustomDialog(view, title, "", "", "", true, false, allowDismiss, listener, null);
    }

    public void showCustomDialog(String msg, String negativeBtn, String positiveBtn, OnPositiveButtonClickListener positiveButtonClickListener, OnNegativeButtonClickListener negativeButtonClickListener) {
        showCustomDialog(null, "", msg, negativeBtn, positiveBtn, true, false, true, positiveButtonClickListener, negativeButtonClickListener);
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
    protected void showCustomDialog(View view, String title, String msg, String negativeBtn, String positiveBtn, boolean showBtn, boolean autoDismiss, boolean allowDismiss, final OnPositiveButtonClickListener listener, final OnNegativeButtonClickListener negativeButtonClickListener) {
        final CustomDialog.Builder builder = new CustomDialog.Builder(this);
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
        builder.setNegativeButton(TextUtils.isEmpty(negativeBtn) ? getString(R.string.cancel) : negativeBtn, negativeButtonClickListener == null ? null : new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                negativeButtonClickListener.negativeButtonClick(dialog, which);
            }
        });
        builder.setPositiveButton(TextUtils.isEmpty(positiveBtn) ? getString(R.string.ok) : positiveBtn,
                new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        listener.positiveButtonClick(dialog, which);
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

    /**
     * 对话框取消按钮点击
     */
    public interface OnNegativeButtonClickListener {
        void negativeButtonClick(DialogInterface dialog, int which);
    }

    /***
     * 功能：长按图片保存到手机
     */
    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuItem.OnMenuItemClickListener handler = new MenuItem.OnMenuItemClickListener() {
            public boolean onMenuItemClick(MenuItem item) {
                if (item.getTitle() == "保存图片到手机") {
                    new SaveImage().execute(); // Android 4.0以后要使用线程来访问网络
                } else {
                    return false;
                }
                return true;
            }
        };
        if (v instanceof WebView) {
            WebView.HitTestResult result = ((WebView) v).getHitTestResult();
            if (result != null) {
                int type = result.getType();
                if (type == WebView.HitTestResult.IMAGE_TYPE || type == WebView.HitTestResult.SRC_IMAGE_ANCHOR_TYPE) {
                    imgurl = result.getExtra();
                    menu.setHeaderTitle("提示");
                    menu.add(0, v.getId(), 0, "保存图片到手机").setOnMenuItemClickListener(handler);
                    menu.add(0, v.getId(), 0, "查看大图").setOnMenuItemClickListener(handler);
                }
            }
        }
    }

    /***
     * 功能：用线程保存图片
     *
     * @author wangyp
     */
    private class SaveImage extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... params) {
            String result = "";
            try {
                String sdcard = Environment.getExternalStorageDirectory().toString();
                File file = new File(sdcard + "/Pictures");
                if (!file.exists()) {
                    file.mkdirs();
                }
                int idx = imgurl.lastIndexOf(".");
                String ext = imgurl.substring(idx);
                file = new File(sdcard + "/Pictures/" + new Date().getTime() + ext);
                InputStream inputStream = null;
                URL url = new URL(imgurl);
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("GET");
                conn.setConnectTimeout(20000);
                if (conn.getResponseCode() == 200) {
                    inputStream = conn.getInputStream();
                }
                byte[] buffer = new byte[4096];
                int len = 0;
                FileOutputStream outStream = new FileOutputStream(file);
                while ((len = inputStream.read(buffer)) != -1) {
                    outStream.write(buffer, 0, len);
                }
                outStream.close();
                result = "图片已保存至：" + file.getAbsolutePath();
                //更新系统相册
                Intent intent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
                Uri uri = Uri.fromFile(file);
                intent.setData(uri);
                sendBroadcast(intent);
            } catch (Exception e) {
                result = "保存失败！" + e.getLocalizedMessage();
            }
            return result;
        }

        @Override
        protected void onPostExecute(String result) {
            Toast.getInstance().showDefaultToast(mActivity, result);
        }
    }

    /**
     * 提示错误信息
     *
     * @param errorMsg
     */
    public void toastError(String errorMsg) {
        Toast.getInstance().showWarningToast(this, errorMsg);
    }

    /**
     * 提示基本信息
     *
     * @param errorMsg
     */
    public void toastInfo(String errorMsg) {
        Toast.getInstance().showDefaultToast(this, errorMsg);
    }

    /**
     * 提示成功信息
     *
     * @param errorMsg
     */
    public void toastSuccess(String errorMsg) {
        Toast.getInstance().showSuccessToast(this, errorMsg);
    }

    /**
     * 提示警告信息
     *
     * @param errorMsg
     */
    public void toastWarning(String errorMsg) {
        Toast.getInstance().showWarningToast(this, errorMsg);
    }

//    //设置Android app的字体不随系统全局字体大小的变动而变动
//    @Override
//    public Resources getResources() {
//        Resources res = super.getResources();
//        Configuration config = new Configuration();
//        config.setToDefaults();
//        res.updateConfiguration(config, res.getDisplayMetrics());
//        return res;
//    }

}

