package com.luckyaf.oneforall.module.test.ui.view.drag;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import com.luckyaf.oneforall.module.test.R;
import com.luckyaf.onforall.res.layout.DragBackLayout;
import com.luckyaf.onforall.res.utils.ViewUtil;
import com.luckyaf.smartandroid.lib.base.app.BaseActivity;

/**
 * 类描述：
 *
 * @author Created by luckyAF on 2018/7/23
 */
public class DragBackActivity extends BaseActivity implements DragBackLayout.Callback{


    /**
     * 上拉下拉关闭ViewGroup
     */
    private DragBackLayout dragBackLayout;


    /**
     * rootView background alpha
     */
    private ColorDrawable background;

    @Override
    public int getLayoutId() {
        return R.layout.test_activity_dragbacklayout;
    }

    @Override
    public void initParams(Bundle bundle) {

    }



    @Override
    public void initView() {
        background = new ColorDrawable(Color.BLACK);
        getWindow().setBackgroundDrawable(background);
        getWindow().getDecorView().setBackground(background);
        dragBackLayout = findViewById(R.id.dragBackLayout);
        dragBackLayout.setCallback(this);
    }

    @Override
    public void initData() {

    }

    @Override
    public void onDragStart() {

    }

    @Override
    public void onDrag(float progress) {
        ViewUtil.convertActivityToTranslucent(this);
        background.setAlpha((int) (0xff * (1f -  Math.abs(progress))));
    }

    @Override
    public void onDragCancel() {
    }

    @Override
    public void onDragComplete() {
        finish();
    }
}
