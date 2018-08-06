package com.luckyaf.oneforall.module.test;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;

import android.support.v4.view.ViewPager;
import android.view.MenuItem;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.luckyaf.oneforall.module.test.ui.home.HomeFragment;
import com.luckyaf.oneforall.module.test.ui.view.AllViewFragment;
import com.luckyaf.oneforall.module.test.utils.helper.BottomNavigationViewHelper;
import com.luckyaf.onforall.common.core.RouterHub;
import com.luckyaf.smartandroid.lib.base.app.BaseActivity;
import com.luckyaf.smartandroid.lib.base.widget.viewpager.NoScrollViewPager;

import java.util.ArrayList;
import java.util.List;

/**
 * @author xiangzhongfei
 */
@Route(path = RouterHub.TEST_MAIN)
public class MainActivity extends BaseActivity {

    List<Fragment> fragments = new ArrayList<>();
    private BottomNavigationView bottomNavigationView;
    private NoScrollViewPager mViewPager;


    @Override
    public int getLayoutId() {
        return R.layout.test_activity_main;
    }
    @Override
    public void initParams(Bundle bundle) {

    }

    @Override
    public void initView() {
        bottomNavigationView = findViewById(R.id.bv_main);
        mViewPager = findViewById(R.id.vp_main);
        //
        BottomNavigationViewHelper.disableShiftMode(bottomNavigationView);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int itemId = item.getItemId();
                if (itemId == R.id.item_tab_home) {
                    mViewPager.setCurrentItem(0);
                    return true;
                }else if(itemId == R.id.item_tab_view) {
                    mViewPager.setCurrentItem(1);
                    return true;
                }else if(itemId ==  R.id.item_tab_util){

                    mViewPager.setCurrentItem(2);
                    return true;
                }else if(itemId == R.id.item_tab_anim){
                    mViewPager.setCurrentItem(3);
                    return true;
                }else{
                    return false;
                }
            }
        });
        setupViewPager(mViewPager);
    }

    @Override
    public void initData() {

    }

    private void setupViewPager(ViewPager viewPager) {

        fragments.add(HomeFragment.newInstance("home"));
        fragments.add(AllViewFragment.newInstance());
        fragments.add(HomeFragment.newInstance("util"));
        fragments.add(HomeFragment.newInstance("anim"));

        viewPager.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public int getCount() {
                return fragments.size();
            }

            @Override
            public Fragment getItem(int position) {
                return fragments.get(position);
            }
        });

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }
            @Override
            public void onPageSelected(int position) {
                bottomNavigationView.getMenu().getItem(position).setChecked(true);
            }
            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });

    }
}
