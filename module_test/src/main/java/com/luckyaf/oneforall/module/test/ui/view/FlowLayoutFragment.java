package com.luckyaf.oneforall.module.test.ui.view;

import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.TextView;

import com.luckyaf.oneforall.module.test.R;
import com.luckyaf.oneforall.module.test.widget.flowlayout.FlowLayout;
import com.luckyaf.oneforall.module.test.widget.flowlayout.TagAdapter;
import com.luckyaf.oneforall.module.test.widget.flowlayout.TagFlowLayout;
import com.luckyaf.smartandroid.lib.base.app.BaseFragment;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 类描述：
 *
 * @author Created by luckyAF on 2018/7/6
 */
public class FlowLayoutFragment extends BaseFragment{

    TagFlowLayout tagFlowLayout;
    List<String> stringList = new ArrayList<>();

    public static FlowLayoutFragment newInstance(){
        FlowLayoutFragment fragment = new FlowLayoutFragment();
        return fragment;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.test_fragment_flowlayout;
    }

    @Override
    protected void initParams(Bundle bundle) {

    }

    @Override
    protected void initView(View view, Bundle savedInstanceState) {
        tagFlowLayout = findView(R.id.tag_view);
        String[] tags = {"A","BBBB","CC","DDDDDDDD","EE","FF","GGGGGGG","HHHHhH"};
        stringList.clear();
        stringList.addAll(Arrays.asList(tags));
        tagFlowLayout.setAdapter(new TagAdapter<String>(stringList) {
            @Override
            public View getView(FlowLayout parent, int position, String o) {
                TextView textView = new TextView(mContext);
                textView.setText("TAG_" + o);
                textView.setBackgroundColor(ContextCompat.getColor(mContext,R.color.test_colorAccent));
                return textView;
            }
        });

        tagFlowLayout.setOnTagClickListener(new TagFlowLayout.OnTagClickListener() {
            @Override
            public boolean onTagClick(View view, int position, FlowLayout parent) {
                showMessage("click" + stringList.get(position));
                return false;
            }
        });

    }

    @Override
    protected void initData() {

    }
}
