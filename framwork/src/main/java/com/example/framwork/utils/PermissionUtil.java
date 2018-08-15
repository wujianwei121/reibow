package com.example.framwork.utils;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.LocationManager;
import android.os.Build;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;

import com.example.framwork.utils.permission.DefaultRationale;
import com.example.framwork.utils.permission.PermissionSetting;
import com.yanzhenjie.permission.Action;
import com.yanzhenjie.permission.AndPermission;
import com.yanzhenjie.permission.Permission;
import com.yanzhenjie.permission.Rationale;

import java.util.List;

/**
 * Created by lenovo on 2018/5/10.
 */

public class PermissionUtil {
    private PermissionSetting mSetting;
    private Rationale mRationale;
    public static final int PERMISSION_SUCCESS = 1;
    public static final int PERMISSION_FAILED = 2;
    public static final String[] LOCATION = new String[]{
            Permission.ACCESS_FINE_LOCATION,
            Permission.ACCESS_COARSE_LOCATION,
            Permission.WRITE_EXTERNAL_STORAGE,
            Permission.READ_EXTERNAL_STORAGE,
            Permission.READ_PHONE_STATE
    };

    /**
     * 内部类实现单例模式
     * 延迟加载，减少内存开销
     *
     * @author xuzhaohu
     */
    private static class SingletonHolder {
        private static PermissionUtil instance = new PermissionUtil();

    }

    /**
     * 私有的构造函数
     */
    private PermissionUtil() {
    }

    public static PermissionUtil getInstance() {
        return PermissionUtil.SingletonHolder.instance;
    }

    public interface IPermissionsCallBck {
        void premissionsCallback(int code);
    }

    public void requestPermission(final Activity activity, final IPermissionsCallBck permissionsCallBck, String[]... permissions) {
        if (mRationale == null)
            mRationale = new DefaultRationale();
        if (mSetting == null)
            mSetting = new PermissionSetting(activity);
        AndPermission.with(activity)
                .permission(permissions)
                .rationale(mRationale)
                .onGranted(new Action() {
                    @Override
                    public void onAction(List<String> permissions) {
                        permissionsCallBck.premissionsCallback(PERMISSION_SUCCESS);
                    }
                })
                .onDenied(new Action() {
                    @Override
                    public void onAction(@NonNull List<String> permissions) {
                        Toast.getInstance().showWarningToast(activity, "权限申请失败，可能会影响您的正常使用");
                        permissionsCallBck.premissionsCallback(PERMISSION_FAILED);
                        if (AndPermission.hasAlwaysDeniedPermission(activity, permissions)) {
                            mSetting.showSetting(permissions);
                        }
                    }
                })
                .start();
    }

    public void requestPermission(final Activity activity, final IPermissionsCallBck permissionsCallBck, String permissions) {
        if (mRationale == null)
            mRationale = new DefaultRationale();
        if (mSetting == null)
            mSetting = new PermissionSetting(activity);
        AndPermission.with(activity)
                .permission(permissions)
                .rationale(mRationale)
                .onGranted(new Action() {
                    @Override
                    public void onAction(List<String> permissions) {
                        permissionsCallBck.premissionsCallback(PERMISSION_SUCCESS);
                    }
                })
                .onDenied(new Action() {
                    @Override
                    public void onAction(@NonNull List<String> permissions) {
                        Toast.getInstance().showWarningToast(activity, "权限申请失败，可能会影响您的正常使用");
                        permissionsCallBck.premissionsCallback(PERMISSION_FAILED);
                        if (AndPermission.hasAlwaysDeniedPermission(activity, permissions)) {
                            mSetting.showSetting(permissions);
                        }
                    }
                })
                .start();
    }

    /**
     * 判断GPS是否开启，GPS或者AGPS开启一个就认为是开启的
     *
     * @return true 表示开启
     */
    public boolean isOPenGps(final Activity activity) {

        int locationMode = 0;
        String locationProviders;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            try {
                locationMode = Settings.Secure.getInt(activity.getContentResolver(), Settings.Secure.LOCATION_MODE);
            } catch (Settings.SettingNotFoundException e) {
                e.printStackTrace();
                return false;
            }
            return locationMode != Settings.Secure.LOCATION_MODE_OFF;
        } else {
            locationProviders = Settings.Secure.getString(activity.getContentResolver(), Settings.Secure.LOCATION_PROVIDERS_ALLOWED);
            return !TextUtils.isEmpty(locationProviders);
        }
    }
}
