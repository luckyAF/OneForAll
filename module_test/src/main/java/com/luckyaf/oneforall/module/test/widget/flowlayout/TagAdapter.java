package com.luckyaf.oneforall.module.test.widget.flowlayout;

import android.util.Log;
import android.view.View;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * 类描述：
 *
 * @author Created by luckyAF on 2018/7/6
 */
public abstract  class TagAdapter<T> {
    private List<T> mTagData;
    private OnDataChangedListener mOnDataChangedListener;
    private HashSet<Integer> mCheckedPosList = new HashSet<Integer>();

    public TagAdapter(List<T> data) {
        mTagData = data;
    }

    public TagAdapter(T[] data) {
        mTagData = new ArrayList<T>(Arrays.asList(data));
    }

    interface OnDataChangedListener {
        void onChanged();
    }

    void setOnDataChangedListener(OnDataChangedListener listener) {
        mOnDataChangedListener = listener;
    }

    public void setSelectedList(int... poses) {
        Set<Integer> set = new HashSet<>();
        for (int pos : poses) {
            set.add(pos);
        }
        setSelectedList(set);
    }

    public void setSelectedList(Set<Integer> set) {
        mCheckedPosList.clear();
        if (set != null) {
            mCheckedPosList.addAll(set);
        }
        notifyDataChanged();
    }

    HashSet<Integer> getPreCheckedList() {
        return mCheckedPosList;
    }


    public int getCount() {
        return mTagData == null ? 0 : mTagData.size();
    }

    public void notifyDataChanged() {
        if (mOnDataChangedListener != null) {
            mOnDataChangedListener.onChanged();
        }
    }

    public T getItem(int position) {
        return mTagData.get(position);
    }

    public abstract View getView(FlowLayout parent, int position, T t);


    public void onSelected(int position, View view){
        Log.d("zhy","onSelected " + position);
    }

    public void unSelected(int position, View view){
        Log.d("zhy","unSelected " + position);
    }

    public boolean setSelected(int position, T t) {
        return false;
    }


}
