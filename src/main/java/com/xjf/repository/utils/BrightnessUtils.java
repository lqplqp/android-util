package com.xjf.repository.utils;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Context;
import android.net.Uri;
import android.provider.Settings;
import android.view.WindowManager;

/**
 * -----------------------------------------------------------------
 * User:xijiufu
 * Email:xjfsml@163.com
 * Version:1.0
 * Time:2016/12/13--17:07
 * Function:/*
 * Android调节屏幕亮度工具类
 * by itas109
 * http://blog.csdn.net/itas109
 * <p>
 * 注意：需要添加setting权限
 * <uses-permission android:name="android.permission.WRITE_SETTINGS">
 * ModifyHistory:
 * -----------------------------------------------------------------
 */
public class BrightnessUtils {
    // 判断是否开启了自动亮度调节

    public static boolean IsAutoBrightness(Context context) {
        boolean IsAutoBrightness = false;
        try {
            IsAutoBrightness = Settings.System.getInt(
                    context.getContentResolver(),
                    Settings.System.SCREEN_BRIGHTNESS_MODE) == Settings.System.SCREEN_BRIGHTNESS_MODE_AUTOMATIC;
        } catch (Settings.SettingNotFoundException e) {
            e.printStackTrace();
        }
        return IsAutoBrightness;
    }
    
    // 获取当前屏幕的亮度
    public static int getScreenBrightness(Context context) {
        int nowBrightnessValue = 0;
        ContentResolver resolver = context.getContentResolver();
        try {
            nowBrightnessValue = Settings.System.getInt(
                    resolver, Settings.System.SCREEN_BRIGHTNESS);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return nowBrightnessValue;
    }

    // 设置亮度
    // 程序退出之后亮度失效

    public static void setCurWindowBrightness(Context context, int brightness) {
        // 如果开启自动亮度，则关闭。否则，设置了亮度值也是无效的
        if (IsAutoBrightness(context)) {
            stopAutoBrightness(context);
        }

        // context转换为Activity
        Activity activity = (Activity) context;
        WindowManager.LayoutParams lp = activity.getWindow().getAttributes();

        // 异常处理
        if (brightness < 1) {
            brightness = 1;
        }

        // 异常处理
        if (brightness > 255) {
            brightness = 255;
        }

        lp.screenBrightness = Float.valueOf(brightness) * (1f / 255f);
        activity.getWindow().setAttributes(lp);
    }

    // 设置系统亮度
    // 程序退出之后亮度依旧有效
    public static void setSystemBrightness(Context context, int brightness) {
        // 异常处理
        if (brightness < 1) {
            brightness = 1;
        }

        // 异常处理
        if (brightness > 255) {
            brightness = 255;
        }
        saveBrightness(context, brightness);
    }

    // 停止自动亮度调节

    public static void stopAutoBrightness(Context context) {
        Settings.System.putInt(context.getContentResolver(),
                Settings.System.SCREEN_BRIGHTNESS_MODE,
                Settings.System.SCREEN_BRIGHTNESS_MODE_MANUAL);
    }

    // 开启亮度自动调节

    public static void startAutoBrightness(Context context) {
        Settings.System.putInt(context.getContentResolver(),
                Settings.System.SCREEN_BRIGHTNESS_MODE,
                Settings.System.SCREEN_BRIGHTNESS_MODE_AUTOMATIC);
    }

    // 保存亮度设置状态
    public static void saveBrightness(Context context, int brightness) {
        Uri uri = Settings.System.getUriFor(Settings.System.SCREEN_BRIGHTNESS);
        Settings.System.putInt(context.getContentResolver(), Settings.System.SCREEN_BRIGHTNESS, brightness);
        context.getContentResolver().notifyChange(uri, null);
    }
}
//===========================================================================// 
//                                                                           // 
//                            _ooOoo_                                        // 
//                           o8888888o                                       // 
//                           88" . "88                                       // 
//                           (| -_- |)                                       // 
//                            O\ = /O                                        // 
//                        ____/`---'\____                                    // 
//                      .   ' \\| |// `.                                     // 
//                       / \\||| : |||// \                                   // 
//                     / _||||| -:- |||||- \                                 // 
//                       | | \\\ - /// | |                                   // 
//                     | \_| ''\---/'' | |                                   // 
//                      \ .-\__ `-` ___/-. /                                 // 
//                   ___`. .' /--.--\ `. . __                                // 
//                ."" '< `.___\_<|>_/___.' >'"".                             // 
//               | | : `- \`.;`\ _ /`;.`/ - ` : | |                          // 
//                 \ \ `-. \_ __\ /__ _/ .-` / /                             // 
//         ======`-.____`-.___\_____/___.-`____.-'======                     // 
//                            `=---='                                        // 
//                                                                           // 
//         .............................................                     // 
//                  佛祖镇楼                  BUG辟易                        //       
//          佛曰:                                                            //   
//                  写字楼里写字间，写字间里程序员；                         //                 
//                  程序人员写程序，又拿程序换酒钱。                         //                 
//                  酒醒只在网上坐，酒醉还来网下眠；                         //                 
//                  酒醉酒醒日复日，网上网下年复年。                         //                 
//                  但愿老死电脑间，不愿鞠躬老板前；                         //                 
//                  奔驰宝马贵者趣，公交自行程序员。                         //                 
//                  别人笑我忒疯癫，我笑自己命太贱；                         //                 
//                  不见满街漂亮妹，哪个归得程序员？                         //                 
//===========================================================================// 