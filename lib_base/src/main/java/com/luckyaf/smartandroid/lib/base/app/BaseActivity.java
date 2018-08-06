package com.luckyaf.smartandroid.lib.base.app;

import android.content.Context;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.annotation.StringRes;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;

import com.luckyaf.smartandroid.lib.base.component.RxBus;
import com.luckyaf.smartandroid.lib.base.utils.ToastUtil;

/**
 * 类描述：基类activity
 *
 * @author Created by luckyAF on 2018/7/6
 */
public abstract  class BaseActivity extends AppCompatActivity {
    public static String TAG = BaseActivity.class.getName();
    public Context mContext;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        doBeforeSetContentView();
        setContentView(getLayoutId());
        mContext = this;
        Bundle bundle = getIntent().getExtras();
        initParams(bundle);
        initView();
        initData();

    }
    public void showMessage(String message) {
        ToastUtil.showMessage(mContext,message);
    }

    public void showMessage(@StringRes int message) {
        ToastUtil.showMessage(mContext,message);
    }




//    private void initTipView() {
//        LayoutInflater inflater = getLayoutInflater();
//        mTipView = inflater.inflate(R.layout.layout_network_tip, null); //提示View布局
//        mWindowManager = (WindowManager) this.getSystemService(Context.WINDOW_SERVICE);
//        mLayoutParams = new WindowManager.LayoutParams(
//                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT,
//                WindowManager.LayoutParams.TYPE_APPLICATION,
//                WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE
//                        | WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
//                PixelFormat.TRANSLUCENT);
//        //使用非CENTER时，可以通过设置XY的值来改变View的位置
//        mLayoutParams.gravity = Gravity.TOP;
//        mLayoutParams.x = 0;
//        mLayoutParams.y = 0;
//    }



    //*********************子类实现*****************************//

    /**
     * 获取布局文件
     *
     * @return 布局文件id
     */
    public abstract int getLayoutId();

    /**
     * 初始化参数
     *
     * @param bundle bundle
     */
    public abstract void initParams(Bundle bundle);

    /**
     * 初始化view
     */
    public abstract void initView();

    /**
     * 初始化数据
     */
    public abstract void initData();




    protected void doBeforeSetContentView() {
        // 无标题
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        // 设置竖屏
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        // 默认着色状态

        // 默认着色状态栏
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
//            StatusBarUtil.setLightStatusBar(this, ContextCompat.getColor(this, R.color.common_color_white));
//        } else {
//            StatusBarUtil.setColor(this, ContextCompat.getColor(this, R.color.common_status_bar_gray), 0);
//        }
    }




}
