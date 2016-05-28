package com.bupt.weeat.db;


import android.content.Context;
import android.content.SharedPreferences;

import com.bupt.weeat.Constant;

public class SharedPreferencesHelper {
    public static boolean isFirstEnter(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(Constant.LOGTAG, Context.MODE_PRIVATE);
        return sharedPreferences.getBoolean("isFirstEnter", true);
    }
    public static void setTheme(Context context, boolean is) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(Constant.LOGTAG, Context.MODE_PRIVATE);
        sharedPreferences.edit().putBoolean("theme", is).apply();
    }
    public static boolean getTheme(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(Constant.LOGTAG, Context.MODE_PRIVATE);
        return sharedPreferences.getBoolean("theme", false);
    }
}
