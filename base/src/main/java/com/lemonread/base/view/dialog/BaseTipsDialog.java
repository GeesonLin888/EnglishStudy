package com.lemonread.base.view.dialog;

import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.annotation.ColorInt;
import android.support.annotation.DrawableRes;
import android.support.annotation.LayoutRes;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.TextView;

import com.lemonread.base.R;
import com.lemonread.base.utils.glide.ImageShowUtils;


public abstract class BaseTipsDialog extends Dialog {
    private View mView;
    private boolean isBase = true;

    public BaseTipsDialog(Context context, @LayoutRes int r_id, boolean cancelable) {
        super(context, R.style.TipDialog);
        mView = LayoutInflater.from(getContext()).inflate(r_id, null);
        if (r_id != R.layout.dialog_base_tips_layout) {
            isBase = false;
        }
        initView(new DialogHelper());
        setCancelable(cancelable);
        setCanceledOnTouchOutside(cancelable);
        setContentView(mView);
        getWindow().setGravity(Gravity.CENTER);
    }

    //默认使用dialog_base_layout布局
    public BaseTipsDialog(Context context, boolean cancelable) {
        this(context, R.layout.dialog_base_tips_layout, cancelable);
    }

    public abstract void initView(DialogHelper helper);

    public enum Visibility {
        VISIBLE,
        INVISIBLE,
        GONE
    }

    public class DialogHelper {

        // 通过控件的Id获取对于的控件，如果没有则加入views
        public <T extends View> T getView(int viewId) {
            View view = mView.findViewById(viewId);
            return (T) view;
        }

        //取消显示Dialog
        public void setDismiss() {
            dismiss();
        }

        /**
         * 设置默认布局 dialog_base_tips_layout 显示内容及点击事件
         *
         * @param title           标题 为空则标题visible为Gone
         * @param content         内容 不可为空
         * @param confirmListener 为空则默认点击Dialog消失
         * @param showCancel      confirmListener为空时 不起效，tvCancel visible为Gone
         *                        confirmListener不为空 showCancel为false时，visible为Gone
         */
        public void set(String title, String content, View.OnClickListener confirmListener, boolean showCancel) {
            if (!isBase) {
                return;
            }
            TextView tvTitle = getView(R.id.tv_title);
            TextView tvContent = getView(R.id.tv_content);
            TextView tvConfirm = getView(R.id.tv_confirm);
            TextView tvCancel = getView(R.id.tv_cancel);

            if (title == null) {
                tvTitle.setVisibility(View.GONE);
            } else {
                tvTitle.setText(title);
            }
            tvContent.setText(content);

            if (confirmListener == null) {
                tvConfirm.setOnClickListener(mDismissClickListener);
                tvCancel.setVisibility(View.GONE);
            } else {
                tvConfirm.setOnClickListener(confirmListener);
                if (showCancel) {
                    tvCancel.setOnClickListener(mDismissClickListener);
                } else {
                    tvCancel.setVisibility(View.GONE);
                }
            }
        }

        public void setTitle(String title) {
            setText(R.id.tv_title, title);
        }

        public void setContent(String content) {
            setText(R.id.tv_content, content);
        }

        private View.OnClickListener mDismissClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        };

        public void setText(int viewId, String text) {
            TextView view = getView(viewId);
            view.setText(text);
        }

        public void setEnable(int viewId, boolean enable) {
            View view = getView(viewId);
            view.setEnabled(enable);
        }

        public void setOnClickListener(int viewId, View.OnClickListener listener) {
            View view = getView(viewId);
            view.setOnClickListener(listener);
        }


        public void setVisible(int viewId, Visibility visible) {
            View view = getView(viewId);
            if (visible == Visibility.VISIBLE) {
                view.setVisibility(View.VISIBLE);
            } else if (visible == Visibility.INVISIBLE) {
                view.setVisibility(View.INVISIBLE);
            } else if (visible == Visibility.GONE) {
                view.setVisibility(View.GONE);
            }
        }

        /**
         * 为TextView设置字体颜色
         *
         * @param viewId
         * @param color
         * @return
         */
        public void setTextColor(int viewId, @ColorInt int color) {
            TextView view = getView(viewId);
            view.setTextColor(color);
        }


        /**
         * 为TextView设置字体大小
         *
         * @param viewId
         * @param textSize
         * @return
         */
        public void setTextSize(int viewId, float textSize) {
            TextView view = getView(viewId);
            view.setTextSize(textSize);
        }

        /**
         * 为TextView设置字体粗细
         *
         * @param viewId
         * @param isFake
         * @return
         */
        public void setTextBold(int viewId, boolean isFake) {
            TextView view = getView(viewId);
            view.getPaint().setFakeBoldText(isFake);
        }

        /**
         * 为View设置背景图片资源
         *
         * @param viewId
         * @param R_id
         * @return
         */
        public void setBackgroundResource(int viewId, @DrawableRes int R_id) {
            View view = getView(viewId);
            view.setBackgroundResource(R_id);
        }


        /**
         * 为View设置背景颜色
         *
         * @param viewId
         * @param color_id
         * @return
         */
        public void setBackgroundColor(int viewId, @ColorInt int color_id) {
            View view = getView(viewId);
            view.setBackgroundColor(color_id);
        }

        /**
         * 为ImageView设置图片
         *
         * @param viewId
         * @param drawableId
         * @return
         */
        public void setImageResource(int viewId, int drawableId) {
            ImageView view = getView(viewId);
            view.setImageResource(drawableId);

        }

        /**
         * 为ImageView设置图片
         *
         * @param viewId
         * @param drawable
         * @return
         */
        public void setImageDrawable(int viewId, Drawable drawable) {
            ImageView view = getView(viewId);
            view.setImageDrawable(drawable);
        }

        /**
         * 为ImageView设置图片
         *
         * @param viewId
         * @return
         */
        public void setImageByUrl(int viewId, String url, @DrawableRes int R_id) {
            // ImageLoader.getInstance(3, Type.LIFO).loadImage(url,
            // (ImageView) getView(viewId));
            ImageView view = getView(viewId);
            ImageShowUtils.imageShow(url, view, R_id);
        }

        /**
         * 为ImageView设置圆角图片
         *
         * @param viewId
         * @return
         */
        public void setImageByUrlWithRadius(int viewId, String url, @DrawableRes int R_id, int radius) {
            // ImageLoader.getInstance(3, Type.LIFO).loadImage(url,
            // (ImageView) getView(viewId));
            ImageView view = getView(viewId);
            ImageShowUtils.imageShowRadius(url, view, R_id, radius);
        }

        /**
         * 为RatingBar 设置星数
         *
         * @param viewId
         * @param rating 星级 1~5
         * @return
         */
        public void setRating(int viewId, int rating) {
            RatingBar view = getView(viewId);
            if (rating >= 1 & rating <= 5) {
                view.setProgress(rating);
            }
        }

        /**
         * 为RatingBar 设置星数
         *
         * @param viewId
         * @param rating 星级 1~5
         * @return
         */
        public void setRating2(int viewId, int rating) {
            RatingBar view = getView(viewId);
            view.setProgress(rating);
        }

        /**
         * 为ProgressBar 设置最大值
         *
         * @param viewId
         * @param max
         * @return
         */
        public void setProgressMax(int viewId, int max) {
            ProgressBar pb = getView(viewId);
            pb.setMax(max);
        }

        /**
         * 为ProgressBar 设置进度
         *
         * @param viewId
         * @param progress
         * @return
         */
        public void setProgress(int viewId, int progress) {
            ProgressBar pb = getView(viewId);
            pb.setProgress(progress);
        }
    }
}
