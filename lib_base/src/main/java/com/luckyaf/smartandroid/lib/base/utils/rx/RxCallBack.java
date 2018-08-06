package com.luckyaf.smartandroid.lib.base.utils.rx;

import io.reactivex.disposables.Disposable;

/**
 * 类描述：
 *
 * @author Created by luckyAF on 2018/7/2
 */
public class RxCallBack {
    public interface ClickListener{
        /**
         * 做些事
         */
        void doSomeThing();
    }

    public interface CommonCallBack<T>{
        /**
         * 开始
         * @param disposable disposable
         */
        void onStart(Disposable disposable);

        /**
         * 进行中
         * @param t 数据
         */
        void onNext(T t);

        /**
         * 错误
         * @param throwable throwable
         */
        void onError(Throwable throwable);

        /**
         * 完成
         */
        void onComplete();
    }


    public interface  SimpleCallBack<T>{
        /**
         * 处理新数据
         * @param t t
         */
        void dealWith(T t);
    }


}
