package com.example.framwork.base;

import android.app.Activity;
import android.content.Context;
import android.text.TextUtils;

import com.example.framwork.baseapp.BaseAppConfig;
import com.example.framwork.bean.BaseRequestBean;
import com.example.framwork.noHttp.BeanJsonResult;
import com.example.framwork.noHttp.OnRequestListener;
import com.example.framwork.utils.DLog;
import com.example.framwork.utils.JsonParseControl;
import com.yanzhenjie.nohttp.Headers;
import com.yanzhenjie.nohttp.NoHttp;
import com.yanzhenjie.nohttp.RequestMethod;
import com.yanzhenjie.nohttp.rest.Request;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;


/**
 * Created by wanjingyu on 2016/10/8.
 */

public class CommonModel {
    public static final String IMAHTTP = "IMAHTTP";
    public AddHeaderListener listener;

    public interface AddHeaderListener {
        void addHeader(Request request);
    }

    public void setAddHeaderListener(AddHeaderListener listener) {
        this.listener = listener;
    }

    /*
        * 一般post请求是调用*/
    public void resultPostModel(Activity activity, HashMap info, String methodName, boolean isShowLoading, String loadingText, boolean isShowToast, OnRequestListener requestListener) {
        Request<JSONObject> request = NoHttp.createJsonObjectRequest(BaseAppConfig.SERVICE_PATH + methodName,
                RequestMethod.POST);
        if (info != null) {
            DLog.d(IMAHTTP, methodName + ">>Post入参>>" + JsonParseControl.getJsonParams(info));
            request.add(info);
        }
        if (listener != null) {
            listener.addHeader(request);
        }
        modeExecute(activity, request, isShowLoading, loadingText, isShowToast, methodName, requestListener);
    }

    /*
        * 一般post请求是调用*/
    public void resultGettModel(Activity activity, HashMap info, String methodName, boolean isShowLoading, String loadingText, boolean isShowToast, OnRequestListener requestListener) {
        Request<JSONObject> request = NoHttp.createJsonObjectRequest(BaseAppConfig.SERVICE_PATH + methodName,
                RequestMethod.GET);
        if (info != null) {
            DLog.d(IMAHTTP, methodName + ">>Get入参>>" + JsonParseControl.getJsonParams(info));
            request.add(info);
        }
        if (listener != null) {
            listener.addHeader(request);
        }
        modeExecute(activity, request, isShowLoading, loadingText, isShowToast, methodName, requestListener);
    }

    /*
          * 一般post请求是调用*/
    public void resultPostJsonModel2(Activity activity, String path, String methodName, Object info, boolean isShowLoading, String loadingText, boolean isShowToast, OnRequestListener requestListener) {
        String jsonString = JsonParseControl.getJsonParams(info);
        Request<JSONObject> request = NoHttp.createJsonObjectRequest(path,
                RequestMethod.POST);
        if (info != null) {
            DLog.d(IMAHTTP, "接口地址：" + path);
            DLog.d(IMAHTTP, methodName + ">>Post入参>>" + jsonString);
            request.setDefineRequestBody(jsonString, Headers.HEAD_VALUE_CONTENT_TYPE_JSON);
        }
        modeExecute(activity, request, isShowLoading, loadingText, isShowToast, methodName, requestListener);
    }

    /**
     * 对象post请求是调用
     *
     * @param activity
     * @param o               入参类
     * @param methodName      方法名
     * @param requestListener
     */
    public void resultPostJsonModel(Activity activity, Object o, String methodName, boolean isShowLoading, String loadingText, boolean isShowToast, OnRequestListener requestListener) {
        String jsonString = JsonParseControl.getJsonParams(o);
        Request<JSONObject> request = NoHttp.createJsonObjectRequest(BaseAppConfig.SERVICE_PATH + methodName,
                RequestMethod.POST);
        if (o != null) {
            DLog.d(IMAHTTP, "接口地址：" + BaseAppConfig.SERVICE_PATH + methodName);
            DLog.d(IMAHTTP, methodName + ">>Post入参>>" + jsonString);
            request.setDefineRequestBody(jsonString, "application/json");
        }
        if (listener != null) {
            listener.addHeader(request);
        }
        modeExecute(activity, request, isShowLoading, loadingText, isShowToast, methodName, requestListener);
    }

    public void resultPostJsonModel(Context context, Object o, String methodName, boolean isShowLoading, String loadingText, boolean isShowToast, OnRequestListener requestListener) {
        String jsonString = JsonParseControl.getJsonParams(o);
        Request<JSONObject> request = NoHttp.createJsonObjectRequest(BaseAppConfig.SERVICE_PATH + methodName,
                RequestMethod.POST);
        if (o != null) {
            DLog.d(IMAHTTP, "接口地址：" + BaseAppConfig.SERVICE_PATH + methodName);
            DLog.d(IMAHTTP, methodName + ">>Post入参>>" + jsonString);
            request.setDefineRequestBody(jsonString, "application/json");
        }
        if (listener != null) {
            listener.addHeader(request);
        }
        BeanJsonResult.execute(context, request, requestListener);
    }

    public void resultGetModel(Activity activity, BaseRequestBean requestInfo, String methodName, boolean isShowLoading, String loadingText, boolean isShowToast, OnRequestListener requestListener) {
        Request<JSONObject> request = NoHttp.createJsonObjectRequest(BaseAppConfig.SERVICE_PATH + methodName,
                RequestMethod.GET);
        if (requestInfo != null) {
            DLog.d(IMAHTTP, "接口地址：" + BaseAppConfig.SERVICE_PATH + methodName);
            String jsonString = JsonParseControl.getJsonParams(requestInfo);
            DLog.d(IMAHTTP, methodName + ">>Get入参>>" + jsonString.toString());
            request.add(JsonParseControl.getMapParams(requestInfo));
        }
        if (listener != null) {
            listener.addHeader(request);
        }
        modeExecute(activity, request, isShowLoading, loadingText, isShowToast, methodName, requestListener);
    }

    /*
      * 自定义URL的model*/
    public void resultCustomUrlModel(Activity activity, HashMap info, String URL, boolean isShowLoading, String loadingText, boolean isShowToast, OnRequestListener requestListener) {
        Request<JSONObject> request = NoHttp.createJsonObjectRequest(URL,
                RequestMethod.POST);
        if (info != null) {
            DLog.d(IMAHTTP, ">>入参>>" + JsonParseControl.getJsonParams(info));
            request.add(info);
        }
        if (listener != null) {
            listener.addHeader(request);
        }
        modeExecute(activity, request, isShowLoading, loadingText, isShowToast, URL, requestListener);
    }


    public Request<JSONObject> getRequest(HashMap info, String url, String methodName) {
        Request<JSONObject> request = NoHttp.createJsonObjectRequest(url + methodName, RequestMethod.POST);
        if (info != null) {
            DLog.d(IMAHTTP, methodName + ">>入参>>" + JsonParseControl.getJsonParams(info));
            request.add(info);
        }
        return request;
    }

    public Request<JSONObject> getRequest(BaseRequestBean o, String url, String methodName) {
        Request<JSONObject> request = NoHttp.createJsonObjectRequest(url + methodName, RequestMethod.POST);
        if (o != null) {
            String jsonString = JsonParseControl.getJsonParams(o);
            DLog.d(IMAHTTP, "接口地址：" + url + methodName);
            DLog.d(IMAHTTP, methodName + ">>Post入参>>" + jsonString);
            request.add(JsonParseControl.getMapParams(o));
        }
        return request;
    }

    public Request<JSONObject> getRequest(BaseRequestBean o, String url, String methodName, RequestMethod method) {
        Request<JSONObject> request = NoHttp.createJsonObjectRequest(url + methodName, method);
        if (o != null) {
            String jsonString = JsonParseControl.getJsonParams(o);
            DLog.d(IMAHTTP, "接口地址：" + url + methodName);
            DLog.d(IMAHTTP, methodName + ">>Post入参>>" + jsonString);
            request.add(JsonParseControl.getMapParams(o));
        }
        if (listener != null) {
            listener.addHeader(request);
        }
        return request;
    }

    public Request<JSONObject> getRequest(HashMap info, String methodName) {
        Request<JSONObject> request = NoHttp.createJsonObjectRequest(BaseAppConfig.SERVICE_PATH + methodName, RequestMethod.POST);
        if (info != null) {
            DLog.d(IMAHTTP, "接口地址：" + BaseAppConfig.SERVICE_PATH + methodName);
            DLog.d(IMAHTTP, methodName + ">>入参>>" + JsonParseControl.getJsonParams(info));
            request.add(info);
        }
        return request;
    }

    public Request<JSONObject> getRequest(String methodName) {
        // FastJson
        Request<JSONObject> request = NoHttp.createJsonObjectRequest(BaseAppConfig.SERVICE_PATH + methodName, RequestMethod.POST);
        return request;
    }

    public Request<JSONObject> getRequest(String url, String methodName) {
        // FastJson
        Request<JSONObject> request = NoHttp.createJsonObjectRequest(url + methodName, RequestMethod.POST);
        return request;
    }

    /**
     * 需要配合resultFileModel
     *
     * @param activity
     * @param request
     * @param requestListener
     */
    public void execute(Activity activity, Request request, boolean isShowLoading, String loadingText, boolean isShowToast, String methodName, OnRequestListener requestListener) {
        modeExecute(activity, request, isShowLoading, loadingText, isShowToast, methodName, requestListener);
    }

    public void modeExecute(Activity activity, Request request, boolean isShowLoading, String loadingText, boolean isShowToast, String methodName, OnRequestListener requestListener) {
        if (isShowLoading) {
            if (TextUtils.isEmpty(loadingText)) {
                BeanJsonResult.execute(activity, request, true, isShowToast, methodName, requestListener);
            } else {
                BeanJsonResult.execute(activity, request, loadingText, isShowToast, methodName, requestListener);
            }
        } else {
            BeanJsonResult.execute(activity, request, false, isShowToast, methodName, requestListener);
        }
    }
}
