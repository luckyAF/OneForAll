package com.luckyaf.oneforall;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.launcher.ARouter;
import com.luckyaf.onforall.common.core.RouteNavigator;
import com.luckyaf.onforall.common.core.RouterHub;
import com.luckyaf.onforall.common.service.test.TestService;
import com.luckyaf.smartandroid.lib.base.app.BaseActivity;
import com.luckyaf.smartandroid.lib.base.utils.rx.RxView;

/**
 * 类描述：
 *
 * @author Created by luckyAF on 2018/7/9
 */
public class MainActivity extends BaseActivity{

    @Autowired(name = RouterHub.TEST_SERVICE_ABOUT_ME)
    TestService testService;



    Button btnTest;
    Button btnKotlin;

    @Override
    public int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    public void initParams(Bundle bundle) {
        ARouter.getInstance().inject(this);
    }

    @Override
    public void initView() {
        btnTest = findViewById(R.id.btn_test);
        btnKotlin = findViewById(R.id.btn_kotlin);

        btnTest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RouteNavigator.navigation(mContext,RouterHub.TEST_MAIN);
            }
        });
        btnKotlin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RouteNavigator.navigation(mContext,RouterHub.KOTLIN_MAIN);
            }
        });
    }

    @Override
    public void initData() {
        //inject 初始化@Autowired注解的字段
        if(null == testService){
            showMessage("testService" + "  not merged !");
        }
    }
}
