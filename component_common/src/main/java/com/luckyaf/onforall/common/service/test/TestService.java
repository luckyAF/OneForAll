package com.luckyaf.onforall.common.service.test;

import com.alibaba.android.arouter.facade.template.IProvider;

/**
 * 类描述： TestService
 * 向外提供服务的接口, 在此接口中声明一些具有特定功能的方法提供给外部, 即可让一个组件与其他组件或宿主进行交互
 * module_test 实现该接口
 * 其他模块可以使用该接口
 *
 * @author Created by luckyAF on 2018/7/2
 */
public interface TestService extends IProvider {
    /**
     * 获取消息
     * @return   TestExampleMessage
     */
    TestExampleMessage getMessage();
}
