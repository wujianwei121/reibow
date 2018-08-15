package com.example.framwork.noHttp;

import android.app.Activity;
import android.content.Context;
import android.text.TextUtils;

import com.alibaba.fastjson.JSON;
import com.example.framwork.base.BaseActivity;
import com.example.framwork.utils.DLog;
import com.example.framwork.utils.DateUtil;
import com.example.framwork.utils.SPUtils;
import com.example.framwork.utils.Toast;
import com.yanzhenjie.nohttp.Headers;
import com.yanzhenjie.nohttp.Logger;
import com.yanzhenjie.nohttp.RequestMethod;
import com.yanzhenjie.nohttp.error.NetworkError;
import com.yanzhenjie.nohttp.error.TimeoutError;
import com.yanzhenjie.nohttp.rest.Request;
import com.yanzhenjie.nohttp.rest.RestRequest;
import com.yanzhenjie.nohttp.rest.StringRequest;

import org.json.JSONObject;


/**
 * Created by ${wjw} on 2016/3/31.
 */
public class BeanJsonResult<T> extends Request<T> {
    private Class<T> clazz;
    private static TokenOverdueLinstener tokenRestLinstener;
    private static HttpRequestSuccessListener httpRequestSuccessListener;

    public BeanJsonResult(String url, RequestMethod requestMethod, Class<T> clazz) {
        super(url, requestMethod);
        this.clazz = clazz;
    }

    public static void setTokenRestLinstener(TokenOverdueLinstener tokenRestLinstener) {
        BeanJsonResult.tokenRestLinstener = tokenRestLinstener;
    }


    public BeanJsonResult(String url, Class<T> clazz) {
        this(url, RequestMethod.GET, clazz);
    }

    @Override
    public T parseResponse(Headers responseHeaders, byte[] responseBody) {
        String res = StringRequest.parseResponseString(responseHeaders, responseBody);
        try {
            return JSON.parseObject(res, clazz);
        } catch (Exception e) {
            Logger.e(e);
            try {
                // 所以传进来的JavaBean一定要提供默认无参构造
                return clazz.newInstance();
            } catch (Exception e1) {
                e1.printStackTrace();
            }
        }
        return null;
    }

    public interface HttpRequestSuccessListener {
        void toSuccess(Activity activity, int what,boolean showToast, JSONObject response, OnRequestListener requestListener, TokenOverdueLinstener overdueLinstener);
    }

    public interface TokenOverdueLinstener {
        void tokenRest(Activity activity, boolean isShowLoading, String loadingText, Request request, String methodName, OnRequestListener requestListener);

        //是否要执行登录或者刷新token
        boolean isOverdue(Activity activity);

        void toSetGps(String methodName, Activity activity);

        void toLogin(Activity activity);
    }

    public static void setHttpRequestSuccessListener(HttpRequestSuccessListener httpRequestSuccessListener) {
        BeanJsonResult.httpRequestSuccessListener = httpRequestSuccessListener;
    }

    public static void execute(Activity activity, Request request, String loadtext, boolean isShowToast, String methodName, OnRequestListener requestListener) {
        execute(activity, true, loadtext, isShowToast, request, methodName, requestListener);
    }

    public static void execute(Activity activity, Request request, boolean isShow, boolean isShowToast, String methodName, OnRequestListener requestListener) {
        execute(activity, isShow, "", isShowToast, request, methodName, requestListener);
    }


    public static void execute(Activity activity, boolean isShowLoading, String loadingText, boolean isShowToast, Request request, String methodName, OnRequestListener requestListener) {
        if (activity != null && !activity.isFinishing()) {
            if (isShowLoading) {
                BaseActivity b = (BaseActivity) activity;
                if (TextUtils.isEmpty(loadingText))
                    b.showProgress();
                else b.showProgress(loadingText);
            }
        }
        if (tokenRestLinstener != null) {
            tokenRestLinstener.toSetGps(methodName, activity);
            if (tokenRestLinstener.isOverdue(activity)) {
                if (activity != null && isShowLoading) {
                    BaseActivity b = (BaseActivity) activity;
                    b.hideProgress();
                }
                tokenRestLinstener.toLogin(activity);
            } else {
                addRequest(activity, isShowLoading, loadingText, isShowToast, request, methodName, requestListener);
            }
        } else {
            addRequest(activity, isShowLoading, loadingText, isShowToast, request, methodName, requestListener);
        }
    }

    public static void addRequest(final Activity activity, final boolean isShowLoading, String loadingText, final boolean isShowToast, final Request request, final String methodName, final OnRequestListener requestListener) {
        CallServer.getRequestInstance().add(activity, request, new HttpCallBack<JSONObject>() {
            @Override
            public void onSucceed(int what, JSONObject response) {
                if (activity != null && activity.isFinishing()) {
                    return;
                }
                DLog.d("IMAHTTP", methodName + ">>出参>>" + response.toString());
                if (httpRequestSuccessListener != null) {
                    httpRequestSuccessListener.toSuccess(activity, what,isShowToast, response, requestListener, tokenRestLinstener);
                }
                onFinish();
            }

            @Override
            public void onFailed(int what, Exception exception, String error) {
                if (activity != null && activity.isFinishing()) {
                    return;
                }
                onFinish();
                if (isShowToast) {
                    Toast.getInstance().showWarningToast(activity, error);
                }
                requestListener.requestFailed(what, exception, error);

            }

            @Override
            public void onFinish() {
                if (activity != null && isShowLoading) {
                    BaseActivity b = (BaseActivity) activity;
                    b.hideProgress();
                }
                requestListener.requestFinish();
            }

            @Override
            public void onTokenOverdue() {
                if (tokenRestLinstener != null) {
                    SPUtils.getInstance().clear(activity);
                    tokenRestLinstener.toLogin(activity);
                }
            }

        }, 0, false);
    }

    public static void execute(final Context context, final Request request, final OnRequestListener requestListener) {
        CallServer.getRequestInstance().add(context, request, new HttpCallBack<JSONObject>() {
            @Override
            public void onSucceed(int what, JSONObject response) {
            }

            @Override
            public void onFailed(int what, Exception exception, String error) {

            }

            @Override
            public void onFinish() {
            }

            @Override
            public void onTokenOverdue() {
            }

        }, 0, false);
    }
}
