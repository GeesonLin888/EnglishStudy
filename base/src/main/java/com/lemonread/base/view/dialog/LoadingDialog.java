package com.lemonread.base.view.dialog;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.StyleRes;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.Window;
import android.widget.TextView;

import com.lemonread.base.R;
import com.lemonread.base.utils.EinkInvalidateUtils;

/**
 * @desc
 * @author zhao
 * @time 2019/3/6 16:29
 */
public class LoadingDialog extends Dialog {
    public LoadingDialog(@NonNull Context context) {
        super(context);
        init(context);
    }

    public LoadingDialog(@NonNull Context context, @StyleRes int themeResId) {
        super(context, themeResId);
        init(context);
    }

    protected LoadingDialog(@NonNull Context context, boolean cancelable, @Nullable OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
        init(context);
    }

    private void init(Context context){
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
    }

    TextView tvLoadingMsg;
    private long mStartShowTime;
    private static final int FRESH_TIME = 10 * 1000;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_layout);
        tvLoadingMsg = (TextView) findViewById(R.id.tv_loading_msg);

        if (!TextUtils.isEmpty(mLoadingMsg)) {
            tvLoadingMsg.setText(mLoadingMsg);
        }
        Window window = getWindow();
        if(window != null){
            window.setGravity(Gravity.CENTER);
        }
        setCancelable(mCancelable);
        setCanceledOnTouchOutside(mCancelable);
        setOnShowListener(new OnShowListener() {
            @Override
            public void onShow(DialogInterface dialog) {
                mStartShowTime = System.currentTimeMillis();
            }
        });
        setOnDismissListener(new OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                //如果显示的时间大于10s则全刷一遍
                if(System.currentTimeMillis() - mStartShowTime > FRESH_TIME){
                    //EventBus.getDefault().post(new MyEvent(EventConstants.REFRESH_FULL_SCREEN));
                    if(getWindow() != null){
                        EinkInvalidateUtils.fullRefresh(getWindow().getDecorView());
                    }
                }
            }
        });
    }

    private String mLoadingMsg;

    private boolean mCancelable = true;

    public void setMessage(String message) {
        this.mLoadingMsg =message;
    }

    public void setCancelable(boolean cancelable) {
        this.mCancelable =cancelable;
    }

    @Override
    public void onAttachedToWindow() {
        super.onAttachedToWindow();
        getOwnerActivity();
    }
}