package com.bupt.weeat.utils;

import android.support.v4.app.FragmentActivity;

import java.util.ArrayList;
import java.util.List;


public class ActivityUtils {
    public static List<FragmentActivity> Activitylist = new ArrayList<>();

    public static void addActivity(FragmentActivity activity) {
        Activitylist.add(activity);
    }

    public static void removeActivity(FragmentActivity activity) {
        Activitylist.remove(activity);

    }

    public static void finishAll() {
        for (FragmentActivity activity : Activitylist) {
            if (!activity.isFinishing()) {
                activity.finish();
            }
        }
    }
}
