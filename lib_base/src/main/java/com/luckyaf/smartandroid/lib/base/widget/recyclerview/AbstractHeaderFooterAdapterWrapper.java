package com.luckyaf.smartandroid.lib.base.widget.recyclerview;

import android.support.annotation.NonNull;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.ViewGroup;

import java.util.ArrayList;

/**
 * 类描述：
 *
 * @author Created by luckyAF on 2018/7/16
 */
@SuppressWarnings("unused")
public abstract class AbstractHeaderFooterAdapterWrapper extends RecyclerView.Adapter<RecyclerView.ViewHolder>{


    protected ArrayList<ItemData> mHeaders = new ArrayList<>();
    protected ArrayList<ItemData> mFooters = new ArrayList<>();
    /**
     * 内部的的普通Adapter
     */
    protected RecyclerView.Adapter mInnerAdapter;

    public AbstractHeaderFooterAdapterWrapper(RecyclerView.Adapter mInnerAdapter) {
        this.mInnerAdapter = mInnerAdapter;
    }

    public int getHeaderViewCount() {
        return mHeaders.size();
    }

    public int getFooterViewCount() {
        return mFooters.size();
    }


    protected int getInnerItemCount() {
        return mInnerAdapter != null ? mInnerAdapter.getItemCount() : 0;
    }

    /**
     * 传入position 判断是否是header
     *
     * @param position position
     * @return boolean
     */
    public boolean isHeaderView(int position) {
        // 举例， 2 个头，pos 0 1，true， 2+ false
        return getHeaderViewCount() > position;
    }

    /**
     * 传入position判断是否是footer
     *
     * @param position position
     * @return  boolean
     */
    public boolean isFooterView(int position) {
        //举例， 2个头，2个inner，pos 0 1 2 3 ,false,4+true
        return position >= getHeaderViewCount() + getInnerItemCount();
    }

    /**
     * 添加HeaderView
     *
     * @param layoutId headerView 的LayoutId
     * @param data     headerView 的data(可能多种不同类型的header 只能用Object了)
     */
    protected void addHeaderView(int layoutId, Object data) {
        mHeaders.add(new ItemData(layoutId, data));
    }
    /**
     * 添加HeaderView
     *
     * @param layoutId  headerView 的LayoutId
     * @param data      headerView 的data(可能多种不同类型的header 只能用Object了)
     * @param cacheSize 该种headerView在缓存池中的缓存个数
     */
    protected void addHeaderView(int layoutId, Object data, int cacheSize) {
        mHeaders.add(new ItemData(layoutId, data, cacheSize));
    }

    /**
     * 设置某个位置的HeaderView
     *
     * @param headerPos 从0开始，如果pos过大 就是addHeader
     * @param layoutId layoutId
     * @param data data
     */
    protected void setHeaderView(int headerPos, int layoutId, Object data) {
        if (mHeaders.size() > headerPos) {
            mHeaders.get(headerPos).setLayoutId(layoutId);
            mHeaders.get(headerPos).setData(data);
        } else if (mHeaders.size() == headerPos) {
            //调用addHeaderView
            addHeaderView(layoutId, data);
        } else {
            //
            addHeaderView(layoutId, data);
        }
    }

    /**
     * 设置某个位置的HeaderView
     *
     * @param headerPos 从0开始，如果pos过大 就是addHeader
     * @param layoutId layoutId
     * @param data data
     * @param cacheSize 该种headerView在缓存池中的缓存个数
     */
    protected void setHeaderView(int headerPos, int layoutId, Object data, int cacheSize) {
        if (mHeaders.size() > headerPos) {
            mHeaders.get(headerPos).setLayoutId(layoutId);
            mHeaders.get(headerPos).setData(data);
            mHeaders.get(headerPos).setCacheSize(cacheSize);
        } else if (mHeaders.size() == headerPos) {
            //调用addHeaderView
            addHeaderView(layoutId, data, cacheSize);
        } else {
            addHeaderView(layoutId, data, cacheSize);
        }
    }

    /**
     * 添加FooterView
     *
     * @param data data
     */
    protected void addFooterView(ItemData data) {
        mFooters.add(data);
    }

    protected void setFooterView(ItemData data) {
        clearFooterView();
        addFooterView(data);
    }

    /**
     * 清空HeaderView数据
     */
    public void clearHeaderView() {
        mHeaders.clear();
    }

    public void clearFooterView() {
        mFooters.clear();
    }


    @Override
    public int getItemViewType(int position) {
        if (isHeaderView(position)) {
            return  mHeaders.get(position).getLayoutId();
        } else if (isFooterView(position)) {
            //举例：header 2， inner 2， 0123都不是，4才是，4-2-2 = 0，ok。
            return mFooters.get(position - getHeaderViewCount() - getInnerItemCount()).getLayoutId();
        }
        return mInnerAdapter.getItemViewType(position - getHeaderViewCount());
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (hasHeaderView(viewType)){
            return CommonViewHolder.get(parent, viewType);
        }
        if (isFooterView(viewType)) {
            return CommonViewHolder.get(parent, viewType);
        }
        return mInnerAdapter.onCreateViewHolder(parent, viewType);
    }

    /**
     * 绑定header
     * @param holder holder
     * @param position position
     * @param layoutId layoutId 多回传一个layoutId出去，用于判断是第几个header

     * @param o data
     */
    protected abstract void onBindItemHolder(CommonViewHolder holder, int position, int layoutId, Object o);

    @Override
    @SuppressWarnings("unchecked")
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder , int position) {
        if (isHeaderView(position)) {
            onBindItemHolder((CommonViewHolder) holder, position, mHeaders.get(position).getLayoutId(),mHeaders.get(position).getData());
            return;
        } else if (isFooterView(position)) {
            int footerPosition = position - getInnerItemCount() - getHeaderViewCount();
            onBindItemHolder((CommonViewHolder) holder, position, mFooters.get(footerPosition).getLayoutId(), mFooters.get(footerPosition).getData());
            return;
        }
        //举例子，2个header，0 1是头，2是开始，2-2 = 0
        mInnerAdapter.onBindViewHolder(holder, position - getHeaderViewCount());
    }

    @Override
    public int getItemCount() {
        return getInnerItemCount() + getHeaderViewCount() + getFooterViewCount();
    }

    @Override
    public void onAttachedToRecyclerView(@NonNull RecyclerView recyclerView) {
        mInnerAdapter.onAttachedToRecyclerView(recyclerView);
        //设置HeaderView的ViewHolder的缓存数量
        if (null != mHeaders && !mHeaders.isEmpty()) {
            for (ItemData headerData : mHeaders) {
                recyclerView.getRecycledViewPool().setMaxRecycledViews(headerData.getLayoutId(), headerData.getCacheSize());
            }
        }
        //为了兼容GridLayout
        RecyclerView.LayoutManager layoutManager = recyclerView.getLayoutManager();
        if (layoutManager instanceof GridLayoutManager) {
            final GridLayoutManager gridLayoutManager = (GridLayoutManager) layoutManager;
            final GridLayoutManager.SpanSizeLookup spanSizeLookup = gridLayoutManager.getSpanSizeLookup();
            gridLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
                @Override
                public int getSpanSize(int position) {
                    int viewType = getItemViewType(position);
                    if (isHeaderView(position)) {
                        return gridLayoutManager.getSpanCount();
                    } else if (isFooterView(viewType)) {
                        return gridLayoutManager.getSpanCount();
                    }
                    if (spanSizeLookup != null) {
                        return spanSizeLookup.getSpanSize(position);
                    }
                    return 1;
                }
            });
            gridLayoutManager.setSpanCount(gridLayoutManager.getSpanCount());
        }

    }

    @Override
    @SuppressWarnings("unchecked")
    public void onViewAttachedToWindow(@NonNull RecyclerView.ViewHolder holder) {
        mInnerAdapter.onViewAttachedToWindow(holder);
        int position = holder.getLayoutPosition();
        if (isHeaderView(position) || isFooterView(position)) {
            ViewGroup.LayoutParams lp = holder.itemView.getLayoutParams();

            if (lp != null
                    && lp instanceof StaggeredGridLayoutManager.LayoutParams) {

                StaggeredGridLayoutManager.LayoutParams p =
                        (StaggeredGridLayoutManager.LayoutParams) lp;

                p.setFullSpan(true);
            }
        }
    }


    private boolean hasFooterView(int viewType) {
        if (null == mFooters ||mFooters.isEmpty()) {
            return false;
        }
        for (ItemData footerData : mFooters) {
            if (footerData.getLayoutId() == viewType) {
                return true;
            }
        }
        return false;
    }

    private boolean hasHeaderView(int viewType) {
        if (null == mHeaders || mHeaders.isEmpty()) {
            return false;
        }
        for (ItemData headerData : mHeaders) {
            if (headerData.getLayoutId() == viewType) {
                return true;
            }
        }
        return false;
    }

}
