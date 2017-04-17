package com.xjf.repository.utils;

import android.app.Activity;
import android.net.Uri;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.io.File;
import java.util.concurrent.ExecutionException;

import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.FutureTarget;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.xjf.repository.R;
import com.xjf.repository.view.CircleImageView;
import com.xjf.repository.view.GlideCircleTransform;

/**
 * -----------------------------------------------------------------
 * User:xijiufu
 * Email:xjfsml@163.com
 * Version:1.0
 * Time:2016/10/10--17:28
 * Function:
 * ModifyHistory:
 * -----------------------------------------------------------------
 */
public class ImageLoaderUtils {

    private ImageLoaderUtils() {
    }

    /**
     * 加载本地的图片
     *
     * @param activity
     * @param path
     * @param imageView
     */
    public static void displayLocalImage(Activity activity, String path, ImageView imageView) {
        Glide.with(activity)                             //配置上下文
                .load(Uri.fromFile(new File(path)))      //设置图片路径(fix #8,文件名包含%符号 无法识别和显示)
                .error(R.mipmap.default_image)           //设置错误图片
                .placeholder(R.mipmap.default_image)     //设置占位图片
                .diskCacheStrategy(DiskCacheStrategy.ALL)//缓存全尺寸
                .into(imageView);


    }

    /**
     * 加载本地与网络资源
     *
     * @param activity
     * @param url
     * @param imageView
     */
    public static void displayUrlImage(Activity activity, String url, ImageView imageView) {
        Glide.with(activity)                                   //配置上下文
                .load(url)                                     //设置图片路径(fix #8,文件名包含%符号 无法识别和显示)
                .error(R.mipmap.default_image)                 //设置错误图片
                .placeholder(R.mipmap.default_image)           //设置占位图片
                .diskCacheStrategy(DiskCacheStrategy.RESULT)   //缓存全尺寸  缓存变化后的
                .into(imageView);

    }
    
    /**
     * 获取图片缓存的路径
     *
     * @param activity
     * @param url
     * @return
     */
    public static String getImgPathFromCache(Activity activity, String url) {
        FutureTarget<File> future = Glide.with(activity).load(url).downloadOnly(100, 100);
        try {
            File cacheFile = future.get();
            return cacheFile.getAbsolutePath();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    /**
     * 加载圆形图片
     *
     * @param activity
     * @param url
     * @param imageView
     */
    public static void displayCircleUrlImage(Activity activity, String url, ImageView imageView) {
        Glide.with(activity)
                .load(url)
                .transform(new GlideCircleTransform(activity))
                .error(R.mipmap.default_image)                 //设置错误图片
                .placeholder(R.mipmap.default_image)
                .diskCacheStrategy(DiskCacheStrategy.RESULT)   //缓存全尺寸  缓存变化后的
                .into(imageView);
    }

    /**
     * 设置CircleImageView  圆形头像
     *
     * @param activity
     * @param url
     * @param imageView
     */
    public static void displayCircleImageUrl(Activity activity, String url, final CircleImageView imageView) {
        Glide.with(activity)
                .load(url)
                .placeholder(R.mipmap.default_image)
                .error(R.mipmap.default_image)                 //设置错误图片
                .diskCacheStrategy(DiskCacheStrategy.RESULT)   //缓存全尺寸  缓存变化后的
                .into(new SimpleTarget<GlideDrawable>() {
                    @Override
                    public void onResourceReady(GlideDrawable resource, GlideAnimation<? super GlideDrawable> glideAnimation) {
                        imageView.setImageDrawable(resource);
                    }
                });
    }

    /**
     * 设置资源文件
     *
     * @param activity
     * @param resId
     * @param imageView
     */
    public static void displayResIdImageUrl(Activity activity, int  resId, final CircleImageView imageView) {
        Glide.with(activity)
                .load(resId)
                .placeholder(R.mipmap.default_image)
                .error(R.mipmap.default_image)                 //设置错误图片
                .diskCacheStrategy(DiskCacheStrategy.RESULT)   //缓存全尺寸  缓存变化后的
                .into(new SimpleTarget<GlideDrawable>() {
                    @Override
                    public void onResourceReady(GlideDrawable resource, GlideAnimation<? super GlideDrawable> glideAnimation) {
                        imageView.setImageDrawable(resource);
                    }
                });
    }

}
