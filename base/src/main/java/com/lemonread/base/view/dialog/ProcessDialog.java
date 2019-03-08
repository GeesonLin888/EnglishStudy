package com.lemonread.base.view.dialog;

import android.content.Context;

/**
 * @desc 加载进度框
 * @author zhao
 * @time 2019/3/6 16:36
 */
public class ProcessDialog {
    private LoadingDialog mDialog = null;

    //单一实例
    private static ProcessDialog mInstance;

    public static ProcessDialog getInstance() {
        if (mInstance == null) {
            synchronized (ProcessDialog.class) {
                if (mInstance == null) {
                    mInstance = new ProcessDialog();
                }
            }
        }

        return mInstance;
    }

    /**
     * @param context
     * @param text
     * @param canBack 能否点击back键dismiss Dialog
     */
    public void openDialog(Context context, String text, boolean canBack) {
        if (context != null) {
            mDialog = new LoadingDialog(context);
        }
        if (mDialog != null && !mDialog.isShowing()) {
            mDialog.setMessage(text);
            mDialog.setCanceledOnTouchOutside(canBack);
            mDialog.setCancelable(canBack);
            mDialog.show();
        }
    }

    public void closeDialog() {
        if (isDialogShowing()) {
            mDialog.dismiss();
            mDialog = null;
        }
    }

    public boolean isDialogShowing() {
        return (mDialog != null && mDialog.isShowing());
    }
}
