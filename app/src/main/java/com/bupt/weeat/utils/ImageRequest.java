package com.bupt.weeat.utils;

import android.widget.ImageView;

import com.bupt.weeat.R;

/**
 * Created by zhaoruolei on 16/12/25.
 */


public class ImageRequest {
    private int type;  //类型 (大图，中图，小图)
    private String url; //需要解析的url
    private int placeHolder; //当没有成功加载的时候显示的图片
    private ImageView view; //ImageView的实例
    private int wifiStrategy;//加载策略，是否在wifi下才加载
    private int width;
    private int height;
    private  int scaleType;
    enum SCALE_TYPE{
        CenterCrop,
        CenterInside,
        fit
    };


    public ImageRequest(Builder builder) {
        this.type = builder.type;
        this.url = builder.url;
        this.view = builder.view;
        this.wifiStrategy = builder.wifiStrategy;
        this.placeHolder = builder.placeHolder;
        this.width = builder.width;
        this.height = builder.height;
        this.scaleType=builder.scaleType;
    }

    public int getWifiStrategy() {
        return wifiStrategy;
    }

    public void setWifiStrategy(int wifiStrategy) {
        this.wifiStrategy = wifiStrategy;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getPlaceHolder() {
        return placeHolder;
    }

    public void setPlaceHolder(int placeHolder) {
        this.placeHolder = placeHolder;
    }

    public ImageView getView() {
        return view;
    }

    public void setView(ImageView view) {
        this.view = view;
    }


    public static class Builder {
        private int type;  //类型 (大图，中图，小图)
        private String url; //需要解析的url
        private int placeHolder; //当没有成功加载的时候显示的图片
        private ImageView view; //ImageView的实例
        private int wifiStrategy;//加载策略，是否在wifi下才加载
        private int height;
        private int width;
        private int scaleType;

        public Builder() {

            this.type = ImageLoaderUtil.PIC_SMALL;
            this.url = "";
            this.placeHolder = R.drawable.loading_placeholer;
            this.view = null;
            this.wifiStrategy = ImageLoaderUtil.LOAD_IMAGE_NORMAL;
        }


        public Builder type(int type) {
            this.type = type;
            return this;
        }

        public Builder url(String url) {
            this.url = url;
            return this;
        }

        public Builder placeHolder(int placeHolder) {
            this.placeHolder = placeHolder;
            return this;

        }

        public Builder imageView(ImageView view) {
            this.view = view;
            return this;
        }

        public Builder width(int width) {
            this.width = width;
            return this;
        }

        public Builder height(int height) {
            this.height = height;
            return this;
        }

        public ImageRequest build() {
            return new ImageRequest(this);
        }

    }


}
