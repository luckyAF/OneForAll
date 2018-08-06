package com.luckyaf.oneforall.module.test.app;

import android.app.Application;

import com.alibaba.android.arouter.BuildConfig;
import com.alibaba.android.arouter.launcher.ARouter;

/**
 * 类描述：
 *
 * @author Created by luckyAF on 2018/7/3
 */
public class App extends Application {
    @Override
    public void onCreate(){
        super.onCreate();
//        if (BuildConfig.DEBUG) {           // These two lines must be written before init, otherwise these configurations will be invalid in the init process
//            ARouter.openLog();     // Print log
//            ARouter.openDebug();   // Turn on debugging mode (If you are running in InstantRun mode, you must turn on debug mode! Online version needs to be closed, otherwise there is a security risk)
//        }
//        ARouter.init(this);

        if (BuildConfig.DEBUG) {
            ARouter.openLog();
            ARouter.openDebug();
            ARouter.printStackTrace();
        }
        ARouter.init(this);
    }
}
