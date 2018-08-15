package com.example.framwork.base;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.Nullable;

import com.example.framwork.bean.BaseRequestBean;
import com.example.framwork.noHttp.OnRequestListener;
import com.yanzhenjie.nohttp.RequestMethod;
import com.yanzhenjie.nohttp.rest.Request;

import org.json.JSONObject;

import java.util.HashMap;


/**
 * Created by wanjingyu on 2016/10/8.
 */

public abstract class BasePresenter {
    public CommonModel model;
    public Activity activity;

    public BasePresenter(Activity activity) {
        this.model = new CommonModel();
        this.activity = activity;
        this.context = activity;
    }

    public Context context;

    public BasePresenter(Context context) {
        this.model = new CommonModel();
        this.context = context;
    }

    /**
     * 当请求里面需要添加请求头 先获取request
     * 最后要调用model.execute
     *
     * @return
     */
    public Request<JSONObject> getRequest(HashMap info, String url, String methodName) {
        return model.getRequest(info, url, methodName);
    }

    public Request<JSONObject> getRequest(String url, String methodName) {
        return model.getRequest(url, methodName);
    }

    public Request<JSONObject> getRequest(BaseRequestBean info, String url, String methodName) {
        return model.getRequest(info, url, methodName);
    }

    public Request<JSONObject> getRequest(BaseRequestBean info, String url, String methodName, RequestMethod method) {
        return model.getRequest(info, url, methodName, method);
    }

    public void post(HashMap info, String methodName, boolean isShowLoading, String loadingText, OnRequestListener requestListener) {
        model.resultPostModel(activity, info, methodName, isShowLoading, loadingText, true, requestListener);
    }

    public void get(@Nullable HashMap info, String methodName, String loadingText, OnRequestListener requestListener) {
        model.resultGettModel(activity, info, methodName, true, loadingText, true, requestListener);
    }

    public void get(@Nullable HashMap info, String methodName, OnRequestListener requestListener) {
        model.resultGettModel(activity, info, methodName, true, "", true, requestListener);
    }

    public void get(@Nullable HashMap info, String methodName, boolean isShow, OnRequestListener requestListener) {
        model.resultGettModel(activity, info, methodName, isShow, "", true, requestListener);
    }

    /**
     * 无参数请求
     *
     * @param methodName
     * @param isShow
     * @param requestListener
     */
    public void get(String methodName, boolean isShow, OnRequestListener requestListener) {
        model.resultGettModel(activity, null, methodName, isShow, "", true, requestListener);
    }

    public void postJson(Object o, String methodName, String loadingText, OnRequestListener requestListener) {
        model.resultPostJsonModel(activity, o, methodName, true, loadingText, true, requestListener);
    }

    public void postJson2(String path, String methodName, Object o, boolean showLoading, OnRequestListener requestListener) {
        model.resultPostJsonModel2(activity, path, methodName, o, showLoading, "", showLoading, requestListener);
    }

    public void postJson2(String path, String methodName, Object o, String showLoading, OnRequestListener requestListener) {
        model.resultPostJsonModel2(activity, path, methodName, o, true, showLoading, true, requestListener);
    }

    public void postJson2(String path, String methodName, Object o, String loadingText, boolean isShowToast, OnRequestListener requestListener) {
        model.resultPostJsonModel2(activity, path, methodName, o, true, loadingText, isShowToast, requestListener);
    }

    public void postJson(Object o, String methodName, String loadingText, boolean isShowToast, OnRequestListener requestListener) {
        model.resultPostJsonModel(activity, o, methodName, true, loadingText, isShowToast, requestListener);
    }

    public void postJson(Object o, String methodName, boolean isShowLoading, String loadingText, boolean isShowToast, OnRequestListener requestListener) {
        model.resultPostJsonModel(activity, o, methodName, isShowLoading, loadingText, isShowToast, requestListener);
    }

    /**
     * 默认带loading并且loading默认文字的postjson
     *
     * @param o
     * @param methodName
     * @param requestListener
     */
    public void postJson(Object o, String methodName, boolean isShow, OnRequestListener requestListener) {
        model.resultPostJsonModel(activity, o, methodName, isShow, "", isShow, requestListener);
    }

    public void postJsonContext(Object o, String methodName, boolean isShow, OnRequestListener requestListener) {
        model.resultPostJsonModel(context, o, methodName, true, "", isShow, requestListener);
    }

    public void postJson(Object o, String methodName, OnRequestListener requestListener) {
        model.resultPostJsonModel(activity, o, methodName, true, "", true, requestListener);
    }

    public void get(BaseRequestBean o, String methodName, String loadingText, OnRequestListener requestListener) {
        model.resultGetModel(activity, o, methodName, true, loadingText, true, requestListener);
    }

    public void get(BaseRequestBean o, String methodName, boolean isShow, OnRequestListener requestListener) {
        model.resultGetModel(activity, o, methodName, isShow, "", true, requestListener);
    }

    public void get(BaseRequestBean o, String methodName, OnRequestListener requestListener) {
        model.resultGetModel(activity, o, methodName, true, "", true, requestListener);
    }

}
