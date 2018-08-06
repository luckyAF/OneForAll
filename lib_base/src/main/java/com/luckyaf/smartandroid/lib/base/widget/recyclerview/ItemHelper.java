package com.luckyaf.smartandroid.lib.base.widget.recyclerview;

/**
 * 类描述：
 *
 * @author Created by luckyAF on 2018/7/16
 */
public interface ItemHelper {

    int getItemLayoutId();

    void onBind(CommonViewHolder holder);

}
