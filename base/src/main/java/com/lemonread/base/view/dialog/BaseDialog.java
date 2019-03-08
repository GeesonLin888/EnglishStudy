package com.lemonread.base.view.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.Gravity;
import android.view.Window;
import android.view.WindowManager;

import com.lemonread.base.R;
import java.util.List;

import butterknife.ButterKnife;

/**
 * @desc dialog基类
 * @author zhao
 * @time 2019/3/6 17:03
 */
public abstract class BaseDialog extends Dialog {
    public BaseDialog(Context context) {
        super(context, R.style.TipDialog);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
        Window window = getWindow();
        WindowManager.LayoutParams params = window.getAttributes();
        params.height = WindowManager.LayoutParams.WRAP_CONTENT;
        params.width = WindowManager.LayoutParams.WRAP_CONTENT;
        window.setAttributes(params);
        window.setGravity(Gravity.CENTER);
        //去掉dialog的边距
        //window.getDecorView().setPadding(0, 0, 0, 0);
        initView();
    }
    protected abstract void initView();
}
