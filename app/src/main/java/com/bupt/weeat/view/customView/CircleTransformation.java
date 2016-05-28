package com.bupt.weeat.view.customView;


import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import com.squareup.picasso.Transformation;

public class CircleTransformation implements Transformation {
    private static final int STROKE_WIDTH = 3;

    @Override
    public Bitmap transform(Bitmap source) {
        int size = Math.min(source.getWidth(), source.getHeight());
        int x = (source.getWidth() - size) / 2;//坐标偏移量除2
        int y = (source.getHeight() - size) / 2;
        Bitmap squareBitmap = Bitmap.createBitmap(source, x, y, size, size);
        //以source为原图，创建新的图片，指定起始坐标以及新图像的高宽。
        if (squareBitmap != source) {
            source.recycle();
        }
        Bitmap bitmap = Bitmap.createBitmap(size, size, source.getConfig());
        //创建指定格式、大小的位图
        Canvas canvas = new Canvas(bitmap);//创建画布
        Paint avatarPaint = new Paint();
        avatarPaint.setAntiAlias(true);
        BitmapShader shader = new BitmapShader(squareBitmap, BitmapShader.TileMode.CLAMP, BitmapShader.TileMode.CLAMP);
        avatarPaint.setShader(shader);
        Paint outlinePaint = new Paint();
        outlinePaint.setColor(Color.WHITE);
        outlinePaint.setStyle(Paint.Style.STROKE);
        outlinePaint.setStrokeWidth(STROKE_WIDTH);
        outlinePaint.setAntiAlias(true);
        float radius = size / 2;
        canvas.drawCircle(radius, radius, radius, avatarPaint);
        //canvas.drawCircle(radius, radius, radius - STROKE_WIDTH / 2, outlinePaint);
        squareBitmap.recycle();
        return bitmap;

    }

    @Override
    public String key() {
        return "circleTransformation()";
    }
}
