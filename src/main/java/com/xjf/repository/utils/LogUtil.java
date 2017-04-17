package com.xjf.repository.utils;

import android.os.Environment;
import android.text.TextUtils;
import android.util.Log;

import com.orhanobut.logger.Logger;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * -----------------------------------------------------------------
 * User:xijiufu
 * Email:xjfsml@163.com
 * Version:1.0
 * Time:2016/7/18--14:03
 * Function:日志工具  可写入文件  方便调式
 * ModifyHistory:
 * -----------------------------------------------------------------
 */
public class LogUtil {

    private static String customTagPrefix = "ZhaiTianXia_Log";
    
    //日志文件开关  true：输出日志    false：关闭日志
    public static boolean isOutPutLog = true;
    // 日志写入文件开关
    public static boolean isWriteToFile = false;

    private static char MYLOG_TYPE = 'v';// 输入日志类型，w代表只输出告警信息等，v代表输出所有信息
    private static String MYLOG_PATH_SDCARD_DIR = Environment.getExternalStorageDirectory().getAbsolutePath() + "/xnongji/log/shop";// 日志文件在sdcard中的路径
    private static SimpleDateFormat myLogSdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");// 日志的输出格式
    private static SimpleDateFormat logfile = new SimpleDateFormat("yyyy-MM-dd");// 日志文件格式

    private LogUtil() {

    }

    /**
     * 初始化 日志文件
     */
    public static void init() {
        File fl = new File(MYLOG_PATH_SDCARD_DIR);
        if (!fl.exists()) {
            fl.mkdirs();
        }
        delFile();

        Logger.init("xijiufu").methodCount(5);
    }


    private static String generateTag() {
        StackTraceElement caller = new Throwable().getStackTrace()[2];
        String tag = "%s.%s(L:%d)";
        String callerClazzName = caller.getClassName();
        callerClazzName = callerClazzName.substring(callerClazzName.lastIndexOf(".") + 1);
        tag = String.format(tag, callerClazzName, caller.getMethodName(), caller.getLineNumber());
        tag = TextUtils.isEmpty(customTagPrefix) ? tag : customTagPrefix + ":" + tag;
        return tag;
    }

    public static void d(String content) {
        if (!isOutPutLog) return;
        String tag = generateTag();

        log(tag, content, 'd');
    }

    public static void d(String content, Throwable tr) {
        if (!isOutPutLog) return;
        String tag = generateTag();

        log(tag, content + tr.toString(), 'd');
    }

    public static void e(String content) {
        if (!isOutPutLog) return;
        String tag = generateTag();

        log(tag, content, 'e');
    }

    public static void e(String content, Throwable tr) {
        if (!isOutPutLog) return;
        String tag = generateTag();

        log(tag, content + tr.toString(), 'e');
    }

    public static void i(String content) {
        if (!isOutPutLog) return;
        String tag = generateTag();

        log(tag, content, 'i');
    }

    public static void i(String content, Throwable tr) {
        if (!isOutPutLog) return;
        String tag = generateTag();

        log(tag, content + tr.toString(), 'i');
    }

    public static void v(String content) {
        if (!isOutPutLog) return;
        String tag = generateTag();

        log(tag, content, 'v');
    }

    public static void v(String content, Throwable tr) {
        if (!isOutPutLog) return;
        String tag = generateTag();

        log(tag, content + tr.toString(), 'v');
    }

    public static void w(String content) {
        if (!isOutPutLog) return;
        String tag = generateTag();
        log(tag, content, 'w');
    }

    public static void w(String content, Throwable tr) {
        if (!isOutPutLog) return;
        String tag = generateTag();

        log(tag, content + tr.toString(), 'w');
    }

    public static void w(Throwable tr) {
        if (!isOutPutLog) return;
        String tag = generateTag();

        log(tag, tr.toString(), 'w');
    }

    /**
     * 日志管理
     *
     * @param tag
     * @param msg
     * @param level
     */
    private static void log(String tag, String msg, char level) {
        if ('e' == level && ('e' == MYLOG_TYPE || 'v' == MYLOG_TYPE)) {
            Logger.e(msg);
//            Log.e(tag, msg);
        } else if ('w' == level && ('w' == MYLOG_TYPE || 'v' == MYLOG_TYPE)) {
            Logger.w(msg);
//            Log.w(tag, msg);
        } else if ('d' == level && ('d' == MYLOG_TYPE || 'v' == MYLOG_TYPE)) {
            Logger.d(msg);
//            Log.d(tag, msg);
        } else if ('i' == level && ('d' == MYLOG_TYPE || 'v' == MYLOG_TYPE)) {
            Logger.i(msg);
//            Log.i(tag, msg);
        } else {
            Logger.v(msg);
//            Log.v(tag, msg);
        }

        //输出到文件
        if (isWriteToFile) {
            writeLogToFile(String.valueOf(level), tag, msg);
        }
    }

    /**
     * 打开日志文件并写入日志
     *
     * @return
     **/
    private static void writeLogToFile(String mylogtype, String tag, String text) {// 新建或打开日志文件
        Date nowTime = new Date();
        String needWriteFiel = logfile.format(nowTime);
        String needWriteMessage = myLogSdf.format(nowTime) + "    " + mylogtype + "    " + tag + "    " + text;
        File file = new File(MYLOG_PATH_SDCARD_DIR, needWriteFiel + ".txt");
        try {
            FileWriter filerWriter = new FileWriter(file, true);//后面这个参数代表是不是要接上文件中原来的数据，不进行覆盖
            BufferedWriter bufWriter = new BufferedWriter(filerWriter);
            bufWriter.write(needWriteMessage);
            bufWriter.newLine();
            bufWriter.close();
            filerWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void makeRootDirectory(String filePath) {
        File file = null;
        try {
            file = new File(filePath);
            if (!file.exists()) {
                file.mkdir();
            }
        } catch (Exception e) {

        }
    }

    /**
     * 删除非本月的日志文件
     */
    public static void delFile() {
        File fl = new File(MYLOG_PATH_SDCARD_DIR);
        Date nowTime = new Date();
        String curMonth = logfile.format(nowTime).substring(0, 7);
        getAllFiles(fl, curMonth);
    }

    // 遍历接收一个文件路径，然后把文件子目录中的所有文件遍历并输出来
    private static void getAllFiles(File root, String curDate) {
        File files[] = root.listFiles();
        if (files != null) {
            for (File f : files) {
                if (f.isDirectory()) {
                    getAllFiles(f, curDate);
                } else {
                    if (!f.getName().substring(0, 7).equalsIgnoreCase(curDate)) {
                        f.delete();
                    }
                }
            }
        }
    }

}
