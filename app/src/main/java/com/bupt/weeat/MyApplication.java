package com.bupt.weeat;

import android.app.Application;
import android.content.Context;

import com.bupt.weeat.skinloader.load.SkinManager;
import com.squareup.leakcanary.LeakCanary;

import cn.bmob.v3.Bmob;

public class MyApplication extends Application {
    private static final String TAG = "MyApplication";
    private static Context mContext;
    private static MyApplication myApplication = null;


    public static MyApplication getMyApplication() {
        return myApplication;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        myApplication = this;
        mContext = getApplicationContext();
        LeakCanary.install(this);
        Bmob.initialize(myApplication, Constant.APPLICATION_ID);
        initSkinLoader();
    }

    /**
     * Must call init first
     */
    private void initSkinLoader() {
        SkinManager.getInstance().init(mContext);
        SkinManager.getInstance().load();
    }


    public static Context getContext() {
        return mContext;
    }


}

