package com.luckyaf.smartandroid.lib.base.widget.viewpager;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * 类描述：不可滑动viewPager
 *
 * @author Created by luckyAF on 2018/7/6
 */
public class NoScrollViewPager extends ViewPager {
    private boolean isCanScroll = false;

    public NoScrollViewPager(Context context) {
        super(context);
    }

    public NoScrollViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public void setScanScroll(boolean isCanScroll) {
        this.isCanScroll = isCanScroll;
    }

    @Override
    public void scrollTo(int x, int y) {
        super.scrollTo(x, y);
    }

    @Override
    public void setCurrentItem(int item, boolean smoothScroll) {
        super.setCurrentItem(item, smoothScroll);
    }

    @Override
    public void setCurrentItem(int item) {
        //false 去除滚动效果
        super.setCurrentItem(item,false);
    }

    @Override
    public boolean performClick() {
        return super.performClick();
    }

    @Override
    public boolean onTouchEvent(MotionEvent arg0) {
        if (arg0.getAction() == MotionEvent.ACTION_DOWN) {
            // 如果点击
            performClick();
        }
        return isCanScroll && super.onTouchEvent(arg0);

    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent arg0) {
        return isCanScroll && super.onInterceptTouchEvent(arg0);

    }
}