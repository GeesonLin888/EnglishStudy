package com.lemonread.base.view.adapter;

/**
 * Created by zhd66 on 2015/11/10.
 */

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.annotation.ColorInt;
import android.support.annotation.DrawableRes;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.TextView;

import com.lemonread.base.utils.glide.ImageShowUtils;
import com.lemonread.base.utils.LogUtils;


public class BaseListViewHolder {
    private final SparseArray<View> mViews;
    private int mPosition;
    private View mConvertView;
    private Context mContext;

    private int itemWidth = 0;
    private int itemHeight = 0;
    private int columNum = 0;

    private BaseListViewHolder(Context context, ViewGroup parent, int layoutId,
                               int position, int rowNum) {
        this.mPosition = position;
        this.mViews = new SparseArray<View>();
        mConvertView = LayoutInflater.from(context).inflate(layoutId, parent,
                false);
        mContext = context;
        // setTag
        mConvertView.setTag(this);
        if (rowNum != 0) {
            if (parent instanceof GridView) {

                GridView gv = (GridView) parent;
                columNum = gv.getNumColumns();

                itemWidth = (gv.getWidth() - gv.getHorizontalSpacing() * (columNum - 1)) / columNum;
                itemHeight = (gv.getHeight() - gv.getVerticalSpacing()) / rowNum;
                GridView.LayoutParams params = (GridView.LayoutParams) mConvertView.getLayoutParams();
                params.width = itemWidth;
                params.height = itemHeight;
                mConvertView.setLayoutParams(params);
                LogUtils.log("BaseListViewHolder----gridview---itemWidth----" + itemWidth + "----itemHeight-----" + itemHeight + "---gv.getHeight()---" + gv.getHeight() + "--columNum--" + columNum);
            } else if (parent instanceof ListView) {

                ListView lv = (ListView) parent;

                LogUtils.log("ListView.getHeight=" + lv.getHeight());
                itemHeight = lv.getHeight() / rowNum;
                ListView.LayoutParams params = (ListView.LayoutParams) mConvertView.getLayoutParams();
                params.height = itemHeight;
                mConvertView.setLayoutParams(params);
                LogUtils.log("BaseListViewHolder----listview---itemWidth----" + itemWidth + "----itemHeight-----" + itemHeight + ",rowNum=" + rowNum);
            }
        }


    }

    /**
     * 拿到一个ViewHolder对象
     *
     * @param context
     * @param convertView
     * @param parent
     * @param layoutId
     * @param position
     * @return
     */
    public static BaseListViewHolder get(Context context, View convertView,
                                         ViewGroup parent, int layoutId, int position, int rowNum) {
        if (convertView == null) {
            return new BaseListViewHolder(context, parent, layoutId, position, rowNum);
        }
        BaseListViewHolder vh = (BaseListViewHolder) convertView.getTag();
        if (rowNum != 0) {
            if (parent instanceof GridView) {

                GridView gv = (GridView) parent;
                int columNum = gv.getNumColumns();
                int itemWidth = (gv.getWidth() - gv.getHorizontalSpacing() * (columNum - 1)) / columNum;
                int itemHeight = (gv.getHeight() - gv.getVerticalSpacing()) / rowNum;
                GridView.LayoutParams params = (GridView.LayoutParams) convertView.getLayoutParams();
                params.width = itemWidth;
                params.height = itemHeight;
                convertView.setLayoutParams(params);

            } else if (parent instanceof ListView) {

                ListView lv = (ListView) parent;
                int itemHeight = lv.getHeight() / rowNum;

                ListView.LayoutParams params = (ListView.LayoutParams) convertView.getLayoutParams();
                params.height = itemHeight;
                convertView.setLayoutParams(params);

            }
        }
        return vh;
    }

    public View getConvertView() {
        return mConvertView;
    }

    /**
     * 通过控件的Id获取对于的控件，如果没有则加入views
     *
     * @param viewId
     * @return
     */
    public <T extends View> T getView(int viewId) {
        View view = mViews.get(viewId);
        if (view == null) {
            view = mConvertView.findViewById(viewId);
            mViews.put(viewId, view);
        }
        return (T) view;
    }

    /**
     * 为TextView设置字符串
     *
     * @param viewId
     * @param text
     * @return
     */
    public BaseListViewHolder setText(int viewId, String text) {
        TextView view = getView(viewId);
        view.setText(text);
        return this;
    }

    /**
     * 为TextView设置字体颜色
     *
     * @param viewId
     * @param color
     * @return
     */
    public BaseListViewHolder setTextColor(int viewId, @ColorInt int color) {
        TextView view = getView(viewId);
        view.setTextColor(color);
        return this;
    }


    /**
     * 为TextView设置字体大小
     *
     * @param viewId
     * @param textSize
     * @return
     */
    public BaseListViewHolder setTextSize(int viewId, float textSize) {
        TextView view = getView(viewId);
        view.setTextSize(textSize);
        return this;
    }

    /**
     * 为TextView设置字体粗细
     *
     * @param viewId
     * @param isFake
     * @return
     */
    public BaseListViewHolder setTextBold(int viewId, boolean isFake) {
        TextView view = getView(viewId);
        view.getPaint().setFakeBoldText(isFake);
        return this;
    }

    /**
     * 为View设置背景图片资源
     *
     * @param viewId
     * @param R_id
     * @return
     */
    public BaseListViewHolder setBackgroundResource(int viewId, @DrawableRes int R_id) {
        View view = getView(viewId);
        view.setBackgroundResource(R_id);
        return this;
    }


    /**
     * 为View设置背景颜色
     *
     * @param viewId
     * @param color_id
     * @return
     */
    public BaseListViewHolder setBackgroundColor(int viewId, @ColorInt int color_id) {
        View view = getView(viewId);
        view.setBackgroundColor(color_id);
        return this;
    }

    /**
     * 为ImageView设置图片
     *
     * @param viewId
     * @param drawableId
     * @return
     */
    public BaseListViewHolder setImageResource(int viewId, int drawableId) {
        ImageView view = getView(viewId);
        view.setImageResource(drawableId);

        return this;
    }

    /**
     * 为ImageView设置图片
     *
     * @param viewId
     * @param drawable
     * @return
     */
    public BaseListViewHolder setImageDrawable(int viewId, Drawable drawable) {
        ImageView view = getView(viewId);
        view.setImageDrawable(drawable);
        return this;
    }

    /**
     * 为ImageView设置图片
     *
     * @param viewId
     * @return
     */
    public BaseListViewHolder setImageByUrl(int viewId, String url, @DrawableRes int R_id) {
        // ImageLoader.getInstance(3, Type.LIFO).loadImage(url,
        // (ImageView) getView(viewId));
        ImageView view = getView(viewId);
        ImageShowUtils.imageShow(url, view, R_id);
        return this;
    }
    /**
     * 为ImageView设置圆角图片
     *
     * @param viewId
     * @return
     */
    public BaseListViewHolder setImageByUrlWithRadius(int viewId, String url, @DrawableRes int R_id, int radius) {
        // ImageLoader.getInstance(3, Type.LIFO).loadImage(url,
        // (ImageView) getView(viewId));
        ImageView view = getView(viewId);
        ImageShowUtils.imageShowRadius(url, view, R_id,radius);
        return this;
    }

    /**
     * 为RatingBar 设置星数
     *
     * @param viewId
     * @param rating 星级 1~5
     * @return
     */
    public BaseListViewHolder setRating(int viewId, int rating) {
        RatingBar view = getView(viewId);
        if (rating >= 1 & rating <= 5) {
            view.setProgress(rating);
        }
        return this;
    }
    /**
     * 为RatingBar 设置星数
     *
     * @param viewId
     * @param rating 星级 1~5
     * @return
     */
    public BaseListViewHolder setRating2(int viewId, int rating) {
        RatingBar view = getView(viewId);
        view.setProgress(rating);
        return this;
    }

    /**
     * 为ProgressBar 设置最大值
     *
     * @param viewId
     * @param max
     * @return
     */
    public BaseListViewHolder setProgressMax(int viewId, int max) {
        ProgressBar pb = getView(viewId);
        pb.setMax(max);
        return this;
    }

    /**
     * 为ProgressBar 设置进度
     *
     * @param viewId
     * @param progress
     * @return
     */
    public BaseListViewHolder setProgress(int viewId, int progress) {
        ProgressBar pb = getView(viewId);
        pb.setProgress(progress);
        return this;
    }

    public enum Visibility {
        VISIBLE,
        INVISIBLE,
        GONE
    }

    /**
     * 设置View可见性
     *
     * @param viewId
     * @param visible 1.VISIBLE 2.INVISIBLE 3.GONE
     * @return
     */
    public BaseListViewHolder setVisible(int viewId, Visibility visible) {
        View view = getView(viewId);
        if (visible == Visibility.VISIBLE) {
            view.setVisibility(View.VISIBLE);
        } else if (visible == Visibility.INVISIBLE) {
            view.setVisibility(View.INVISIBLE);
        } else if (visible == Visibility.GONE) {
            view.setVisibility(View.GONE);
        }

        return this;
    }

    public BaseListViewHolder setEnable(int viewId, boolean enable) {
        View view = getView(viewId);
        view.setEnabled(enable);
        return this;
    }

    /**
     * 关于事件的
     */
    public BaseListViewHolder setOnClickListener(int viewId,
                                                 View.OnClickListener listener) {
        View view = getView(viewId);
        view.setOnClickListener(listener);
        return this;
    }

    public int getPosition() {
        return mPosition;
    }


    public BaseListViewHolder setLineLayoutHeight(int viewId, GridView gv) {
        LinearLayout view = getView(viewId);
        AbsListView.LayoutParams params = (AbsListView.LayoutParams) view.getLayoutParams();

        params.height = gv.getHeight();
        view.setLayoutParams(params);
        return this;
    }
}
