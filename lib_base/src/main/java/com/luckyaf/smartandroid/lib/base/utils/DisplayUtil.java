package com.luckyaf.smartandroid.lib.base.utils;

import android.app.Activity;
import android.app.Application;
import android.content.ComponentCallbacks;
import android.content.Context;
import android.content.res.Configuration;
import android.support.annotation.NonNull;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.WindowManager;

/**
 * 类描述：
 *
 * @author Created by luckyAF on 2018/6/25
 */
@SuppressWarnings("unused")
public class DisplayUtil {

    public static int SCREEN_WIDTH = -1;
    public static int SCREEN_HEIGHT = -1;
    public static float DIMEN_RATE = -1.0F;
    public static int DIMEN_DPI = -1;



    public static void initialize(Context context){
        WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics dm = new DisplayMetrics();
        if(null != windowManager){
            Display display = windowManager.getDefaultDisplay();
            display.getMetrics(dm);
        }
        DIMEN_RATE = dm.density / 1.0F;
        DIMEN_DPI = dm.densityDpi;
        SCREEN_WIDTH = dm.widthPixels;
        SCREEN_HEIGHT = dm.heightPixels;
        if(SCREEN_WIDTH > SCREEN_HEIGHT) {
            int t = SCREEN_HEIGHT;
            SCREEN_HEIGHT = SCREEN_WIDTH;
            SCREEN_WIDTH = t;
        }
    }


    /**
     * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
     */
    public static int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    /**
     * 根据手机的分辨率从 px(像素) 的单位 转成为 dp
     */
    public static int px2dip(Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }

    private static float sNowCompatDensity;
    private static float sNowCompatScaleDensity;

    /**
     * 设置自定义 density  在activity onCreate里面调用一下
     * @param activity      当前Activity
     * @param application   当前application
     */
    public static void setCustomDensity(@NonNull Activity activity, @NonNull final Application application){
        final DisplayMetrics appDisplayMetrics = application.getResources().getDisplayMetrics();
        if(sNowCompatDensity == 0){
            sNowCompatDensity = appDisplayMetrics.density;
            sNowCompatScaleDensity = appDisplayMetrics.scaledDensity;
            application.registerComponentCallbacks(new ComponentCallbacks() {
                @Override
                public void onConfigurationChanged(Configuration newConfig) {
                    if(null != newConfig && newConfig.fontScale > 0){
                        sNowCompatScaleDensity = application.getResources().getDisplayMetrics().scaledDensity;
                    }
                }
                @Override
                public void onLowMemory() {

                }
            });
        }
        final float targetDensity = appDisplayMetrics.widthPixels / 360;
        final float targetScaleDensity = targetDensity * (sNowCompatScaleDensity / sNowCompatDensity);
        final int targetDensityDpi = (int)(160 * targetDensity);

        appDisplayMetrics.density = targetDensity;
        appDisplayMetrics.scaledDensity = targetScaleDensity;
        appDisplayMetrics.densityDpi = targetDensityDpi;

        final DisplayMetrics activityDisplayMetrics = activity.getResources().getDisplayMetrics();
        activityDisplayMetrics.density = targetDensity;
        activityDisplayMetrics.scaledDensity = targetScaleDensity;
        activityDisplayMetrics.densityDpi = targetDensityDpi;
    }



}
