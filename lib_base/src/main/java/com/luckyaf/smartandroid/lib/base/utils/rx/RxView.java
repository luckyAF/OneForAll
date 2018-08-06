package com.luckyaf.smartandroid.lib.base.utils.rx;

import android.view.View;
import android.widget.TextView;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;

/**
 * 类描述：
 *
 * @author Created by luckyAF on 2018/7/2
 */
public class RxView {


    public static Observable<Object> click(View view){
        return new ViewClickObservable(view);
    }

    public static Observable<Object> singleClick(View view){
        return click(view).throttleFirst(1, TimeUnit.SECONDS);
    }

    public static Observable<CharSequence> textChange(TextView textView){
        return new TextViewTextObservable(textView);
    }
}
