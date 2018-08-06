package com.luckyaf.smartandroid.lib.base.widget.recyclerview;

import android.support.v7.widget.RecyclerView;

/**
 * 类描述：
 *
 * @author Created by luckyAF on 2018/7/16
 */
@SuppressWarnings("unused")
public class HeaderFooterAdapter extends AbstractHeaderFooterAdapterWrapper {


    public HeaderFooterAdapter(RecyclerView.Adapter mInnerAdapter) {
        super(mInnerAdapter);
    }

    public void addFooterView(ItemHelper footerHelper) {
        addFooterView(new ItemData(footerHelper.getItemLayoutId(), footerHelper));
    }

    public void setFooterView(ItemHelper footerHelper){
        setFooterView(new ItemData(footerHelper.getItemLayoutId(), footerHelper));
    }

    public void addHeaderView(ItemHelper headerHelper){
        addHeaderView(headerHelper.getItemLayoutId(),headerHelper);
    }

    public void addHeaderView(ItemHelper headerHelper,int cacheSize){
        addHeaderView( headerHelper.getItemLayoutId(),cacheSize);
    }

    public void setHeaderView(int position,ItemHelper headerHelper){
        setHeaderView(position,headerHelper.getItemLayoutId(),headerHelper);
    }
    public void setHeaderView(int position,ItemHelper headerHelper,int cacheSize){
        setHeaderView(position,headerHelper.getItemLayoutId(),headerHelper,cacheSize);
    }


    @Override
    protected void onBindItemHolder(CommonViewHolder holder, int position, int layoutId, Object o) {
        if (o instanceof ItemHelper) {
            ItemHelper itemHelper = (ItemHelper) o;
            itemHelper.onBind(holder);
        }
    }
}
