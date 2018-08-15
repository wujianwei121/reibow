package com.example.framwork.bean;

import android.text.TextUtils;

import com.example.framwork.BaseApplictaion;
import com.example.framwork.baseapp.BaseAppConfig;
import com.example.framwork.utils.CommonUtil;
import com.example.framwork.utils.MD5;
import com.example.framwork.utils.SPUtils;

import java.io.Serializable;
import java.util.Random;

/**
 * 公共请求
 */
public class BaseRequestBean implements Serializable {
    private String version = BaseAppConfig.VERSION_NUM;
    private String deviceNo = BaseAppConfig.DEVICE_NO;
    private String appID = "1001";
    private long timestamp = System.currentTimeMillis();

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public String getAppID() {
        return appID;
    }

    public void setAppID(String appID) {
        this.appID = appID;
    }

    public String getDeviceNo() {
        if (TextUtils.isEmpty(deviceNo)) {
            deviceNo = (String) SPUtils.getInstance().get(BaseApplictaion.getInstance(), BaseAppConfig.DEVICE_NO_KEY, "");
        }
        return deviceNo;
    }

    public void setDeviceNo(String deviceNo) {
        this.deviceNo = deviceNo;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }
}