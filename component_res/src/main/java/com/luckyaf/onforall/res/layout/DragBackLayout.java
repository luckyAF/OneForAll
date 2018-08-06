package com.luckyaf.onforall.res.layout;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewCompat;
import android.support.v4.widget.ViewDragHelper;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;

import com.luckyaf.onforall.res.R;

/**
 * 类描述：
 *
 * @author Created by luckyAF on 2018/7/23
 */
public class DragBackLayout extends FrameLayout {


    private final ViewDragHelper viewDragHelper;
    private final int dragDismissDistance;


    @Nullable
    private DragBackLayout.Callback callback;


    public DragBackLayout(Context context) {
        this(context, (AttributeSet) null);
    }

    public DragBackLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public DragBackLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.viewDragHelper = ViewDragHelper.create(this, 0.125F, new DragBackLayout.ViewDragCallback());
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.DragBackLayout);
        this.dragDismissDistance = (int) typedArray.getDimension(R.styleable.DragBackLayout_dragDismissDistance, 200);
        typedArray.recycle();
    }

    public void setCallback(@Nullable DragBackLayout.Callback callback) {
        this.callback = callback;
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        try {
            return this.viewDragHelper.shouldInterceptTouchEvent(ev);
        } catch (Exception e) {
            return false;
        }
    }

    @Override

    public boolean onTouchEvent(@NonNull MotionEvent event) {
        try {
            this.viewDragHelper.processTouchEvent(event);
        } catch (Exception e) {
        }
        return true;
    }

    @Override
    public void computeScroll() {
        if (this.viewDragHelper.continueSettling(true)) {
            ViewCompat.postInvalidateOnAnimation(this);
        }

    }


    private class ViewDragCallback extends android.support.v4.widget.ViewDragHelper.Callback {
        private ViewDragCallback() {
        }

        @Override
        public boolean tryCaptureView(View child, int pointerId) {
            return true;
        }


        @Override
        public int clampViewPositionHorizontal(View child, int left, int dx) {
            if (left > dragDismissDistance / 2) {
                return dragDismissDistance / 2;
            } else if (left < -dragDismissDistance / 2) {
                return -dragDismissDistance / 2;
            } else {
                return left;
            }
        }

        @Override
        public int clampViewPositionVertical(View child, int top, int dy) {
            if (top > dragDismissDistance) {
                return dragDismissDistance;
            } else if (top < -dragDismissDistance) {
                return -dragDismissDistance;
            } else {
                return top;
            }
        }

        @Override
        public int getViewHorizontalDragRange(View child) {
            return 0;
        }

        @Override
        public int getViewVerticalDragRange(View child) {
            return DragBackLayout.this.getHeight();
        }

        @Override
        public void onViewCaptured(View capturedChild, int activePointerId) {
            if (DragBackLayout.this.callback != null) {
                DragBackLayout.this.callback.onDragStart();
            }

        }

        @Override
        public void onViewPositionChanged(View changedView, int left, int top, int dx, int dy) {
            final float scale = 1 - Math.abs( (float) top / (float) DragBackLayout.this.getHeight());
            getChildAt(0).setScaleX(scale);
            getChildAt(0).setScaleY(scale);
            if (DragBackLayout.this.callback != null) {
                DragBackLayout.this.callback.onDrag((float)   top / (float) dragDismissDistance);
            }

        }

        @Override
        public void onViewReleased(View releasedChild, float xvel, float yvel) {
            int slop = dragDismissDistance;
            if (Math.abs(releasedChild.getTop()) >= slop) {
                if (DragBackLayout.this.callback != null) {
                    DragBackLayout.this.callback.onDragComplete();
                }
            } else {
                if (DragBackLayout.this.callback != null) {
                    DragBackLayout.this.callback.onDragCancel();
                }

                DragBackLayout.this.viewDragHelper.settleCapturedViewAt(0, 0);
                DragBackLayout.this.invalidate();
            }

        }
    }


    public interface Callback {
        void onDragStart();

        void onDrag(float var1);

        void onDragCancel();

        void onDragComplete();
    }


}
