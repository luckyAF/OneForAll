package com.luckyaf.oneforall.kotlin.base

import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

/**
 * 类描述：RxPresenter
 * @author Created by luckyAF on 2018/7/18
 *
 */
open class RxPresenter: BasePresenter {

    override fun start() {
    }

    private var compositeDisposable: CompositeDisposable? = null

    override fun detachView() {
        //保证activity结束时取消所有正在执行的订阅
        compositeDisposable?.clear()

    }

    fun addSubscription(disposable: Disposable) {
        if (null == compositeDisposable) {
            compositeDisposable = CompositeDisposable()
        }
        compositeDisposable?.add(disposable)
    }

    fun removeSubscription(disposable: Disposable) {
        compositeDisposable?.remove(disposable)
    }
}