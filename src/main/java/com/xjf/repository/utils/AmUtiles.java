package com.xjf.repository.utils;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import java.util.Calendar;


/**
 * -----------------------------------------------------------------
 * User:xijiufu
 * Email:xjfsml@163.com
 * Version:1.0
 * Time:2017/4/18--0:43
 * Function:  闹钟定时器
 * ModifyHistory:
 * -----------------------------------------------------------------
 */
public class AmUtiles {
    private static AlarmManager am;
    private static PendingIntent pendingIntent;


    /**
     * 设置一次性闹钟
     * 简化10分钟之后启动一个intent 600代表10分钟   20代表20秒
     */
    public static AlarmManager sendUpdateBroadcastRepeat(Context ctx, Intent intent) {
        pendingIntent = PendingIntent.getBroadcast(ctx, 0, intent, 0);
        am = (AlarmManager) ctx.getSystemService(Context.ALARM_SERVICE);
        am.set(AlarmManager.RTC_WAKEUP, System.currentTimeMillis() + 600 * 1000, pendingIntent);
        return am;
    }

    /**
     * 设置周期性的定时任务
     *
     * @param context
     * @param intent
     * @return
     */
    public static AlarmManager sendRepeatingBroadcastRepeat(Context context, Intent intent) {
        pendingIntent = PendingIntent.getBroadcast(context, 0, intent, 0);
        am = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        /**
         * 第一个参数表示闹钟类型int type指定定时服务的类型，该参数接受如下值：
         ELAPSED_REALTIME： 在指定的延时过后，发送广播，但不唤醒设备（闹钟在睡眠状态下不可用）。如果在系统休眠时闹钟触发，它将不会被传递，直到下一次设备唤醒。
         ELAPSED_REALTIME_WAKEUP： 在指定的延时过后，发送广播，并唤醒设备（即使关机也会执行operation所对应的组件） 。
         延时是要把系统启动的时间SystemClock.elapsedRealtime()算进去的，具体用法看代码。
         RTC： 指定当系统调用System.currentTimeMillis()方法返回的值与triggerAtTime相等时启动operation所对应的设备（在指定的时刻，发送广播，但不唤醒设备）。如果在系统休眠时闹钟触发，它将不会被传递，直到下一次设备唤醒（闹钟在睡眠状态下不可用）。
         RTC_WAKEUP： 指定当系统调用System.currentTimeMillis()方法返回的值与triggerAtTime相等时启动operation所对应的设备（在指定的时刻，发送广播，并唤醒设备）。即使系统关机也会执行 operation所对应的组件。
         第二个参数表示闹钟首次执行时间
         第三个参数表示闹钟两次执行的间隔时间
         第四个参数表示闹钟响应动作
         */
        //首次执行十分钟后   之后10分钟执行一次
        am.setRepeating(AlarmManager.RTC_WAKEUP, System.currentTimeMillis() + 10 * 60 * 1000, 10 * 60 * 1000, pendingIntent);
        return am;
    }

    /**
     * 设置省电模式  的定时任务
     *
     * @param context
     * @param intent
     * @return
     */
    public static AlarmManager sendInexactRepeating(Context context, Intent intent) {
        pendingIntent = PendingIntent.getBroadcast(context, 0, intent, 0);
        am = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        /**
         * 第一个参数表示闹钟类型int type指定定时服务的类型，该参数接受如下值：
         ELAPSED_REALTIME： 在指定的延时过后，发送广播，但不唤醒设备（闹钟在睡眠状态下不可用）。如果在系统休眠时闹钟触发，它将不会被传递，直到下一次设备唤醒。
         ELAPSED_REALTIME_WAKEUP： 在指定的延时过后，发送广播，并唤醒设备（即使关机也会执行operation所对应的组件） 。
         延时是要把系统启动的时间SystemClock.elapsedRealtime()算进去的，具体用法看代码。
         RTC： 指定当系统调用System.currentTimeMillis()方法返回的值与triggerAtTime相等时启动operation所对应的设备（在指定的时刻，发送广播，但不唤醒设备）。如果在系统休眠时闹钟触发，它将不会被传递，直到下一次设备唤醒（闹钟在睡眠状态下不可用）。
         RTC_WAKEUP： 指定当系统调用System.currentTimeMillis()方法返回的值与triggerAtTime相等时启动operation所对应的设备（在指定的时刻，发送广播，并唤醒设备）。即使系统关机也会执行 operation所对应的组件。
         第二个参数表示闹钟首次执行时间
         第三个参数intervalTime为闹钟间隔
         INTERVAL_DAY：      设置闹钟，间隔一天
         INTERVAL_HALF_DAY：  设置闹钟，间隔半天
         INTERVAL_FIFTEEN_MINUTES：设置闹钟，间隔15分钟
         INTERVAL_HALF_HOUR：     设置闹钟，间隔半个小时
         INTERVAL_HOUR：  设置闹钟，间隔一个小时
         第四个参数表示闹钟响应动作
         */
        //首次执行当前后十分钟
        am.setInexactRepeating(AlarmManager.RTC_WAKEUP, System.currentTimeMillis() + 10 * 60 * 1000, AlarmManager.INTERVAL_FIFTEEN_MINUTES, pendingIntent);
        return am;
    }


    /**
     * 应用退出了就取消闹钟
     */
    public static void destoryAlarmManger() {
        if (null != am) {
            am.cancel(pendingIntent);
        }
    }


    /**
     * 设置午夜定时器, 午夜12点发送广播, MIDNIGHT_ALARM_FILTER.
     * 实际测试可能会有一分钟左右的偏差.
     *
     * @param context 上下文
     */
    public static void setMidnightAlarm(Context context, String action) {
        Context appContext = context.getApplicationContext();
        Intent intent = new Intent(action);

        PendingIntent pi = PendingIntent.getBroadcast(appContext, 0, intent,
                PendingIntent.FLAG_CANCEL_CURRENT);
        AlarmManager am = (AlarmManager) appContext.getSystemService(
                Context.ALARM_SERVICE);

        // 午夜12点的标准计时, 来源于SO, 实际测试可能会有一分钟左右的偏差.
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.HOUR, 0);
        calendar.set(Calendar.AM_PM, Calendar.AM);
        calendar.add(Calendar.DAY_OF_MONTH, 1);

        // 显示剩余时间
        long now = Calendar.getInstance().getTimeInMillis();
        Log.e("剩余时间(秒): ", ((calendar.getTimeInMillis() - now) / 1000) + "");

        // 设置之前先取消前一个PendingIntent
        am.cancel(pi);
        // 设置每一天的计时器
        am.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(),
                AlarmManager.INTERVAL_DAY, pi);
    }
}
