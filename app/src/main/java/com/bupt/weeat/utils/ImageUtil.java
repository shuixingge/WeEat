package com.bupt.weeat.utils;

import android.widget.ImageView;

import com.bupt.weeat.R;
import com.squareup.picasso.Picasso;

/**
 * Created by zhaoruolei1992 on 2016/5/26.
 */
public class ImageUtil {
    public static void loadFullImg(ImageView v, String url) {
        Picasso.with(v.getContext())
                .load(url)
                .placeholder(R.drawable.loading_placeholer)
                .resize(360, 200)
                .into(v);
    }

    public static void loadSmallImg(ImageView v, String url) {
        Picasso.with(v.getContext())
                .load(url)
                .placeholder(R.drawable.loading_placeholer)
                .resize(80, 60)
                .into(v);
    }
}
