package com.xjf.repository.view;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.xjf.repository.R;


/**
 * -----------------------------------------------------------------
 * User:xijiufu
 * Email:xjfsml@163.com
 * Version:1.0
 * Time:2016/7/18--17:02
 * Function: 自定义输入框
 * ModifyHistory:
 * -----------------------------------------------------------------
 */
public class MAlertDialog extends Dialog implements DialogInterface {

    private Context context;


    //是否可以点击返回按钮
    private boolean bCanBack = true;

    public MAlertDialog(Context context) {
        super(context);
        this.context = context;
    }

    public MAlertDialog(Context context, int themeResId) {
        super(context, themeResId);
        this.context = context;
    }

    @Override
    public void show() {
        if (!(context instanceof Activity)) {
            return;
        }

        Activity activity = (Activity) context;
        if (!activity.isFinishing()) {
            super.show();
        }
    }

    /**
     * 获取指定按钮
     *
     * @param whichButton
     * @return
     */
    public Button getButton(int whichButton) {
        Button resultButton = null;
        switch (whichButton) {
            case DialogInterface.BUTTON_POSITIVE: {
                resultButton = (Button) this.findViewById(R.id.positiveButton);
            }
            break;
            case DialogInterface.BUTTON_NEGATIVE: {
                resultButton = (Button) this.findViewById(R.id.negativeButton);
            }
            break;
            case DialogInterface.BUTTON_NEUTRAL: {
            }
            break;
            default:
                break;
        }
        return resultButton;
    }

    /**
     * 设置需要显示的消息
     *
     * @param resource
     */
    public void setMessage(int resource) {
        setMessage(getContext().getString(resource));
    }

    /**
     * 设置需要显示的消息
     *
     * @param message
     */
    public void setMessage(CharSequence message) {
        ((TextView) this.findViewById(R.id.message)).setText(message);
    }


    /**
     * 设置返回键状态
     *
     * @param mBCanBack
     */
    public void setCanBack(boolean mBCanBack) {
        this.bCanBack = mBCanBack;
    }


    @Override
    public void onBackPressed() {
        if (bCanBack) {
            super.onBackPressed();
            return;
        }
    }

    /**
     * 建造者
     */
    public static class Builder {
        private Context context;

        private String title;

        private String message;

        private String positiveButtonText;

        private String negativeButtonText;

        private Drawable drawableLeft;

        private View contentView;


        private CharSequence[] items;

        private OnClickListener onItemClickListener;

        private OnClickListener positiveButtonClickListener, negativeButtonClickListener;

        private boolean canBack = true;

        public void setCanBack(boolean canBack) {
            this.canBack = canBack;
        }

        //点击任何按钮，是否自动关闭，默认True
        private boolean isAutoDismiss = true;

        private boolean editMode = false;
        //点击其他区域是否关闭弹窗
        private boolean cancel = false;
        private String hintText;


        /**
         * 设置点击窗口其他区域是否关闭窗口
         *
         * @param cancel
         */
        public void setCanceledOnTouchOutside(boolean cancel) {
            this.cancel = cancel;
        }

        public Builder setEditMode() {
            this.editMode = true;
            return this;
        }

        public Builder(Context context) {
            this.context = context;
        }

        public Builder setMessage(String message) {
            this.message = message;
            return this;
        }

        public Builder setMessage(int message) {
            this.message = (String) context.getText(message);
            return this;
        }

        public Builder setTitle(int title) {
            this.title = (String) context.getText(title);
            return this;
        }

        public Builder setTitle(String title) {
            this.title = title;
            return this;
        }

        public Builder setContentView(View v) {
            this.contentView = v;
            return this;
        }

        public Builder setEditMode(String hintText) {
            this.editMode = true;
            this.hintText = hintText;
            return this;
        }

        public boolean isAutoDismiss() {
            return isAutoDismiss;
        }

        public Builder setAutoDismiss(boolean isAutoDismiss) {
            this.isAutoDismiss = isAutoDismiss;
            return this;
        }

        public Builder setPositiveButton(int positiveButtonText, OnClickListener listener) {
            this.positiveButtonText = (String) context.getText(positiveButtonText);
            this.positiveButtonClickListener = listener;
            return this;
        }

        public Builder setPositiveButton(String positiveButtonText, OnClickListener listener) {
            this.positiveButtonText = positiveButtonText;
            this.positiveButtonClickListener = listener;
            return this;
        }

        public Builder setNegativeButton(int negativeButtonText, OnClickListener listener) {
            this.negativeButtonText = (String) context.getText(negativeButtonText);
            this.negativeButtonClickListener = listener;
            return this;
        }

        private boolean isShowCloseBtn = false;

        public Builder showCloseBtn() {
            isShowCloseBtn = true;
            return this;
        }

        public Builder setLeftDrawable(int id) {
            drawableLeft = context.getResources().getDrawable(id);
            return this;
        }

        public Builder setNegativeButton(String negativeButtonText, OnClickListener listener) {
            this.negativeButtonText = negativeButtonText;
            this.negativeButtonClickListener = listener;
            return this;
        }

        public Builder setItems(CharSequence[] items, OnClickListener onClickListener) {
            this.items = items;
            this.onItemClickListener = onClickListener;
            return this;
        }


        public MAlertDialog create() {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            final MAlertDialog dialog = new MAlertDialog(context, R.style.transprentDialogStyle);
            View layout = inflater.inflate(R.layout.dialog_common_layout, null);
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.addContentView(layout, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.FILL_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));

            try {
                DisplayMetrics d = context.getResources().getDisplayMetrics();
                WindowManager.LayoutParams params = dialog.getWindow().getAttributes();
                // 获取对话框当前的参数值
                params.x = 0; // 设置位置 默认为居中
                params.y = 0; // 设置位置 默认为居中

                // 设置dialog的宽度
                params.width = (int) (d.widthPixels * 0.85);
                params.gravity = Gravity.CENTER;
                dialog.getWindow().setAttributes(params);
            } catch (Exception e) {
                e.printStackTrace();
            }

            if (null == title) {
                layout.findViewById(R.id.titleLayout).setVisibility(View.GONE);
                layout.findViewById(R.id.titleDriver).setVisibility(View.GONE);
            } else {
                ((TextView) layout.findViewById(R.id.title)).setText(title);

                layout.findViewById(R.id.titleLayout).setVisibility(View.VISIBLE);
                layout.findViewById(R.id.titleDriver).setVisibility(View.GONE);
            }

            if (isShowCloseBtn) {
                layout.findViewById(R.id.dialogCloseBtn).setVisibility(View.VISIBLE);
                layout.findViewById(R.id.dialogCloseBtn).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (dialog != null) {
                            dialog.dismiss();
                        }
                    }
                });
            } else {
                layout.findViewById(R.id.dialogCloseBtn).setVisibility(View.GONE);
            }

            //无标题，有按钮
            if (title == null && (positiveButtonText != null && negativeButtonText != null)) {
                layout.findViewById(R.id.content).setBackgroundResource(R.drawable.dialog_content_notitle_bg);

            }

            //无标题，无按钮
            else if (title == null && (positiveButtonText == null && negativeButtonText == null)) {
                layout.findViewById(R.id.content).setBackgroundResource(R.drawable.dialog_content_notitle_nobutton_bg);
            }
            //有标题，无按钮
            else if (title != null && positiveButtonText == null && negativeButtonText == null) {
                layout.findViewById(R.id.content).setBackgroundResource(R.drawable.dialog_content_bg);
            }
            //不存在按钮时，也有圆角
            if (positiveButtonText == null && negativeButtonText == null) {
                Button negative = (Button) layout.findViewById(R.id.negativeButton);
                layout.findViewById(R.id.driver).setVisibility(View.GONE);
                negative.setVisibility(View.GONE);

                Button positive = (Button) layout.findViewById(R.id.positiveButton);
                positive.setVisibility(View.GONE);
                layout.findViewById(R.id.sepLine).setVisibility(View.GONE);
                layout.findViewById(R.id.content).setPadding(0, 5, 0, 5);
            }
            //只存在一个取消按钮的情况下
            else if (TextUtils.isEmpty(positiveButtonText) && !TextUtils.isEmpty(negativeButtonText)) {
                Button negative = (Button) layout.findViewById(R.id.negativeButton);
                layout.findViewById(R.id.driver).setVisibility(View.GONE);
                negative.setVisibility(View.VISIBLE);
                negative.setText(negativeButtonText);
                negative.setBackgroundResource(R.drawable.dialog_one_btn_bg);
                if (negativeButtonClickListener != null) {
                    negative.setOnClickListener(new View.OnClickListener() {
                        public void onClick(View v) {

                            if (isAutoDismiss && null != dialog && dialog.isShowing()) {
                                dialog.dismiss();
                            }
                            negativeButtonClickListener.onClick(dialog, DialogInterface.BUTTON_NEGATIVE);
                        }
                    });
                }
                Button positive = (Button) layout.findViewById(R.id.positiveButton);
                positive.setVisibility(View.GONE);
            }
            //只存在一个确定按钮的情况下
            else if (!TextUtils.isEmpty(positiveButtonText) && TextUtils.isEmpty(negativeButtonText)) {
//                layout.findViewById(R.id.mLayoutGroup).setBackgroundResource(R.drawable.dialog_content_notitle_bg);
                Button positive = (Button) layout.findViewById(R.id.positiveButton);
                positive.setVisibility(View.VISIBLE);
                layout.findViewById(R.id.driver).setVisibility(View.GONE);
                positive.setText(positiveButtonText);
                if (positiveButtonClickListener != null) {
                    positive.setOnClickListener(new View.OnClickListener() {
                        public void onClick(View v) {
                            positiveButtonClickListener.onClick(dialog, DialogInterface.BUTTON_POSITIVE);
                        }
                    });
                }
                Button negative = (Button) layout.findViewById(R.id.negativeButton);
                negative.setVisibility(View.GONE);
            }
            //两个按钮都存在时
            else {
                Button positive = (Button) layout.findViewById(R.id.positiveButton);
                positive.setVisibility(View.VISIBLE);
                layout.findViewById(R.id.driver).setVisibility(View.VISIBLE);
                positive.setText(positiveButtonText);
                if (positiveButtonClickListener != null) {
                    positive.setOnClickListener(new View.OnClickListener() {
                        public void onClick(View v) {
                            positiveButtonClickListener.onClick(dialog, DialogInterface.BUTTON_POSITIVE);
                        }
                    });
                }

                Button negative = (Button) layout.findViewById(R.id.negativeButton);
                negative.setVisibility(View.VISIBLE);
                negative.setText(negativeButtonText);
                if (negativeButtonClickListener != null) {
                    negative.setOnClickListener(new View.OnClickListener() {
                        public void onClick(View v) {

                            if (isAutoDismiss && null != dialog && dialog.isShowing()) {
                                dialog.dismiss();
                            }
                            negativeButtonClickListener.onClick(dialog, DialogInterface.BUTTON_NEGATIVE);
                        }
                    });
                }
            }

            //编辑模式则展示编辑框
            if (editMode) {
                ((TextView) layout.findViewById(R.id.message)).setVisibility(View.GONE);


                layout.findViewById(R.id.content).setPadding(0, 30, 0, 30);
            } else {
                if (message != null) {
                    TextView message_text = ((TextView) layout.findViewById(R.id.message));
                    message_text.setText(message);
                    if (drawableLeft != null) {   /// 这一步必须要做,否则不会显示.
                        drawableLeft.setBounds(0, 0, drawableLeft.getMinimumWidth(), drawableLeft.getMinimumHeight());
                        message_text.setCompoundDrawables(drawableLeft, null, null, null);
                    }
                    ((ScrollView) layout.findViewById(R.id.scrollView)).setVisibility(View.VISIBLE);
                } else if (contentView != null) {
                    ((RelativeLayout) layout.findViewById(R.id.content)).removeAllViews();
                    ((RelativeLayout) layout.findViewById(R.id.content)).addView(contentView, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.FILL_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                }
            }
            dialog.setContentView(layout);
            dialog.setCanBack(canBack);
            // 默认不允许点击其他地方取消dialog
            dialog.setCanceledOnTouchOutside(cancel);
            return dialog;
        }

    }

}
