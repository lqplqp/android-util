package com.xjf.repository.view;

import android.content.Context;
import android.os.Handler;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.xjf.repository.R;

/**
 * -----------------------------------------------------------------
 * User:xijiufu
 * Email:xjfsml@163.com
 * Version:1.0
 * Time:2016/10/31--10:59
 * Function: 自定义Toast
 * ModifyHistory:
 * -----------------------------------------------------------------
 */
public class DisplayToast {
    private Toast toast;
    private TextView tvToast;

    private Handler handler;

    /**
     * 不能实例化
     */
    private DisplayToast() {
    }

    public static DisplayToast getInstance() {
        return DisplayToastHolder.INSTANCE;
    }

    /**
     * 用静态内部类实现单列
     */
    private static class DisplayToastHolder {
        private static final DisplayToast INSTANCE = new DisplayToast();
    }


    /**
     * 在应用Application中初始化
     *
     * @param context 上下文用Context
     */
    public void init(Context context) {
        View view = LayoutInflater.from(context).inflate(R.layout.view_toast, null);
        tvToast = (TextView) view.findViewById(R.id.tv_toast);
        //初始化Toast并把View设置给它
        toast = new Toast(context);
        toast.setView(view);
        handler = new Handler(context.getMainLooper());
    }


    public void display(final CharSequence content, final int duration) {
        if (TextUtils.isEmpty(content)) {
            return;
        }
        tvToast.setText(content);
        //设置显示时间
        toast.setDuration(duration);
        toast.show();
    }

    public void display(int resId, int duration) {
        tvToast.setText(resId);
        toast.setDuration(duration);
        toast.show();
    }

    /**
     * 取消Toast
     */
    public void dismiss() {
        toast.cancel();
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