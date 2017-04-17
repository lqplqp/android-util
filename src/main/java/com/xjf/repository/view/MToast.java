package com.xjf.repository.view;

import android.widget.Toast;

/**
 * -----------------------------------------------------------------
 * User:xijiufu
 * Email:xjfsml@163.com
 * Version:1.0
 * Time:2016/10/31--11:00
 * Function:
 * ModifyHistory:
 * -----------------------------------------------------------------
 */
public class MToast {

    private MToast() {
    }

    /**
     * Toast短暂提示
     *
     * @param content 提示内容
     */
    public static void showToast(String content) {
        DisplayToast.getInstance().display(content, Toast.LENGTH_SHORT);
    }

    /**
     * Toast长提示
     *
     * @param content 提示内容
     */
    public static void longToast(String content) {
        DisplayToast.getInstance().display(content, Toast.LENGTH_LONG);
    }


    /**
     * Toast自定义提示时间
     *
     * @param content  提示内容
     * @param duration 显示时间
     */
    public static void timeToast(String content, int duration) {
        DisplayToast.getInstance().display(content, duration);
    }

    /**
     * Toast短暂提示
     *
     * @param resId 从资源文件中获取提示文本
     */
    public static void showToast(int resId) {
        DisplayToast.getInstance().display(resId, Toast.LENGTH_SHORT);
    }


    /**
     * Toast长提示
     *
     * @param resId 从资源文件中获取提示文本
     */
    public static void longToast(int resId) {
        DisplayToast.getInstance().display(resId, Toast.LENGTH_LONG);
    }

    /**
     * Toast自定义提示时间
     *
     * @param resId 从资源文件中获取提示文本
     */
    public static void timeToast(int resId, int duration) {
        DisplayToast.getInstance().display(resId, duration);
    }

    /**
     * 取消Toast
     */
    public static void cancel() {
        DisplayToast.getInstance().dismiss();
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