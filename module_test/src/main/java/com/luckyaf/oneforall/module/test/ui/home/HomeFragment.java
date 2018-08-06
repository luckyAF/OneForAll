package com.luckyaf.oneforall.module.test.ui.home;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.luckyaf.oneforall.module.test.R;
import com.luckyaf.smartandroid.lib.base.app.BaseFragment;

/**
 * 类描述：
 *
 * @author Created by luckyAF on 2018/7/6
 */
public class HomeFragment extends BaseFragment{
    public static final String INTENT_STRING_CONTENT = "intent_string_content";
    private String content;

    public static HomeFragment newInstance(String content){
        HomeFragment fragment = new HomeFragment();
        Bundle bundle = new Bundle();
        bundle.putString(INTENT_STRING_CONTENT,content);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.test_fragment_home;
    }

    @Override
    protected void initParams(Bundle bundle) {
        content = bundle.getString(INTENT_STRING_CONTENT);
    }

    @Override
    protected void initView(View view, Bundle savedInstanceState) {
        TextView textView = findView(R.id.txt_content);
        textView.setText(content);
    }

    @Override
    protected void initData() {

    }
}
