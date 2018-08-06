package com.luckyaf.smartandroid.lib.base.component;

import java.util.concurrent.ConcurrentHashMap;

import io.reactivex.BackpressureStrategy;
import io.reactivex.Flowable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.subjects.PublishSubject;
import io.reactivex.subjects.Subject;

/**
 * 类描述：rxBus
 *
 * @author Created by luckyAF on 2018/6/29
 */
@SuppressWarnings("unused")
public class RxBus {


    private final Subject<Object> mSubject;
    private ConcurrentHashMap<Object, CompositeDisposable> mSubscriptionMap;

    private RxBus (){
        // toSerialized method made bus thread safe
        this.mSubject = PublishSubject.create().toSerialized();
    }

    /**
     *  发送事件
     * @param o 事件
     */
    public void post(Object o) {
        this.mSubject.onNext(o);
    }
    /**
     * 返回指定类型的带背压的Flowable实例
     *
     * @param <T>  t
     * @param type type
     * @return Flowable
     */
    public <T>Flowable<T> getObservable(Class<T> type) {
        return mSubject.toFlowable(BackpressureStrategy.BUFFER)
                .ofType(type);
    }

    /**
     * 一个默认的订阅方法
     *
     * @param <T>  T
     * @param type  Type
     * @param next  next
     * @param error error
     * @return Disposable
     */
    public <T> Disposable doSubscribe(Class<T> type, Consumer<T> next, Consumer<Throwable> error){
        return getObservable(type)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(next,error);
    }

    /**
     * 一个默认的订阅方法
     * @param tag  Tag
     * @param <T>  T
     * @param type  Type
     * @param next  next
     * @param error error
     * @return Disposable
     */
    public <T> Disposable doSubscribe(Object tag,Class<T> type, Consumer<T> next, Consumer<Throwable> error){
        Disposable disposable = doSubscribe(type,next,error);
        addSubscription(tag,disposable);
        return disposable;
    }

    /**
     * 保存订阅后的disposable
     * @param tag tag
     * @param disposable disposable
     */
    public void addSubscription(Object tag, Disposable disposable) {
        if (mSubscriptionMap == null) {
            mSubscriptionMap = new ConcurrentHashMap<>(10);
        }
        if (mSubscriptionMap.get(tag) != null) {
            mSubscriptionMap.get(tag).add(disposable);
        } else {
            //一次性容器,可以持有多个并提供 添加和移除。
            CompositeDisposable disposables = new CompositeDisposable();
            disposables.add(disposable);
            mSubscriptionMap.put(tag, disposables);
        }
    }

    /**
     * 取消订阅
     * @param tag tag
     */
    public void unSubscribe(Object tag) {
        if (mSubscriptionMap == null) {
            return;
        }
        if (!mSubscriptionMap.containsKey(tag)){
            return;
        }
        if (mSubscriptionMap.get(tag) != null) {
            mSubscriptionMap.get(tag).dispose();
        }
        mSubscriptionMap.remove(tag);
    }

    /**
     * 取消订阅
     * @param tag tag
     */
    public void unSubscribe(Object tag,Disposable disposable) {
        if (mSubscriptionMap == null) {
            return;
        }
        if (!mSubscriptionMap.containsKey(tag)){
            return;
        }
        if (mSubscriptionMap.get(tag) != null) {
            if(!disposable.isDisposed()){
                disposable.dispose();
            }
            mSubscriptionMap.get(tag).remove(disposable);
        }
        if(mSubscriptionMap.get(tag).size() == 0){
            mSubscriptionMap.get(tag).dispose();
            mSubscriptionMap.remove(tag);

        }
    }


    public static  RxBus getInstance() {
        return Holder.INSTANCE;
    }
    private static class Holder {
        private static final RxBus INSTANCE = new RxBus();
    }
}
