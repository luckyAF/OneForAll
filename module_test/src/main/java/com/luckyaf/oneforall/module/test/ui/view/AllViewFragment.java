package com.luckyaf.oneforall.module.test.ui.view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;

import com.luckyaf.oneforall.module.test.R;
import com.luckyaf.oneforall.module.test.ui.home.HomeFragment;
import com.luckyaf.oneforall.module.test.ui.view.drag.DragBackLayoutFragment;
import com.luckyaf.smartandroid.lib.base.app.BaseFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * 类描述：所有
 *
 * @author Created by luckyAF on 2018/7/6
 */
public class AllViewFragment extends BaseFragment{

    private ViewPager vpView;
    private TabLayout tabView;

    //页卡标题集合
     private List<String> mTitleList ;
     ///页卡视图集合
     private List<Fragment> mViewList;

    public static AllViewFragment newInstance(){
        AllViewFragment fragment = new AllViewFragment();
        return fragment;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.test_fragment_all_view;
    }

    @Override
    protected void initParams(Bundle bundle) {

    }

    @Override
    protected void initView(View view, Bundle savedInstanceState) {
        tabView = findView(R.id.tab_view);
        vpView = findView(R.id.vp_view);
    }

    @Override
    protected void initData() {
        mTitleList = new ArrayList<>();
        mViewList = new ArrayList<>();
        mTitleList.add("flowLayout");
        mViewList.add(FlowLayoutFragment.newInstance());
        mTitleList.add("RecyclerView");
        mViewList.add(RecyclerViewFragment.newInstance());
        mTitleList.add("DragBackLayout");
        mViewList.add(DragBackLayoutFragment.newInstance());
        mTitleList.add("C");
        mViewList.add(HomeFragment.newInstance("C"));
        mTitleList.add("D");
        mViewList.add(HomeFragment.newInstance("D"));
        for (int i=0;i<mTitleList.size();i++){
            //添加tab选项
            tabView.addTab(tabView.newTab().setText(mTitleList.get(i)));
        }
        FragmentPagerAdapter mAdapter = new FragmentPagerAdapter(getChildFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                return mViewList.get(position);
            }

            @Override
            public int getCount() {
                return mViewList.size();
            }

            @Nullable
            @Override
            public CharSequence getPageTitle(int position) {
                return mTitleList.get(position);
            }
        };
        vpView.setAdapter(mAdapter);
        tabView.setupWithViewPager(vpView);
        tabView.setTabMode(TabLayout.MODE_SCROLLABLE);
    }
}
