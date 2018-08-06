package com.luckyaf.smartandroid.lib.base.widget.recyclerview;

import android.view.View;
import android.view.ViewGroup;

/**
 * 类描述：
 *
 * @author Created by luckyAF on 2018/7/16
 */
public interface OnItemClickListener<T> {

    /**
     * item点击
     * @param parent  父
     * @param view   view
     * @param t      数据
     * @param position  位置
     */
    void onItemClick(ViewGroup parent, View view, T t, int position);

    /**
     * item点击
     * @param parent  父
     * @param view   view
     * @param t      数据
     * @param position  位置
     * @return
     */
    boolean onItemLongClick(ViewGroup parent, View view, T t, int position);
}
