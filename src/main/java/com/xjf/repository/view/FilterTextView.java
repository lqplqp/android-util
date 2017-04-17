package com.xjf.repository.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.TextView;

/**
 * -----------------------------------------------------------------
 * User:xijiufu
 * Email:xjfsml@163.com
 * Version:1.0
 * Time:2016/11/4--11:17
 * Function: 添加滤镜的TextView
 * ModifyHistory:
 * -----------------------------------------------------------------
 */
public class FilterTextView extends TextView {

    public FilterTextView(Context context) {
        super(context);
    }

    public FilterTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public FilterTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        // 获取TextView的Drawable对象，返回的数组长度应该是4，对应左上右下
        Drawable[] drawables = getCompoundDrawables();
        if (drawables != null) {
            Drawable drawable = drawables[0];
            if (drawable != null) {
                // 当左边Drawable的不为空时，测量要绘制文本的宽度
                float textWidth = getPaint().measureText(getText().toString());
                int drawablePadding = getCompoundDrawablePadding();
                int drawableWidth = drawable.getIntrinsicWidth();
                // 计算总宽度（文本宽度 + drawablePadding + drawableWidth）
                float bodyWidth = textWidth + drawablePadding + drawableWidth;
                // 移动画布开始绘制的X轴
                canvas.translate((getWidth() - bodyWidth) / 2, 0);
            } else if ((drawable = drawables[1]) != null) {
                // 否则如果上边的Drawable不为空时，获取文本的高度
                Rect rect = new Rect();
                getPaint().getTextBounds(getText().toString(), 0, getText().toString().length(), rect);
                float textHeight = rect.height();
                int drawablePadding = getCompoundDrawablePadding();
                int drawableHeight = drawable.getIntrinsicHeight();
                // 计算总高度（文本高度 + drawablePadding + drawableHeight）
                float bodyHeight = textHeight + drawablePadding + drawableHeight;
                // 移动画布开始绘制的Y轴
                canvas.translate(0, (((getHeight() - bodyHeight) / 2)));
            }
        }
        super.onDraw(canvas);
    }

    /* @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getActionMasked()) {
            case MotionEvent.ACTION_DOWN:
                //在按下事件中设置滤镜
                setFilter();
                break;
            case MotionEvent.ACTION_UP:
                //由于捕获了Touch事件，需要手动触发Click事件
                performClick();
            case MotionEvent.ACTION_CANCEL:
                //在CANCEL和UP事件中清除滤镜
                removeFilter();
                break;
            default:
                break;
        }
        return true;
    }*/

    /**
     * 移除滤镜
     */
    private void removeFilter() {
        Drawable[] drawables = getCompoundDrawables();
        Drawable drawableTop = drawables[1];//上部

        if (drawableTop == null) {
            return;
        }
        //清除滤镜
        drawableTop.clearColorFilter();
    }

    /**
     * 设置滤镜
     * <p>
     * 1.PorterDuff.Mode.CLEAR
     * 所绘制不会提交到画布上。
     * 2.PorterDuff.Mode.SRC
     * 显示上层绘制图片
     * 3.PorterDuff.Mode.DST
     * 显示下层绘制图片
     * 4.PorterDuff.Mode.SRC_OVER
     * 正常绘制显示，上下层绘制叠盖。
     * 5.PorterDuff.Mode.DST_OVER
     * 上下层都显示。下层居上显示。
     * 6.PorterDuff.Mode.SRC_IN
     * 取两层绘制交集。显示上层。
     * 7.PorterDuff.Mode.DST_IN
     * 取两层绘制交集。显示下层。
     * 8.PorterDuff.Mode.SRC_OUT
     * 取上层绘制非交集部分。
     * 9.PorterDuff.Mode.DST_OUT
     * 取下层绘制非交集部分。
     * 10.PorterDuff.Mode.SRC_ATOP
     * 取下层非交集部分与上层交集部分
     * 11.PorterDuff.Mode.DST_ATOP
     * 取上层非交集部分与下层交集部分
     * 12.PorterDuff.Mode.XOR
     * 异或：去除两图层交集部分
     * 13.PorterDuff.Mode.DARKEN
     * 取两图层全部区域，交集部分颜色加深
     * 14.PorterDuff.Mode.LIGHTEN
     * 取两图层全部，点亮交集部分颜色
     * 15.PorterDuff.Mode.MULTIPLY
     * 取两图层交集部分叠加后颜色
     * 16.PorterDuff.Mode.SCREEN
     * 取两图层全部区域，交集部分变为透明色
     */
    private void setFilter() {
        Drawable[] drawables = getCompoundDrawables();
        Drawable drawableTop = drawables[1];//上部
        if (drawableTop == null) {
            return;
        }
        //设置滤镜
        drawableTop.setColorFilter(Color.GRAY, PorterDuff.Mode.MULTIPLY);

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