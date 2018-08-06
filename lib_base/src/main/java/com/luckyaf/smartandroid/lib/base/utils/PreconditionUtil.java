package com.luckyaf.smartandroid.lib.base.utils;

import android.os.Looper;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposables;

/**
 * 类描述：预处理
 *
 * @author Created by luckyAF on 2018/7/2
 */
public class PreconditionUtil {
    private PreconditionUtil() {
        throw new AssertionError("No instances.");
    }

    public static boolean checkMainThread(Observer<?> observer) {
        if (Looper.myLooper() != Looper.getMainLooper()) {
            observer.onSubscribe(Disposables.empty());
            observer.onError(new IllegalStateException(
                    "Expected to be called on the main thread but was " + Thread.currentThread().getName()));
            return false;
        }
        return true;
    }

    public static boolean checkMainThread() {
        return Looper.myLooper() == Looper.getMainLooper();
    }

}
