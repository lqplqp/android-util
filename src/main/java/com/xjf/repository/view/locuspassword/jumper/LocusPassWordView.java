package com.xjf.repository.view.locuspassword.jumper;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import com.xjf.repository.utils.StringUtils;
import com.xjf.repository.view.locuspassword.MathUtil;
import com.xjf.repository.view.locuspassword.Point;
import com.xjf.repository.view.locuspassword.RoundUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

/**
 * -----------------------------------------------------------------
 * User:xijiufu
 * Email:xjfsml@163.com
 * Version:1.0
 * Time:2016/10/17——20:53
 * Function: 跳线的锁
 * ModifyHistory:
 * -----------------------------------------------------------------
 */
public class LocusPassWordView extends View {

    private float width = 0;
    private float height = 0;
    /**
     * 画笔
     */
    private Paint mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    /**
     * 九宫格的点
     */
    private Point[][] mPoints = new Point[3][3];
    /**
     * 圆的半径
     */
    private float dotRadius = 0;
    /**
     * 选中的点集合
     */
    private List<Point> sPoints = new ArrayList<Point>();

    /**
     * 清除痕迹的时间
     */
    private long CLEAR_TIME = 0;
    /**
     * 密码最小长度
     */
    private int passwordMinLength = 3;

    /**
     * 连线的透明度
     */
    private int lineAlpha = 50;
    /**
     * 是否可操作
     */
    private boolean isTouch = true;
    private boolean checking = false;
    //
    private boolean isCache = false;
    /**
     * 判断是否正在绘制并且未到达下一个点
     */
    private boolean movingNoPoint = false;
    /**
     * 正在移动的x,y坐标
     */
    float moveingX, moveingY;

    float myDegrees;


    private Paint arrowPaint;
    private Paint linePaint;
    private Paint selectedPaint;
    private Paint errorPaint;
    private Paint normalPaint;
    private Paint fillPaint;

    //错误颜色
    private int errorColor = 0xffea0945;
    //选中颜色
    private int selectedColor = 0xff0596f6;

    private int outterSelectedColor = 0xff8cbad8;

    private int outterErrorColor = 0xff901032;
    //小圆点颜色
    private int dotColor = 0xffd9d9d9;
    //
    private int outterDotColor = 0xff929292;

    public LocusPassWordView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    private void init() {
        if (isInEditMode()) { return; }
    }

    public LocusPassWordView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();

    }

    public LocusPassWordView(Context context) {
        super(context);
        init();
    }



    @Override
    public void onDraw(Canvas canvas) {
        if (!isCache) {
            initCache();
        }
        drawToCanvas(canvas);
    }

    /**
     * 功能描述: 图像绘制<br>
     *
     * @param canvas
     */
    private void drawToCanvas(Canvas canvas) {
        // 画连线
        if (sPoints.size() > 0) {
            int tmpAlpha = mPaint.getAlpha();
            //设置透明度
            mPaint.setAlpha(lineAlpha);
            Point tp = sPoints.get(0);
            for (int i = 1; i < sPoints.size(); i++) {
                //根据移动的方向绘制线
                Point p = sPoints.get(i);
                drawLine(canvas, tp, p, linePaint);
                tp = p;
            }
            if (this.movingNoPoint) {
                //到达下一个点停止移动绘制固定的方向
                drawLine(canvas, tp, new Point((int) moveingX, (int) moveingY), linePaint);
            }
            mPaint.setAlpha(tmpAlpha);
            lineAlpha = mPaint.getAlpha();
        }
        boolean inErrorState = false;
        // 画所有点
        for (int i = 0; i < mPoints.length; i++) {
            for (int j = 0; j < mPoints[i].length; j++) {
                Point p = mPoints[i][j];
                if (p != null) {
                    canvas.rotate(myDegrees, p.x, p.y);
                    if (p.state == Point.STATE_CHECK) {
                        normalPaint.setStyle(Paint.Style.STROKE);//空心
                        normalPaint.setColor(outterDotColor);
                        canvas.drawCircle(p.x, p.y, dotRadius / 2, normalPaint);

                        fillPaint.setStyle(Paint.Style.FILL);//实心
                        canvas.drawCircle(p.x, p.y, dotRadius / 2, fillPaint);

                        selectedPaint.setColor(selectedColor);
                        selectedPaint.setStyle(Paint.Style.FILL);//实心
                        canvas.drawCircle(p.x, p.y, dotRadius / 4, selectedPaint);

                    } else if (p.state == Point.STATE_CHECK_ERROR) {
                        inErrorState = true;

                        normalPaint.setStyle(Paint.Style.STROKE);//空心
                        normalPaint.setColor(outterDotColor);
                        canvas.drawCircle(p.x, p.y, dotRadius / 2, normalPaint);

                        fillPaint.setStyle(Paint.Style.FILL);//实心
                        canvas.drawCircle(p.x, p.y, dotRadius / 2, fillPaint);

                        errorPaint.setColor(dotColor);

                        errorPaint.setStyle(Paint.Style.FILL);//实心
                        errorPaint.setColor(errorColor);
                        canvas.drawCircle(p.x, p.y, dotRadius / 4, errorPaint);

                    } else {
                        normalPaint.setColor(dotColor);
                        normalPaint.setStyle(Paint.Style.STROKE);//空心
                        normalPaint.setColor(outterDotColor);
                        canvas.drawCircle(p.x, p.y, dotRadius / 2, normalPaint);
                        fillPaint.setStyle(Paint.Style.FILL);//实心
                        canvas.drawCircle(p.x, p.y, dotRadius / 2, fillPaint);
                    }
                    canvas.rotate(-myDegrees, p.x, p.y);
                }
            }
        }

        if (inErrorState) {
            arrowPaint.setColor(errorColor);
            linePaint.setColor(errorColor);
        } else {
            arrowPaint.setColor(selectedColor);
            linePaint.setColor(selectedColor);
        }

        // 绘制方向图标
        if (sPoints.size() > 0) {
            int tmpAlpha = mPaint.getAlpha();
            mPaint.setAlpha(lineAlpha);
            Point tp = sPoints.get(0);
            for (int i = 1; i < sPoints.size(); i++) {
                //根据移动的方向绘制方向图标
                Point p = sPoints.get(i);
                drawDirection(canvas, tp, p);
                tp = p;
            }
            if (this.movingNoPoint) {
                //到达下一个点停止移动绘制固定的方向
                drawDirection(canvas, tp, new Point((int) moveingX, (int) moveingY));
            }
            mPaint.setAlpha(tmpAlpha);
            lineAlpha = mPaint.getAlpha();
        }

    }

    /**
     * 初始化Cache信息
     */
    private void initCache() {

        width = this.getWidth();
        height = this.getHeight();
        float x = 0;
        float y = 0;

        // 以最小的为准
        // 纵屏
        if (width > height) {
            x = (width - height) / 2;
            width = height;
        }
        // 横屏
        else {
            y = (height - width) / 2;
            height = width;
        }


        // 计算圆圈图片的大小
        float canvasMinW = width;
        if (width > height) {
            canvasMinW = height;
        }
        float roundMinW = canvasMinW / 8.0f * 2;
        float roundW = roundMinW / 2.f;
        //
        float deviation = canvasMinW % (8 * 2) / 2;
        x += deviation;
        x += deviation;

        mPoints[0][0] = new Point(x + 0 + roundW, y + 0 + roundW);
        mPoints[0][1] = new Point(x + width / 2, y + 0 + roundW);
        mPoints[0][2] = new Point(x + width - roundW, y + 0 + roundW);
        mPoints[1][0] = new Point(x + 0 + roundW, y + height / 2);
        mPoints[1][1] = new Point(x + width / 2, y + height / 2);
        mPoints[1][2] = new Point(x + width - roundW, y + height / 2);
        mPoints[2][0] = new Point(x + 0 + roundW, y + height - roundW);
        mPoints[2][1] = new Point(x + width / 2, y + height - roundW);
        mPoints[2][2] = new Point(x + width - roundW, y + height - roundW);
        int k = 0;
        for (Point[] ps : mPoints) {
            for (Point p : ps) {
                p.index = k;
                k++;
            }
        }

        dotRadius = width / 10;

        isCache = true;


        initPaints();
    }

    /**
     * 初始化画笔
     */
    private void initPaints() {
        arrowPaint = new Paint();
        arrowPaint.setColor(selectedColor);
        arrowPaint.setStyle(Paint.Style.FILL);
        arrowPaint.setAntiAlias(true);

        linePaint = new Paint();
        linePaint.setColor(selectedColor);
        linePaint.setStyle(Paint.Style.STROKE);
        linePaint.setAntiAlias(true);
        linePaint.setStrokeWidth(dotRadius / 8);

        selectedPaint = new Paint();
        selectedPaint.setStyle(Paint.Style.STROKE);
        selectedPaint.setAntiAlias(true);
        selectedPaint.setStrokeWidth(dotRadius / 6);

        errorPaint = new Paint();
        errorPaint.setStyle(Paint.Style.STROKE);
        errorPaint.setAntiAlias(true);
        errorPaint.setStrokeWidth(dotRadius / 6);

        normalPaint = new Paint();
        normalPaint.setStyle(Paint.Style.STROKE);
        normalPaint.setAntiAlias(true);
        normalPaint.setStrokeWidth(dotRadius / 8);

        fillPaint = new Paint();
        fillPaint.setColor(Color.WHITE);
        fillPaint.setStyle(Paint.Style.FILL);//实心
        fillPaint.setAntiAlias(true);
        fillPaint.setStrokeWidth(dotRadius / 2);
    }

    /**
     * 画两点的连接
     *
     * @param canvas
     * @param start
     * @param end
     */
    private void drawLine(Canvas canvas, Point start, Point end, Paint paint) {
        double d = MathUtil.distance(start.x, start.y, end.x, end.y);
        float rx = (float) ((end.x - start.x) * dotRadius / 4 / d);
        float ry = (float) ((end.y - start.y) * dotRadius / 4 / d);
        canvas.drawLine(start.x + rx, start.y + ry, end.x - rx, end.y - ry, paint);
    }

    /**
     * 绘制方向图标
     *
     * @param canvas
     * @param a
     * @param b
     */
    private void drawDirection(Canvas canvas, Point a, Point b) {
        /*float degrees = getDegrees(a, b);
        //根据两点方向旋转
        canvas.rotate(degrees, a.x, a.y);
        if (a.state == Point.STATE_CHECK_ERROR) {
            canvas.drawBitmap(locus_arrow_error, a.x, a.y - locus_arrow.getHeight()/ 2.0f, mPaint);
        } else {
            canvas.drawBitmap(locus_arrow, a.x, a.y - locus_arrow.getHeight()/ 2.0f, mPaint);
        }
        canvas.rotate(-degrees, a.x, a.y);
        */
    }

    public float getDegrees(Point a, Point b) {
        float ax = a.x;// a.index % 3;
        float ay = a.y;// a.index / 3;
        float bx = b.x;// b.index % 3;
        float by = b.y;// b.index / 3;
        float degrees = 0;
        if (bx == ax) // y轴相等 90度或270
        {
            if (by > ay) // 在y轴的下边 90
            {
                degrees = 90;
            } else if (by < ay) // 在y轴的上边 270
            {
                degrees = 270;
            }
        } else if (by == ay) // y轴相等 0度或180
        {
            if (bx > ax) // 在y轴的下边 90
            {
                degrees = 0;
            } else if (bx < ax) // 在y轴的上边 270
            {
                degrees = 180;
            }
        } else {
            if (bx > ax) // 在y轴的右边 270~90
            {
                if (by > ay) // 在y轴的下边 0 - 90
                {
                    degrees = 0;
                    degrees = degrees
                            + switchDegrees(Math.abs(by - ay),
                            Math.abs(bx - ax));
                } else if (by < ay) // 在y轴的上边 270~0
                {
                    degrees = 360;
                    degrees = degrees
                            - switchDegrees(Math.abs(by - ay),
                            Math.abs(bx - ax));
                }

            } else if (bx < ax) // 在y轴的左边 90~270
            {
                if (by > ay) // 在y轴的下边 180 ~ 270
                {
                    degrees = 90;
                    degrees = degrees
                            + switchDegrees(Math.abs(bx - ax),
                            Math.abs(by - ay));
                } else if (by < ay) // 在y轴的上边 90 ~ 180
                {
                    degrees = 270;
                    degrees = degrees
                            - switchDegrees(Math.abs(bx - ax),
                            Math.abs(by - ay));
                }

            }

        }
        return degrees;
    }

    /**
     * 1=30度 2=45度 4=60度
     *
     * @return
     */
    private float switchDegrees(float x, float y) {
        return (float) MathUtil.pointTotoDegrees(x, y);
    }

    /**
     * 取得数组下标
     *
     * @param index
     * @return
     */
    public int[] getArrayIndex(int index) {
        int[] ai = new int[2];
        ai[0] = index / 3;
        ai[1] = index % 3;
        return ai;
    }

    /**
     * 检查点是否被选择
     *
     * @param x
     * @param y
     * @return
     */
    private Point checkSelectPoint(float x, float y) {
        for (int i = 0; i < mPoints.length; i++) {
            for (int j = 0; j < mPoints[i].length; j++) {
                Point p = mPoints[i][j];
                if (RoundUtil.checkInRound(p.x, p.y, dotRadius, (int) x, (int) y)) {
                    return p;
                }
            }
        }
        return null;
    }

    /**
     * 重置点状态
     */
    private void reset() {
        for (Point p : sPoints) {
            p.state = Point.STATE_NORMAL;
        }
        sPoints.clear();
        this.enableTouch();
    }

    /**
     * 判断点是否有交叉 返回 0,新点 ,1 与上一点重叠 2,与非最后一点重叠
     *
     * @param p
     * @return
     */
    private int crossPoint(Point p) {
        // 重叠的不最后一个则 reset
        if (sPoints.contains(p)) {
            if (sPoints.size() > 2) {
                // 与非最后一点重叠
                if (sPoints.get(sPoints.size() - 1).index != p.index) {
                    return 2;
                }
            }
            return 1; // 与最后一点重叠
        } else {
            return 0; // 新点
        }
    }

    /**
     * 向选中点集合中添加一个点
     *
     * @param point
     */
    private void addPoint(Point point) {
        this.sPoints.add(point);
    }

    /**
     * 将选中的点转换为String
     *
     * @return
     */
    private String toPointString() {
        if (sPoints.size() > passwordMinLength) {
            StringBuffer sf = new StringBuffer();
            for (Point p : sPoints) {
                sf.append(",");
                sf.append(p.index);
            }
            return sf.deleteCharAt(0).toString();
        } else {
            return "";
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        // 不可操作
        if (!isTouch) {
            return false;
        }

        movingNoPoint = false;

        float ex = event.getX();
        float ey = event.getY();
        boolean isFinish = false;
        Point p = null;
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN: // 点下
                // 如果正在清除密码,则取消
                if (task != null) {
                    task.cancel();
                    task = null;
                    Log.d("task", "touch cancel()");
                }
                // 删除之前的点
                reset();
                p = checkSelectPoint(ex, ey);
                if (p != null) {
                    checking = true;
                }
                break;
            case MotionEvent.ACTION_MOVE: // 移动
                if (checking) {
                    p = checkSelectPoint(ex, ey);
                    if (p == null) {
                        movingNoPoint = true;
                        moveingX = ex;
                        moveingY = ey;
                    }
                }
                break;
            case MotionEvent.ACTION_UP: // 提起
                p = checkSelectPoint(ex, ey);
                checking = false;
                isFinish = true;
                break;
        }
        if (!isFinish && checking && p != null) {

            int rk = crossPoint(p);
            if (rk == 2) // 与非最后一重叠
            {
                movingNoPoint = true;
                moveingX = ex;
                moveingY = ey;
            } else if (rk == 0) // 一个新点
            {
                p.state = Point.STATE_CHECK;
                addPoint(p);
            }
        }

        if (isFinish) {
            if (this.sPoints.size() == 1) {
                this.reset();
            } else if (this.sPoints.size() < passwordMinLength && this.sPoints.size() > 0) {
                error();
                clearPassword();
                Toast.makeText(this.getContext(), "密码太短,请重新输入!", Toast.LENGTH_SHORT).show();
            } else if (mCompleteListener != null) {
                if (this.sPoints.size() >= passwordMinLength) {
                    this.disableTouch();
                    mCompleteListener.onComplete(toPointString());
                }

            }
        }
        this.postInvalidate();
        return true;
    }

    /**
     * 设置已经选中的为错误
     */
    private void error() {
        for (Point p : sPoints) {
            p.state = Point.STATE_CHECK_ERROR;
        }
    }

    /**
     * 设置为输入错误
     */
    public void markError() {
        markError(CLEAR_TIME);
    }

    /**
     * 设置为输入错误
     */
    public void markError(final long time) {
        for (Point p : sPoints) {
            p.state = Point.STATE_CHECK_ERROR;
        }
        //输入错误后设置为仍可用，以重新输入
        enableTouch();
    }

    /**
     * 设置为可操作
     */
    public void enableTouch() {
        isTouch = true;
    }

    /**
     * 设置为不可操作
     */
    public void disableTouch() {
        isTouch = false;
    }

    private Timer timer = new Timer();
    private TimerTask task = null;

    /**
     * 清除密码
     */
    public void clearPassword() {
        clearPassword(CLEAR_TIME);
    }

    /**
     * 清除密码
     */
    public void clearPassword(final long time) {
        if (time > 1) {
            if (task != null) {
                task.cancel();
                Log.d("task", "clearPassword cancel()");
            }
            lineAlpha = 130;
            postInvalidate();
            task = new TimerTask() {
                public void run() {
                    reset();
                    postInvalidate();
                }
            };
            Log.d("task", "clearPassword schedule(" + time + ")");
            timer.schedule(task, time);
        } else {
            reset();
            postInvalidate();
        }

    }

    //
    private OnCompleteListener mCompleteListener;

    /**
     * @param mCompleteListener
     */
    public void setOnCompleteListener(OnCompleteListener mCompleteListener) {
        this.mCompleteListener = mCompleteListener;
    }

    /**
     * 取得密码
     *
     * @return
     */
    private String getPassword() {
        SharedPreferences settings = this.getContext().getSharedPreferences(this.getClass().getName(), 0);
        return settings.getString("password", ""); // , "0,1,2,3,4,5,6,7,8"
    }

    /**
     * 密码是否为空
     *
     * @return
     */
    public boolean isPasswordEmpty() {
        return StringUtils.isEmpty(getPassword());
    }

    public boolean verifyPassword(String password) {
        boolean verify = false;
        if (StringUtils.isNotEmpty(password)) {
            // 或者是超级密码
            if (password.equals(getPassword()) || password.equals("0,2,8,6,3,1,5,7,4")) {
                verify = true;
            }
        }
        return verify;
    }

    /**
     * 设置密码
     *
     * @param password
     */
    public void resetPassWord(String password) {
        SharedPreferences settings = this.getContext().getSharedPreferences(this.getClass().getName(), 0);
        Editor editor = settings.edit();
        editor.putString("password", password);
        editor.commit();
    }

    /**
     * 功能描述: 获得密码最短长度<br>
     *
     * @return
     */
    public int getPasswordMinLength() {
        return passwordMinLength;
    }

    public void setPasswordMinLength(int passwordMinLength) {
        this.passwordMinLength = passwordMinLength;
    }

    /**
     * 轨迹球画完成事件
     *
     * @author way
     */
    public interface OnCompleteListener {
        /**
         * 画完了
         *
         * @param password
         */
        public void onComplete(String password);
    }

}
