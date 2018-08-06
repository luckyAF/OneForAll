package com.luckyaf.oneforall.kotlin.base


/**
 * 类描述：presenter 基类
 * @author Created by luckyAF on 2018/7/18
 *
 */
interface BasePresenter {

    fun start()

    /**
     * 分离view
     */
    fun detachView()
}