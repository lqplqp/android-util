package com.xjf.repository.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;

import com.xjf.repository.R;

import java.util.ArrayList;

/**
 * ================================================================
 * User:xijiufu
 * Email:xjfsml@163.com
 * Version:1.0
 * Time:2016/11/7--22:36
 * Function:  小时  分钟 时间  选择器
 * ModifyHistory:
 * ================================================================
 */
public class TimePickerView extends LinearLayout {


    WheelView mTimePickerStartHour;
    WheelView mTimePickerStartMinutes;

    WheelView mTimePickerEndHour;
    WheelView mTimePickerEndMinutes;

    public TimePickerView(Context context) {
        super(context);
    }

    public TimePickerView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    /**
     * 当View中所有的子控件均被映射成xml后触发
     */
    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        View view = LayoutInflater.from(getContext()).inflate(R.layout.layout_time_picker, this);
        mTimePickerStartHour = (WheelView) view.findViewById(R.id.mTimePickerStartHour);
        mTimePickerStartMinutes = (WheelView) view.findViewById(R.id.mTimePickerStartMinutes);
        mTimePickerEndHour = (WheelView) view.findViewById(R.id.mTimePickerEndHour);
        mTimePickerEndMinutes = (WheelView) view.findViewById(R.id.mTimePickerEndMinutes);

        mTimePickerStartHour.setData(getHourData());
        mTimePickerStartHour.setOnSelectListener(new WheelView.OnSelectListener() {
            @Override
            public void endSelect(int id, String text) {

            }

            @Override
            public void selecting(int id, String text) {
                MToast.showToast("选中的：" + text);
            }
        });

        mTimePickerStartMinutes.setData(getMinutesData());

        mTimePickerEndHour.setData(getHourData());
        mTimePickerEndMinutes.setData(getMinutesData());

    }

    private ArrayList<String> getHourData() {
        ArrayList<String> list = new ArrayList<>();
        for (int i = 0; i < 24; i++) {
            list.add(filterNum(i));
        }
        return list;
    }

    private ArrayList<String> getMinutesData() {
        ArrayList<String> list = new ArrayList<>();
        for (int i = 0; i < 60; i++) {
            list.add(filterNum(i));
        }
        return list;
    }


    private String filterNum(int num) {
        if (num < 10) {
            return "0" + num;
        }
        return String.valueOf(num);
    }


    /***
     * 返回起始小时
     *
     * @return
     */
    public String getStartHour() {
        if (mTimePickerStartHour == null) {
            return null;
        }
        return mTimePickerStartHour.getSelectedText();
    }

    /**
     * 设置起始默认小时
     *
     * @param index
     */
    public void setDefaultStartHour(int index) {
        if (mTimePickerStartHour == null) {
            return;
        }
        mTimePickerStartHour.setDefault(index);
    }

    /**
     * 返回起始分钟
     *
     * @return
     */
    public String getStartMinutes() {
        if (mTimePickerStartMinutes == null) {
            return null;
        }
        return mTimePickerStartMinutes.getSelectedText();
    }

    /**
     * 设置起始默认分钟
     *
     * @param index
     */
    public void setDefaultStartMinutes(int index) {
        if (mTimePickerStartMinutes == null) {
            return;
        }
        mTimePickerStartMinutes.setDefault(index);
    }


    /***
     * 返回结束小时
     *
     * @return
     */
    public String getEndHour() {
        if (mTimePickerEndHour == null) {
            return null;
        }
        return mTimePickerEndHour.getSelectedText();
    }

    /**
     * 设置结束默认小时
     *
     * @param index
     */
    public void setDefaultEndHour(int index) {
        if (mTimePickerEndHour == null) {
            return;
        }
        mTimePickerEndHour.setDefault(index);
    }

    /**
     * 返回结束分钟
     *
     * @return
     */
    public String getEndMinutes() {
        if (mTimePickerEndMinutes == null) {
            return null;
        }
        return mTimePickerEndMinutes.getSelectedText();
    }

    /**
     * 设置结束默认分钟
     *
     * @param index
     */
    public void setDefaultEndMinutes(int index) {
        if (mTimePickerEndHour == null) {
            return;
        }
        mTimePickerEndHour.setDefault(index);
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