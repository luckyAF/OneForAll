package com.luckyaf.smartandroid.lib.base.app;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import com.luckyaf.smartandroid.lib.base.utils.ToastUtil;

/**
 * 类描述：
 *
 * @author Created by luckyAF on 2018/7/6
 */
public abstract  class BaseFragment extends Fragment {

    private static String TAG = BaseFragment.class.getName();
    protected Context mContext;
    protected Activity mActivity;

    /**
     * 根view
     */
    protected View rootView;


    /**
     * 是否使用懒加载
     */
    protected boolean mLazyLoad = false;


    /**
     * 首次显示
     */
    private boolean isFirstResumeVisible = true;
    /**
     * 首次显示
     */
    private boolean isPrepared = false;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.mContext = context;
        this.mActivity = (Activity) context;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container
            , Bundle savedInstanceState) {
        if (null == rootView) {
            rootView = inflater.inflate(getLayoutId(), container, false);
        }
        initParams(getArguments());
        rootView.setClickable(true);
        return rootView;
    }

    @SuppressWarnings("unchecked")
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        initView(view, savedInstanceState);
        isPrepared = true;
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (!mLazyLoad) {
            initData();
        }
    }





    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    /**
     * 该抽象方法就是 onCreateView中需要的layoutID
     *
     * @return fragment layout id
     */
    protected abstract @LayoutRes
    int getLayoutId();

    /**
     * 初始化参数
     *
     * @param bundle 参数
     */
    protected abstract void initParams(Bundle bundle);

    /**
     * 该抽象方法就是 初始化view
     *
     * @param view               初始化view
     * @param savedInstanceState activity传来的数据
     */
    protected abstract void initView(View view, Bundle savedInstanceState);

    /**
     * 数据初始化
     */
    protected abstract void initData();



    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
            if (mLazyLoad && isFirstResumeVisible && isPrepared) {
                initData();
                isFirstResumeVisible = false;
            }
            onVisibleToUser();
        } else {
            onInVisibleToUser();
        }
    }

    /**
     * 对用户可见
     */
    protected void onVisibleToUser() {

    }


    /**
     * 对用户不可见
     */
    protected void onInVisibleToUser() {

    }


    @SuppressWarnings("unchecked")
    protected <V extends View> V findView(@IdRes int resId) {
        return (V) rootView.findViewById(resId);
    }

    public void showMessage(String message) {
        ToastUtil.showMessage(mContext,message);
    }

    public void showMessage(@StringRes int message) {
        ToastUtil.showMessage(mContext,message);
    }


}
