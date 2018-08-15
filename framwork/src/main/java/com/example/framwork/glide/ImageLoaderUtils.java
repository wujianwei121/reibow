package com.example.framwork.glide;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.bumptech.glide.request.RequestOptions;
import com.example.framwork.utils.glide.GlideRoundTransform;

import jp.wasabeef.glide.transformations.BlurTransformation;


/**
 * Description : 图片加载工具类 使用glide框架封装
 */
public class ImageLoaderUtils {

    public static void display(Context context, ImageView imageView, String url, int placeholder, int error) {
        if (imageView == null) {
            throw new IllegalArgumentException("argument error");
        }
        if (url == null || TextUtils.isEmpty(url)) {
            imageView.setImageResource(error);
        } else {
            RequestOptions options = new RequestOptions();
            if (placeholder != 0) {
                options.placeholder(placeholder);
            }
            if (error != 0) {
                options.error(error);
            }
            options.diskCacheStrategy(DiskCacheStrategy.ALL);
            options.skipMemoryCache(false);
            options.centerCrop();
            Glide.with(context).load(url).apply(options).thumbnail(0.3f).into(imageView);
        }
    }

    public static void display(Context context, ImageView imageView, String url, int error) {
        if (imageView == null) {
            throw new IllegalArgumentException("argument error");
        }
        display(context, imageView, url, 0, error);
    }

    public static void display(Context context, ImageView imageView, String url, int error, int width, int height) {
        if (imageView == null) {
            throw new IllegalArgumentException("argument error");
        }
        RequestOptions options = new RequestOptions();
        if (error != 0) {
            options.error(error);
        }
        options.override(width, height);
        options.diskCacheStrategy(DiskCacheStrategy.ALL);
        options.skipMemoryCache(false);
        Glide.with(context).load(url).apply(options).thumbnail(0.3f).into(imageView);
    }

    public static void display(Context context, ImageView imageView, String url, Drawable error, int width, int height) {
        if (imageView == null) {
            throw new IllegalArgumentException("argument error");
        }
        RequestOptions options = new RequestOptions();
        if (error != null) {
            options.error(error);
        }
        options.override(width, height);
        options.diskCacheStrategy(DiskCacheStrategy.ALL);
        options.skipMemoryCache(false);
        Glide.with(context).load(url).apply(options).thumbnail(0.3f).into(imageView);
    }

    public static void display(Context context, ImageView imageView, String url) {
        if (imageView == null) {
            throw new IllegalArgumentException("argument error");
        }
        display(context, imageView, url, 0, 0);
    }


    public static void display(Context context, ImageView imageView, int url) {
        if (imageView == null) {
            throw new IllegalArgumentException("argument error");
        }
        RequestOptions options = new RequestOptions();
        options.diskCacheStrategy(DiskCacheStrategy.ALL);
        options.skipMemoryCache(false);
        Glide.with(context).load(url).apply(options).thumbnail(0.3f).into(imageView);
    }

    public static void displayCircle(Context context, ImageView imageView, String url) {
        if (imageView == null) {
            throw new IllegalArgumentException("argument error");
        }
        displayCircle(context, imageView, url, 0, 0);
    }

    public static void displayCircle(Context context, ImageView imageView, String url, int error) {
        if (imageView == null) {
            throw new IllegalArgumentException("argument error");
        }
        displayCircle(context, imageView, url, 0, error);
    }

    public static void displayCircle(Context context, ImageView imageView, String url, int placeholder, int error) {
        if (imageView == null) {
            throw new IllegalArgumentException("argument error");
        }
        RequestOptions options = new RequestOptions();
        if (placeholder != 0) {
            options.placeholder(placeholder);
        }
        if (error != 0) {
            options.error(error);
        }
        options.skipMemoryCache(false);
        options.diskCacheStrategy(DiskCacheStrategy.ALL).bitmapTransform(new CircleCrop());
        Glide.with(context).load(url).apply(options).into(imageView);
    }

    public static void displayBlur(Context context, ImageView imageView, int url) {
        if (imageView == null) {
            throw new IllegalArgumentException("argument error");
        }
        Glide.with(context).load(url).apply(RequestOptions.bitmapTransform(new BlurTransformation(25))).into(imageView);
    }

    public static void displayRound(Context context, ImageView imageView, String url) {
        if (imageView == null) {
            throw new IllegalArgumentException("argument error");
        }
        RequestOptions options = new RequestOptions();
        options.transform(new GlideRoundTransform(5));
        options.skipMemoryCache(false);
        options.diskCacheStrategy(DiskCacheStrategy.ALL);
        Glide.with(context).load(url).apply(options).into(imageView);
    }
}
