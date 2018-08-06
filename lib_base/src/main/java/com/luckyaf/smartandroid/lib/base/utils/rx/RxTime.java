package com.luckyaf.smartandroid.lib.base.utils.rx;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.functions.Function;

/**
 * 类描述：
 *
 * @author Created by luckyAF on 2018/7/2
 */
public class RxTime {
    /**
     * 倒计时
     * @param start  开始时间
     * @param count  计数数量
     * @return  Observable
     */
    public static Observable<Long> countDown(int start, final int count) {
        return Observable
                .interval((long) start, 1L, TimeUnit.SECONDS)
                .take(count + 1)
                .map(new Function<Long, Long>() {
                    @Override
                    public Long apply(Long aLong) throws Exception {
                        return (long) count - aLong;
                    }
                });
    }



}
