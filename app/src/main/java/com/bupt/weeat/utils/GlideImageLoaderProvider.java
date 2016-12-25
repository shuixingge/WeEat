package com.bupt.weeat.utils;

import android.content.Context;

import com.bumptech.glide.Glide;

/**
 * Created by zhaoruolei on 16/12/25.
 */

public class GlideImageLoaderProvider implements BaseImageLoaderProvider {
    @Override
    public void loadImage(Context context, ImageRequest request) {
        Glide.with(context).
                load(request.getUrl())
                .placeholder(request.getPlaceHolder())
                .into(request.getView());

    }
}
