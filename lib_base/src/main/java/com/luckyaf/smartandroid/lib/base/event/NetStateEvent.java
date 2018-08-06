package com.luckyaf.smartandroid.lib.base.event;

import com.luckyaf.smartandroid.lib.base.utils.NetUtil;

/**
 * 类描述：
 *
 * @author Created by luckyAF on 2018/8/1
 */
public class NetStateEvent {
    int netState;


    public NetStateEvent(int  state){
        netState = state;
    }

    public boolean isNone(){
        return netState != NetUtil.NETWORK_NONE;
    }

    public boolean isUnKnown(){
        return netState == NetUtil.NETWORK_UNKNOWN;
    }
    public boolean isMobile(){
        return netState == NetUtil.NETWORK_MOBILE;
    }
    public boolean isWIFI(){
        return netState == NetUtil.NETWORK_WIFI;
    }
}
