package com.example.framwork.noHttp;


import com.example.framwork.noHttp.Bean.BaseResponseBean;

/**
 * Created by lenovo on 2017/9/1.
 */

public abstract class OnInterfaceRespListener implements OnRequestListener {
    @Override
    public abstract void requestSuccess(BaseResponseBean bean);

    @Override
    public void requestFinish() {

    }

    @Override
    public void requestFailed(int errorCode, Exception exception, String error) {

    }
}
