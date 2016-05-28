package com.bupt.weeat.db;


import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.bupt.weeat.utils.LogUtils;

public class DishOpenHelper extends SQLiteOpenHelper {
    private static final String TAG = DishOpenHelper.class.getSimpleName();
    private static final String CREATE_NEW_DISH = "create table NewDish("
            + "id integer primary key autoincrement ,"
            + "dish_name text,"
            + "dish_price text,"
            + "dish_flavor text,"
            + "dish_praise text,"
            + "dish_windowId text,"
            + "dish_heat text,"
            + "dish_windowName text,"
            + "dish_updateTime text,"
            + "dish_imageUrl text,"
            + "dish_location text)";
    private static final String CREATE_RECOMMENDATION_DISH = "create table RecommendationDish("
            + "id integer primary key autoincrement ,"
            + "dish_name text,"
            + "dish_price text,"
            + "dish_flavor text,"
            + "dish_praise text,"
            + "dish_windowId text,"
            + "dish_heat text,"
            + "dish_windowName text,"
            + "dish_updateTime text,"
            + "dish_imageUrl text,"
            + "dish_location text)";

    private static final String CREATE_WEEK_DISH = "create table WeekDish("
            + "id integer primary key autoincrement ,"
            + "dish_name text,"
            + "dish_price text,"
            + "dish_flavor text,"
            + "dish_praise text,"
            + "dish_windowId text,"
            + "dish_heat text,"
            + "dish_updateTime text,"
            + "dish_windowName text,"
            + "dish_imageUrl text,"
            + "dish_location text)";

    public DishOpenHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
        LogUtils.i(TAG, "onCreate DishOpenHelper");
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        LogUtils.i(TAG, "onCreate");
        db.execSQL(CREATE_NEW_DISH);
        db.execSQL(CREATE_RECOMMENDATION_DISH);
        db.execSQL(CREATE_WEEK_DISH);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        LogUtils.i(TAG, " onUpgrade");

    }
}
