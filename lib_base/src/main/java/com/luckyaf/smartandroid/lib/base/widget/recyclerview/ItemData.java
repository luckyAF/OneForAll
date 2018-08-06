package com.luckyaf.smartandroid.lib.base.widget.recyclerview;

/**
 * 类描述：
 *
 * @author Created by luckyAF on 2018/7/16
 */
public class ItemData {
    /**
     * 默认是5 和RecyclerViewPool的默认值一样
     */
    private final int DEFAULT_HEADER_VIEW_CACHE_SIZE = 5;

    /**
     * layoutId / 顺便作为 viewTypeId
     */
    private int layoutId;

    /**
     * 该viewType对应的数据
     */
    private Object data;
    /**
     * 该种viewType的HeaderView 在RecyclerViewPool的缓存池内的缓存数量
     */
    private int cacheSize;

    public ItemData(int layoutId, Object data, int cacheSize) {

        this.layoutId = layoutId;
        this.data = data;
        this.cacheSize = cacheSize;
    }

    public ItemData(int layoutId, Object data) {
        this.data = data;
        this.layoutId = layoutId;
        this.cacheSize = DEFAULT_HEADER_VIEW_CACHE_SIZE;
    }

    public int getLayoutId() {
        return layoutId;
    }

    public void setLayoutId(int layoutId) {
        this.layoutId = layoutId;
    }



    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public int getCacheSize() {
        return cacheSize;
    }

    public void setCacheSize(int cacheSize) {
        this.cacheSize = cacheSize;
    }

}
