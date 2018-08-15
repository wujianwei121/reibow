package com.example.framwork.utils;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.view.View;

import com.example.framwork.R;
import com.example.framwork.widget.LoadDataLayout;


/**
 * Created by ${wjw} on 2016/4/15.
 */
public class LoadDataLayoutUtils {
    private LoadDataLayout loadDataLayout;
    private LoadDataLayout.OnReloadListener onReloadListener;

    public LoadDataLayoutUtils(LoadDataLayout loadDataLayout, LoadDataLayout.OnReloadListener onReloadListener) {
        this.loadDataLayout = loadDataLayout;
        this.onReloadListener = onReloadListener;
        if (onReloadListener != null) {
            loadDataLayout.setOnReloadListener(onReloadListener);
        }
    }

    /**
     * 显示内容
     */
    public void showContent() {
        if (loadDataLayout != null) {
            loadDataLayout.setStatus(LoadDataLayout.SUCCESS);
        }
    }

    public void showLoading(String s) {
        if (loadDataLayout != null) {
            loadDataLayout.setLoadingText(s);
            loadDataLayout.setStatus(LoadDataLayout.LOADING);
        }
    }

    public void showLoading(String s, int layoutid) {
        if (loadDataLayout != null) {
            loadDataLayout.setLoadingViewLayoutId(layoutid);
            loadDataLayout.setLoadingText(s);
            loadDataLayout.setStatus(LoadDataLayout.LOADING);
        }
    }

    public void showNoWifiError(String error) {
        if (loadDataLayout != null) {
            loadDataLayout.setNoNetWorkText(error);
            loadDataLayout.setStatus(LoadDataLayout.NO_NETWORK);
        }
    }

    public void showNoWifiError(String error, int icon) {
        if (loadDataLayout != null) {
            loadDataLayout.setNoNetWorkText(error);
            loadDataLayout.setNoNetWorkImage(icon);
            loadDataLayout.setStatus(LoadDataLayout.NO_NETWORK);
        }
    }

    public LoadDataLayout showEmpty(String error) {
        if (loadDataLayout != null) {
            loadDataLayout.setEmptyText(error);
            loadDataLayout.setStatus(LoadDataLayout.EMPTY);
        }
        return loadDataLayout;
    }

    public void showEmpty(String error, int icon) {
        if (loadDataLayout != null) {
            loadDataLayout.setEmptyText(error);
            loadDataLayout.setEmptyImage(icon);
            loadDataLayout.setStatus(LoadDataLayout.EMPTY);
        }
    }

    public void showError(String error) {
        if (loadDataLayout != null) {
            loadDataLayout.setErrorText(error);
            loadDataLayout.setStatus(LoadDataLayout.ERROR);
        }
    }

    public void showError(String error, int icon) {
        if (loadDataLayout != null) {
            loadDataLayout.setErrorText(error);
            loadDataLayout.setErrorImage(icon);
            loadDataLayout.setStatus(LoadDataLayout.ERROR);
        }
    }

    public void showReloadBtn(boolean show) {
        if (loadDataLayout != null) {
            loadDataLayout.setReloadBtnVisible(show);
        }
    }

    public LoadDataLayout getLoadDataLayout() {
        return loadDataLayout;
    }
}
