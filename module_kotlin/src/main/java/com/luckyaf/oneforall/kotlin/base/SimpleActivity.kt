package com.luckyaf.oneforall.kotlin.base

import android.os.Bundle
import android.support.annotation.LayoutRes
import android.support.v7.app.AppCompatActivity
import android.view.ViewGroup
import com.luckyaf.oneforall.kotlin.R
import com.luckyaf.oneforall.kotlin.utils.StatusBarUtil
import com.luckyaf.oneforall.kotlin.utils.ToastUtil
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

/**
 * 类描述：
 * @author Created by luckyAF on 2018/7/18
 *
 */

@Suppress("unused")
abstract class SimpleActivity: AppCompatActivity() {
    private var compositeDisposable: CompositeDisposable? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        doBeforeSetContentView()
        setContentView(getLayoutId())
        val bundle = intent.extras
        initParams(bundle)
        initView()
        initData()
    }

    /**
     * 加载布局
     */
    @LayoutRes
    abstract fun getLayoutId(): Int


    /**
     * 初始化参数
     */
    abstract fun initParams(bundle: Bundle?)

    /**
     * 初始化 ViewI
     */
    abstract fun initView()

    /**
     * 初始化数据
     */
    abstract fun initData()


    override fun onDestroy() {
        compositeDisposable?.clear()
        super.onDestroy()
    }

    fun doBeforeSetContentView() {
        StatusBarUtil.setTranslucentView(window.decorView as ViewGroup, R.color.kotlin_colorAccent, 1.0f)
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

    fun showMessage(message: Int) {
        ToastUtil.showMessage(baseContext , message)
    }

    fun showMessage(message: String) {
        ToastUtil.showMessage(baseContext, message)
    }

    fun showProgress() {
    }

    fun hideProgress() {
    }
}