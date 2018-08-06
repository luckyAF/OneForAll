package com.luckyaf.oneforall.module.test.ui.view.drag;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.luckyaf.oneforall.module.test.R;
import com.luckyaf.oneforall.module.test.ui.view.FlowLayoutFragment;
import com.luckyaf.smartandroid.lib.base.app.BaseFragment;

/**
 * 类描述：
 *
 * @author Created by luckyAF on 2018/7/23
 */
public class DragBackLayoutFragment extends BaseFragment {

    public static DragBackLayoutFragment newInstance(){
        DragBackLayoutFragment fragment = new DragBackLayoutFragment();
        return fragment;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.test_fragment_dragbacklayout;
    }

    @Override
    protected void initParams(Bundle bundle) {

    }

    @Override
    protected void initView(View view, Bundle savedInstanceState) {
        findView(R.id.btn_drag_back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext,DragBackActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void initData() {

    }
}
