package com.luckyaf.oneforall.kotlin.base

import android.content.Context
import android.os.Bundle
import android.support.annotation.LayoutRes
import android.support.v7.app.AppCompatActivity
import android.view.ViewGroup
import com.luckyaf.oneforall.kotlin.R
import com.luckyaf.oneforall.kotlin.utils.StatusBarUtil
import com.luckyaf.oneforall.kotlin.utils.ToastUtil
import io.reactivex.disposables.Disposable

/**
 * 类描述： 带presenter的activity
 * @author Created by luckyAF on 2018/7/18
 *
 */

@Suppress("unused")
abstract class BaseActivity <T : BasePresenter> : AppCompatActivity(), BaseView{
    lateinit var mPresenter: T

    lateinit var mContext: Context


    override fun onCreate(savedInstanceState : Bundle?){
        super.onCreate(savedInstanceState)
        doBeforeSetContentView()
        setContentView(getLayoutId())
        mContext = this
        mPresenter = getPresenter()
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
     * 获取presenter
     */
    abstract fun getPresenter(): T



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




    fun addSubscription(disposable: Disposable?) {
        if (null != disposable) {
            if (mPresenter is RxPresenter) {
                (mPresenter as RxPresenter).addSubscription(disposable)
            }
        }
    }

    fun removeSubscription(disposable: Disposable?) {
        if (null != disposable) {
            if (mPresenter is RxPresenter) {
                (mPresenter as RxPresenter).removeSubscription(disposable)
            }
        }
    }


    override fun onDestroy() {
        mPresenter.detachView()
        super.onDestroy()
    }

    fun doBeforeSetContentView(){
        StatusBarUtil.setTranslucentView(window.decorView as ViewGroup, R.color.kotlin_colorAccent,1.0f)
    }




    override fun showMessage(message: Int) {
        ToastUtil.showMessage(mContext, message)
    }

    override fun showMessage(message: String) {
        ToastUtil.showMessage(mContext, message)
    }

    override fun showProgress() {
    }

    override fun hideProgress() {
    }
}