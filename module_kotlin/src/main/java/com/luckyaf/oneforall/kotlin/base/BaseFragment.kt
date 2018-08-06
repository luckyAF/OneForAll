package com.luckyaf.oneforall.kotlin.base

import android.content.Context
import android.os.Bundle
import android.support.annotation.LayoutRes
import android.support.design.widget.Snackbar
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.luckyaf.oneforall.kotlin.utils.ToastUtil
import io.reactivex.disposables.Disposable

/**
 * 类描述：fragment基类
 * @author Created by luckyAF on 2018/7/18
 *
 */
@Suppress("unused")
abstract class BaseFragment<T : BasePresenter> : Fragment(), BaseView {

    lateinit var mPresenter : T


    lateinit var mContext: Context

    /**
     * 是否使用懒加载
     */
    protected var mLazyLoad = false

    /**
     * 首次显示
     */
    private var isFirstResumeVisible = true
    /**
     * 是否准备好
     */
    private var isPrepared = false

    lateinit var mRootView: View

    override fun onAttach(context: Context) {
        super.onAttach(context)
        this.mContext = context
    }

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        mRootView = inflater.inflate(getLayoutId(), container, false)
        initParams(arguments)
        mPresenter = getPresenter()
        return mRootView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        isPrepared = true
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        if (!mLazyLoad) {
            initData()
        }
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


    fun <T : View>findView(viewId: Int): T{
        return mRootView.findViewById(viewId)
    }


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


    override fun setUserVisibleHint(isVisibleToUser: Boolean) {
        super.setUserVisibleHint(isVisibleToUser)
        if (isVisibleToUser) {
            if (mLazyLoad && isFirstResumeVisible && isPrepared) {
                initData()
                isFirstResumeVisible = false
            }
            onVisibleToUser()
        } else {
            onInVisibleToUser()
        }
    }

    /**
     * 对用户可见
     */
    protected fun onVisibleToUser() {

    }


    /**
     * 对用户不可见
     */
    protected fun onInVisibleToUser() {

    }


    override fun onDestroy() {
        mPresenter.detachView()
        super.onDestroy()
    }


    override fun showMessage(message: Int) {
        //ToastUtil.showMessage(mContext, message)
        view?.let {
            Snackbar.make(it, message, Snackbar.LENGTH_SHORT).show()
        }

    }

    override fun showMessage(message: String) {
        //ToastUtil.showMessage(mContext, message)
        view?.let {
            Snackbar.make(it, message, Snackbar.LENGTH_SHORT).show()
        }
    }

    override fun showProgress() {
        //
    }

    override fun showProgress(message: String) {
    }

    override fun showProgress(message: Int) {
    }

    override fun hideProgress() {
        //
    }

}