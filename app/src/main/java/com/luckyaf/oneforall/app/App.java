package com.luckyaf.oneforall.app;

import android.app.Application;

import com.alibaba.android.arouter.launcher.ARouter;

/**
 * 类描述：
 *
 * @author Created by luckyAF on 2018/7/17
 */
public class App extends Application{
    @Override
    public void onCreate(){
        super.onCreate();
        ARouter.init(this);
    }
}
