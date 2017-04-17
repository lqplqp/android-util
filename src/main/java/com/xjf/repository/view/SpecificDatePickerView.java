package com.xjf.repository.view;

import android.content.Context;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;

import com.xjf.repository.R;

import java.util.ArrayList;
import java.util.Calendar;

/**
 * -----------------------------------------------------------------
 * User:xijiufu
 * Email:xjfsml@163.com
 * Version:1.0
 * Time:2016/12/16--15:16
 * Function: 年月日 时间  选择器
 * ModifyHistory:
 * -----------------------------------------------------------------
 */
public class SpecificDatePickerView extends LinearLayout {

    private WheelView mTimePickerStartYear;

    private WheelView mTimePickerStartMonth;

    private WheelView mTimePickerStartDay;

    private String month;
    private String day;

    private String currentYear = getYear();
    private String currentMonth = getMonth();
    private String currentDay = getDay();

    /*  private String selectYear;
      private String selectMonth;
      private String selectDay;*/
    private Handler handler;


    public SpecificDatePickerView(Context context) {
        super(context);
        init(context);
    }

    public SpecificDatePickerView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    private void init(Context context) {
        handler = new Handler();
    }

    /**
     * 当View中所有的子控件均被映射成xml后触发
     */
    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        View view = LayoutInflater.from(getContext()).inflate(R.layout.layout_specific_date_picker, this);
        mTimePickerStartYear = (WheelView) view.findViewById(R.id.mTimePickerStartYear);
        mTimePickerStartMonth = (WheelView) view.findViewById(R.id.mTimePickerStartMonth);
        mTimePickerStartDay = (WheelView) view.findViewById(R.id.mTimePickerStartDay);

        initData();

        mTimePickerStartYear.setData(getYearsData());
        mTimePickerStartMonth.setData(getMonthsData(Integer.parseInt(month)));
        mTimePickerStartDay.setData(getDaysData(Integer.parseInt(day)));

        mTimePickerStartYear.setDefault(setYear(currentYear));
        mTimePickerStartMonth.setDefault(setMonth(currentMonth));
        mTimePickerStartDay.setDefault(Integer.parseInt(currentDay) - 1);

        mTimePickerStartYear.setOnSelectListener(new WheelView.OnSelectListener() {
            @Override
            public void endSelect(int id, String text) {
//                selectYear = text;
                currentYear = text.substring(0, text.length() - 1);
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        //获取月份
                        setDate(currentYear, month, day);

//                        setYear(currentYear);
                        mTimePickerStartMonth.refreshData(getMonthsData(Integer.parseInt(month)));
                        mTimePickerStartMonth.setDefault(0);

                        //获取1月份天数
                        calDays(currentYear, String.valueOf(1));

                        mTimePickerStartDay.refreshData(getDaysData(Integer.parseInt(day)));
                        mTimePickerStartDay.setDefault(0);

                        //获取天数
                        calDays(currentYear, month);

                    }
                });
            }

            @Override
            public void selecting(int id, String text) {

            }
        });

        mTimePickerStartMonth.setOnSelectListener(new WheelView.OnSelectListener() {
            @Override
            public void endSelect(int id, String text) {
//                selectMonth = text;
                text = text.substring(0, text.length() - 1);
                setMonth(text);
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        mTimePickerStartDay.refreshData(getDaysData(Integer.parseInt(day)));
                        mTimePickerStartDay.setDefault(0);
                        calDays(currentYear, month);
                    }
                });
            }

            @Override
            public void selecting(int id, String text) {

            }
        });


        mTimePickerStartDay.setOnSelectListener(new WheelView.OnSelectListener() {
            @Override
            public void endSelect(int id, String text) {
//                selectDay = text;
            }

            @Override
            public void selecting(int id, String text) {

            }
        });
    }


    /**
     * 获取选中的年份
     *
     * @return
     */
    public String getSelectYear() {
        if (mTimePickerStartYear == null) {
            return null;
        }
        return mTimePickerStartYear.getSelectedText();
    }

    /**
     * 设置起始默认年份
     *
     * @param index
     */
    public void setDefaultStartYear(int index) {
        if (mTimePickerStartYear == null) {
            return;
        }
        mTimePickerStartYear.setDefault(index - 1);
    }

    /**
     * 获取选中的月份
     *
     * @return
     */
    public String getSelectMonth() {
        if (mTimePickerStartMonth == null) {
            return null;
        }
        return mTimePickerStartMonth.getSelectedText();
    }

    /**
     * 设置起始默认月份
     *
     * @param index
     */
    public void setDefaultStartMonth(int index) {
        if (mTimePickerStartMonth == null) {
            return;
        }
        mTimePickerStartMonth.setDefault(index - 1);
    }

    /**
     * 获取选中的天
     *
     * @return
     */
    public String getSelectDay() {
        if (mTimePickerStartDay == null) {
            return null;
        }
        return mTimePickerStartDay.getSelectedText();
    }

    /**
     * 设置起始默认天数
     *
     * @param index
     */
    public void setDefaultStartDay(int index) {
        if (mTimePickerStartDay == null) {
            return;
        }
        mTimePickerStartDay.setDefault(index - 1);
    }


    private ArrayList<String> getYearsData() {
        ArrayList<String> list = new ArrayList<>();
        for (int i = Integer.parseInt(getYear()); i > 1950; i--) {
            list.add(i + "年");
//            list.add(i + "");
        }
        return list;
    }

    private ArrayList<String> getMonthsData(int months) {
        ArrayList<String> list = new ArrayList<>();
        for (int i = 1; i <= months; i++) {
            list.add(format(i) + "月");
//            list.add(i + "");
        }
        return list;
    }

    private ArrayList<String> getDaysData(int days) {
        ArrayList<String> list = new ArrayList<>();
        for (int i = 1; i <= days; i++) {
            list.add(format(i) + "日");
//            list.add(i + "");
        }
        return list;
    }

    private String format(int value) {
        if (value < 10) {
            return "0" + value;
        }
        return String.valueOf(value);
    }

    private String getYear() {
        Calendar c = Calendar.getInstance();
        return c.get(Calendar.YEAR) + "";
    }

    private String getMonth() {
        Calendar c = Calendar.getInstance();
        return format(c.get(Calendar.MONTH) + 1);
    }

    private String getDay() {
        Calendar c = Calendar.getInstance();
        return c.get(Calendar.DATE) + "";
    }

    private void initData() {
        setDate(getYear(), getMonth(), getDay());
//        this.currentDay = 1 + "";
//        this.currentMonth = 1 + "";
    }

    /**
     * 设置年月日
     *
     * @param year
     * @param month
     * @param day
     */
    public void setDate(String year, String month, String day) {
        /*selectYear = year + "年";
        selectMonth = month + "月";
        selectDay = day + "日";
        */
        /*selectYear = year;
        selectMonth = month;
        selectDay = day;
        */
        this.currentYear = year;
        this.currentMonth = month;
        this.currentDay = day;
        if (year.equals(getYear())) {
            this.month = getMonth();
        } else {
            this.month = 12 + "";
        }
        calDays(year, month);
    }

    /**
     * 设置年份
     *
     * @param year
     */
    public int setYear(String year) {
        int yearIndex = 0;
        if (!year.equals(getYear())) {
            this.month = 12 + "";
        } else {
            this.month = getMonth();
        }
        for (int i = Integer.parseInt(getYear()); i > 1950; i--) {
            if (i == Integer.parseInt(year)) {
                return yearIndex;
            }
            yearIndex++;
        }
        return yearIndex;
    }

    /**
     * 设置月份
     *
     * @param month
     * @param month
     * @return
     */
    public int setMonth(String month) {
        int monthIndex = 0;
        calDays(currentYear, month);
        for (int i = 1; i < Integer.parseInt(this.month); i++) {
            if (Integer.parseInt(month) == i) {
                return monthIndex;
            } else {
                monthIndex++;
            }
        }
        return monthIndex;
    }

    /**
     * 计算每月多少天
     *
     * @param month
     * @param year
     */
    public void calDays(String year, String month) {
        boolean leayYear = false;
        if (Integer.parseInt(year) % 4 == 0 && Integer.parseInt(year) % 100 != 0) {
            leayYear = true;
        } else {
            leayYear = false;
        }
        for (int i = 1; i <= 12; i++) {
            switch (Integer.parseInt(month)) {
                case 1:
                case 3:
                case 5:
                case 7:
                case 8:
                case 10:
                case 12:
                    this.day = 31 + "";
                    break;
                case 2:
                    if (leayYear) {
                        this.day = 29 + "";
                    } else {
                        this.day = 28 + "";
                    }
                    break;
                case 4:
                case 6:
                case 9:
                case 11:
                    this.day = 30 + "";
                    break;
            }
        }
        //主要用于当月只显示前面天数  不显示后面天数
        if (year.equals(getYear()) && month.equals(getMonth())) {
            this.day = getDay();
        }
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