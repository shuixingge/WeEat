package com.bupt.weeat.utils;

import android.content.Context;

/**
 * Created by zhaoruolei on 16/12/25.
 */

public class ImageLoaderUtil {
    private static ImageLoaderUtil mInstance;
    public static final int LOAD_IMAGE_ONLY_WIFI = 0;
    public static final int LOAD_IMAGE_NORMAL = 1;

    public static final int PIC_LARGE = 0;
    public static final int PIC_MEDIUM = 1;
    public static final int PIC_SMALL = 2;

    private BaseImageLoaderProvider mDefaultprovider=
            new PicassoImageLoaderProvider();
    private  BaseImageLoaderProvider provider=mDefaultprovider;

    private ImageLoaderUtil() {

    }

    public static ImageLoaderUtil getInstance() {
        if (mInstance == null) {
            synchronized (ImageLoaderUtil.class) {
                if (mInstance == null) {
                    mInstance = new ImageLoaderUtil();
                }
            }
        }

        return null;
    }

    public void loadImage(Context context,ImageRequest request) {

       provider.loadImage(context ,request);

    }

    public BaseImageLoaderProvider getProvider() {
        return provider ;
    }

    public void setProvider(BaseImageLoaderProvider provider) {
        this.provider = provider;
    }
}
