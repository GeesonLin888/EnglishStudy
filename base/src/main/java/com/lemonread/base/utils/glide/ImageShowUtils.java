package com.lemonread.base.utils.glide;


import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.bumptech.glide.load.DecodeFormat;
import com.bumptech.glide.request.RequestOptions;
import com.lemonread.base.application.BaseApplication;
import com.lemonread.base.utils.glide.GlideRoundTransform;

import java.io.File;

/**
 * @desc
 * @author zhao
 * @time 2019/3/6 9:27
 */
public class ImageShowUtils {

    /**
     * 加载网络图片Util
     *
     * @param url  图片Url
     * @param iv   加载的ImageView
     * @param R_id 加载失败显示的图片
     */
    public static void imageShow(String url, ImageView iv, int R_id) {
        RequestOptions requestOptions = new RequestOptions()
                .placeholder(R_id)
                .error(R_id)
                .timeout(20*1000) //20秒
                .format(DecodeFormat.PREFER_ARGB_8888)
                .dontAnimate();

        Glide.with(BaseApplication.getContext())
                .load(url)

//                .listener(listener)
                .apply(requestOptions)
                .into(iv);
    }

    public static void imageShowNoHolder(String url, ImageView iv) {
        RequestOptions requestOptions = new RequestOptions()
                .placeholder(iv.getDrawable())
                .timeout(20*1000) //20秒
                .format(DecodeFormat.PREFER_ARGB_8888)
                .dontAnimate();

        Glide.with(BaseApplication.getContext())
                .load(url)
                .apply(requestOptions)
                .into(iv);
    }


    public static void imageShowRadius(String url, ImageView iv, int R_id, int radius) {
        /*requestOptions = new RequestOptions()
                .placeholder(R_id)
                .error(R_id)
                .timeout(20000) //20秒
                .transform(new GlideRoundTransform(radius))
                .dontAnimate();*/
        RequestOptions options = new RequestOptions()
                //.centerCrop()
                .placeholder(R_id)
                .error(R_id)
                .priority(Priority.HIGH)
                .timeout(20*1000) //20秒
                .transform(new GlideRoundTransform(radius));

        Glide.with(BaseApplication.getContext())
                .load(url)
//                .listener(listener)
                .apply(options)
                .into(iv);

    }

    public static void imageShow(File file, ImageView iv, int R_id) {
        RequestOptions requestOptions = new RequestOptions()
                .placeholder(R_id)
                .error(R_id)
                .timeout(20*1000) //20秒
                .dontAnimate();

        Glide.with(BaseApplication.getContext())
                .load(file)
                .apply(requestOptions).into(iv);
    }

   /* private static RequestListener listener = new RequestListener() {
        @Override
        public boolean onLoadFailed(@Nullable GlideException e, Object model, Target target, boolean isFirstResource) {
            return false;
        }

        @Override
        public boolean onResourceReady(Object resource, Object model, Target target, DataSource dataSource, boolean isFirstResource) {
            ToastUtils.show(BaseApplication.getContext(),"加载图片完成");
            return false;
        }
    };*/
}
