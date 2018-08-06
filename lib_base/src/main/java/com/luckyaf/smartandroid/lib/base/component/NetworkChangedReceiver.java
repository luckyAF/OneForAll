package com.luckyaf.smartandroid.lib.base.component;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.support.annotation.RequiresPermission;

import com.luckyaf.smartandroid.lib.base.event.NetStateEvent;
import com.luckyaf.smartandroid.lib.base.utils.NetUtil;

/**
 * 类描述：
 *
 * @author Created by luckyAF on 2018/8/1
 */

public class NetworkChangedReceiver extends BroadcastReceiver {
   // <uses-permission android:name="android.permission.ACCESS_NETWORK_STATE"/>
   // <uses-permission android:name="android.permission.INTERNET"/>

//
//    //在onResume()方法注册
//    @Override
//    protected void onResume() {
//        if (netWorkStateReceiver == null) {
//            netWorkStateReceiver = new NetWorkStateReceiver();
//        }
//        IntentFilter filter = new IntentFilter();
//        filter.addAction(ConnectivityManager.CONNECTIVITY_ACTION);
//        registerReceiver(netWorkStateReceiver, filter);
//        System.out.println("注册");
//        super.onResume();
//    }
//
//    //onPause()方法注销
//    @Override
//    protected void onPause() {
//        unregisterReceiver(netWorkStateReceiver);
//        System.out.println("注销");
//        super.onPause();
//    }



    @Override
    @RequiresPermission(android.Manifest.permission.ACCESS_NETWORK_STATE)
    public void onReceive(Context context, Intent intent) {
        if (ConnectivityManager.CONNECTIVITY_ACTION.equalsIgnoreCase(intent.getAction())) {
            int netWorkState = NetUtil.getNetWorkState(context);
            RxBus.getInstance().post(new NetStateEvent(netWorkState));
        }
    }
}
