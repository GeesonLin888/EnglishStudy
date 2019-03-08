package com.lemonread.base.utils;

import android.content.Context;
import android.view.Gravity;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

/**
 * Created by xuyao on 3/17/17.
 */

public class ToastUtils {


    public static Toast toast;

    public static void show(Context context, String text) {
        if (text.contains("to connect to")) {
            text = "网络错误";
        }
        if (context == null) return;
        if (toast == null) {

            toast = Toast.makeText(context.getApplicationContext(), text, Toast.LENGTH_SHORT);

        } else {
            toast.setText(text);
        }

        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();
    }

    public static void showLong(Context context, String text) {

        if (context == null) return;
        if (toast == null) {

            toast = Toast.makeText(context.getApplicationContext(), text, Toast.LENGTH_LONG);

        } else {
            toast.setText(text);
        }

        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();
    }

    public static void show(Context context, int resId) {
        if (context == null) return;
        if (toast == null) {

            toast = Toast.makeText(context.getApplicationContext(), resId, Toast.LENGTH_SHORT);

        } else {
            toast.setText(resId);
        }

        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();
    }


    public static void show(Context context, String text, int resImg) {
        if (context == null) return;
        if (toast == null) {

            toast = Toast.makeText(context.getApplicationContext(), text, Toast.LENGTH_SHORT);

        } else {
            toast.setText(text);
        }
        LinearLayout view = (LinearLayout) toast.getView();
        ImageView imageView = new ImageView(context);
        imageView.setBackgroundResource(resImg);
        view.addView(imageView);

        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();
    }
}
