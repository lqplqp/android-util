package com.xjf.repository.view;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;


import com.xjf.repository.R;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

/**
 * -----------------------------------------------------------------
 * User:xijiufu
 * Email:xjfsml@163.com
 * Version:1.0
 * Time:2016/7/18--16:36
 * Function:自定义等待框
 * ModifyHistory:
 * -----------------------------------------------------------------
 */
public class MProgressDialog extends Dialog {

    private Animation anim;
    private Activity context;

    //窗体中的根根布局
    private View rootView;
    //窗体的宽度与高度
    private int width = -1, heigth = -1;
    //是否需要显示等待你图片
    private boolean isCircle = true;
    //是否默认布局
    private boolean isDefault = true;

    //对话框是否超时的监听
    private ProgressDialogTimeOutListener listener;

    private ProgressDialogTimeOutListener timeOutListener;

    private String strMessage;
    private ScheduledExecutorService executors;
    //进度条对话框默认超时时间
    public int timeout = 30;


    private ScheduledFuture future;


    public MProgressDialog(Context context) {
        this(context, R.style.progressDialogCustom);
        this.context = (Activity) context;
    }

    public MProgressDialog(Context context, int themeResId) {
        super(context, themeResId);
        this.context = (Activity) context;
        init(context);
    }

    public void setTimeout(int timeout) {
        this.timeout = timeout;
    }

    //消息提示信息
    public void setMessage(CharSequence strMessage) {
        this.strMessage = strMessage.toString();
    }

    //消息提示信息
    public void setMessage(int strMessage) {
        this.strMessage = context.getString(strMessage) + "";
    }

    private void init(Context context) {
        //添加进度条对话框的的动画
        anim = AnimationUtils.loadAnimation(context, R.anim.public_rotate_anim);
    }

    /**
     * 设置对话框超时监听
     *
     * @param listener
     */
    public void setOnProgressDialogTimeOutListener(ProgressDialogTimeOutListener listener) {
        this.listener = listener;
    }

    public void setCancelable(boolean cancelable) {
        super.setCancelable(cancelable);
    }

    public void setCanceledOnTouchOutside(boolean cancelableOutside) {
        super.setCanceledOnTouchOutside(cancelableOutside);
    }

    private void showDialog() {
        if (context != null && !context.isFinishing()) {
            super.show();
        }
    }

    /**
     * 是否需要展示圆圈等待
     *
     * @param isCircle
     */
    public void setIsCircle(boolean isCircle) {
        this.isCircle = isCircle;
    }

    /**
     * 设置窗体的布局
     *
     * @param content
     */
    public void setView(View content) {
        this.rootView = content;
        isDefault = false;
    }

    /**
     * 尺寸
     *
     * @param width
     * @param height
     */
    public void setWindowSize(int width, int height) {
        this.width = width;
        this.heigth = height;
    }

    /**
     * 展示弹框
     */
    public void show() {
        if (context == null) {
            return;
        }
        show(context);
    }

    /**
     * 展示弹出框
     *
     * @param context
     */
    private void show(Context context) {
        Window window = this.getWindow();
        this.setCanceledOnTouchOutside(false);
        WindowManager.LayoutParams params = window.getAttributes();
        params.gravity = Gravity.CENTER;
        if (rootView == null) {
            rootView = LayoutInflater.from(context).inflate(R.layout.progress_dialog_view, null);
        }
        setContentView(rootView);
        //等待圆圈
        ImageView progressDialogImg = (ImageView) findViewById(R.id.progressDialogImg);
        //圆圈背景容器
        RelativeLayout mProgressDialogRL = (RelativeLayout) findViewById(R.id.mProgressDialogRL);

        //是否需要等待框
        if (!isCircle) {
            progressDialogImg.setVisibility(View.GONE);
        }
        if (isDefault) {
            //默认不设置宽高就全屏
            if (this.width == -1 || this.heigth == -1) {
                mProgressDialogRL.setBackgroundDrawable(context.getResources().getDrawable(R.color.translucent));
            } else {
                mProgressDialogRL.setBackgroundDrawable(context.getResources().getDrawable(R.drawable.item_delete_dialog_shape));
                window.setDimAmount(0);
            }
            TextView tvMsg = (TextView) findViewById(R.id.msgTxv);
            if (!TextUtils.isEmpty(strMessage)) {
                tvMsg.setVisibility(View.VISIBLE);
                tvMsg.setText(strMessage.toString());
            } else {
                tvMsg.setVisibility(View.GONE);
            }
        } else {
            //不是默认布局就不显示全屏
            window.setDimAmount(0);
        }
        rootView.getLayoutParams().width = this.width;
        rootView.getLayoutParams().height = this.heigth;
        if (!(context instanceof Activity) || !((Activity) context).isFinishing()) {
            try {
                this.showDialog();
                //开始计算超时时间
                startTimer(this);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 开始计算当前对话框的超时时间
     *
     * @param dialog
     */
    private void startTimer(final MProgressDialog dialog) {
        if (executors == null) {
            executors = Executors.newSingleThreadScheduledExecutor();
        }
        future = executors.schedule(new Runnable() {
            @Override
            public void run() {
                //关闭对话框
                dialog.cancel();
                if (listener != null) {
                    listener.onTimeOut(true);
                }
            }
        }, timeout, TimeUnit.SECONDS);
    }

    /**
     * 关闭对话框超时计时
     *
     * @param dialog
     */
    private void stopTimer(final MProgressDialog dialog) {
        if (executors == null || executors.isShutdown() || executors.isTerminated()) {
            return;
        }
        if (executors != null && !executors.isShutdown()) {
            //取消这次任务，否则即使关闭了定时器这次任务也会执行完
            if (future != null) {
                future.cancel(true);
            }

            if (listener != null) {
                listener.onTimeOut(false);
            }
            executors.shutdown();
            executors = null;
        }
    }

    /**
     * 依附到窗体
     */
    @Override
    public void onAttachedToWindow() {
        ImageView img = (ImageView) findViewById(R.id.progressDialogImg);
        if (null != img) {
            img.clearAnimation();
            img.startAnimation(anim);
        }
        super.onAttachedToWindow();
    }


    /**
     * 与窗体脱离关系
     */
    @Override
    public void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        //关闭动画
        ImageView img = (ImageView) findViewById(R.id.progressDialogImg);
        anim.reset();
        anim.cancel();
        if (null != img) {
            img.clearAnimation();
        }
        this.dismiss();
    }

    @Override
    public void dismiss() {
        stopTimer(this);
        if (context != null && !context.isFinishing()) {
            super.dismiss();
        }
    }

    @Override
    public void cancel() {
        stopTimer(this);
        if (context != null && !context.isFinishing()) {
            super.cancel();
        }
    }


}
