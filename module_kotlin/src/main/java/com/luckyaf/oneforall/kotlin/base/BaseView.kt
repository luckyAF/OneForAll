package com.luckyaf.oneforall.kotlin.base

/**
 * 类描述：view 基类
 * @author Created by luckyAF on 2018/7/18
 *
 */

@Suppress("unused")
interface BaseView {

    /**
     * toast
     */
    fun showMessage(message: String)
    fun showMessage(message: Int)

    fun showProgress(message: String)
    fun showProgress(message: Int)
    fun showProgress()

    fun hideProgress()

}