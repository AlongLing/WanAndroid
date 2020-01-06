package com.along.wanandroid.home.adapter;

import android.content.Context;
import android.widget.ImageView;

import com.along.wanandroid.home.model.bean.BannerBean;
import com.bumptech.glide.Glide;
import com.youth.banner.loader.ImageLoader;

public class GlideImageLoader extends ImageLoader {
    @Override
    public void displayImage(Context context, Object bannerData, ImageView imageView) {
        BannerBean bannerBean = (BannerBean) bannerData;
        Glide.with(context).load(bannerBean.getImagePath()).into(imageView);
    }

    //方便自定义View的创建
    @Override
    public ImageView createImageView(Context context) {
        return super.createImageView(context);
    }
}
