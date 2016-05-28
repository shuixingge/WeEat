package com.bupt.weeat.utils;


import android.content.Context;
import android.widget.Toast;

public class ToastUtils {
    private static Toast mToast;

    public static void showToast(Context mContext, int resId, int duration) {
        if (mToast == null) {
            mToast = Toast.makeText(mContext, resId, duration);
        } else {
            mToast.setText(resId);
        }
        mToast.show();
    }

    public static void showToast(Context mContext, String msg, int duration) {
        if (mToast == null) {
            mToast = Toast.makeText(mContext, msg, duration);
        } else {
            mToast.setText(msg);
        }
        mToast.show();
    }

    public static void clearToast() {
        if (mToast != null) {
            mToast.cancel();
        }
    }
}
