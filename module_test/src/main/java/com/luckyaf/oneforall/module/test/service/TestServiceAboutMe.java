package com.luckyaf.oneforall.module.test.service;

import android.content.Context;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.luckyaf.onforall.common.core.RouterHub;
import com.luckyaf.onforall.common.service.test.TestExampleMessage;
import com.luckyaf.onforall.common.service.test.TestService;

/**
 * 类描述：提供服务测试
 *
 * @author Created by luckyAF on 2018/7/3
 */
@Route(path = RouterHub.TEST_SERVICE_ABOUT_ME, name = "提供测试信息")
public class TestServiceAboutMe implements TestService{

    private Context mContext;

    @Override
    public TestExampleMessage getMessage() {
        TestExampleMessage message = new TestExampleMessage();
        message.setMessage("此模块主要用于测试某些自定义或第三方组件");
        return message;
    }

    @Override
    public void init(Context context) {
        mContext = context;
    }
}
