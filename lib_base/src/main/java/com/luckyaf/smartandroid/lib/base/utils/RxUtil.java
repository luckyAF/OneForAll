package com.luckyaf.smartandroid.lib.base.utils;

import android.view.View;
import android.widget.TextView;

import com.luckyaf.smartandroid.lib.base.utils.rx.RxCallBack;
import com.luckyaf.smartandroid.lib.base.utils.rx.RxTime;
import com.luckyaf.smartandroid.lib.base.utils.rx.RxView;

import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

/**
 * 类描述：RxUtil
 *
 * @author Created by luckyAF on 2018/6/29
 */
@SuppressWarnings("unused")
public class RxUtil {
    private RxUtil() {
    }

    /**
     * 在点击时限内点击了n次后触发
     * @param view   view
     * @param clickCount  点击次数
     * @param timeLimit   点击时限
     * @param clickListener  点击监听
     * @return Disposable
     */
    public static Disposable multiClick(View view,final int clickCount, int timeLimit,final RxCallBack.ClickListener clickListener){
        Observable<Object> mClickStream = RxView.click(view).share();
        return mClickStream.doOnSubscribe(new Consumer<Disposable>() {
            @Override
            public void accept(Disposable disposable) throws Exception{

            }
        }).buffer(mClickStream.debounce((long)timeLimit, TimeUnit.MILLISECONDS))
                .map(new Function<List<Object>, Integer>() {
                    @Override
                    public Integer apply(List<Object> objects) throws Exception {
                        return objects.size();
                    }
                }).subscribe(new Consumer<Integer>() {
            @Override
            public void accept(Integer integer) throws Exception {
                clickListener.doSomeThing();
            }
        });



    }

    /**
     * 单次点击
     * @param view view
     * @param clickListener 点击监听
     * @return Disposable
     */
    public static Disposable singleClick(View view, final RxCallBack.ClickListener clickListener) {
        return RxView.singleClick(view)
                .subscribe(new Consumer<Object>() {
                               @Override
                               public void accept(Object o) throws Exception {
                                   clickListener.doSomeThing();
                               }
                           }
                );
    }

    /**
     * 倒计时
     * @param start  开始时间
     * @param count  倒计时时间
     * @param callBack 回调
     */
    public static void countDown(int start, int count, final RxCallBack.CommonCallBack<Long> callBack) {
        RxTime.countDown(start, count)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Long>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        callBack.onStart(d);
                    }

                    @Override
                    public void onNext(Long aLong) {
                        callBack.onNext(aLong);
                    }

                    @Override
                    public void onError(Throwable e) {
                        callBack.onError(e);
                    }

                    @Override
                    public void onComplete() {
                        callBack.onComplete();
                    }
                });
    }

    /**
     * 字符更改
     * @param textView textView
     * @param callBack 回调
     * @return Disposable
     */
    public static Disposable textChange(TextView textView,final RxCallBack.SimpleCallBack<CharSequence> callBack){
        return RxView.textChange(textView)
                .subscribe(new Consumer<CharSequence>() {
                    @Override
                    public void accept(CharSequence charSequence) throws Exception {
                        callBack.dealWith(charSequence);
                    }
                });
    }


}
