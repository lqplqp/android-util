package com.xjf.repository.utils;

import android.text.TextUtils;

import java.math.BigDecimal;

/**
 * -----------------------------------------------------------------
 * User:xijiufu
 * Email:xjfsml@163.com
 * Version:1.0
 * Time:2016/11/18--16:19
 * Function: 数字相关工具
 * ModifyHistory:
 * -----------------------------------------------------------------
 */
public class MathUtils {

    private MathUtils() {
    }

    /**
     * 保留2位小数
     *
     * @param value
     * @return
     */
    public static String twoDecimal(String value) {
        if (TextUtils.isEmpty(value)) {
            return "0.00";
        }
        //营业额
        BigDecimal decimal = new BigDecimal(value);
        return formatDecimal(decimal).toString();
    }
    
    /**
     * 保留2位小数
     *
     * @param value
     * @return
     */
    public static String twoDecimal(double value) {
        //营业额
        BigDecimal decimal = new BigDecimal(value);
        return formatDecimal(decimal).toString();
    }


    private static BigDecimal formatDecimal(BigDecimal decimal) {
        decimal.setScale(2, BigDecimal.ROUND_HALF_UP);//第一个变量是小数位数，第二个变量是取舍方法(四舍五入)
        return decimal;
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