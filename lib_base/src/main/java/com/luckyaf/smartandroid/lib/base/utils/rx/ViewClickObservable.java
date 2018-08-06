package com.luckyaf.smartandroid.lib.base.utils.rx;

import android.view.View;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.MainThreadDisposable;

import static com.luckyaf.smartandroid.lib.base.utils.PreconditionUtil.checkMainThread;

/**
 * 类描述：点击
 *
 * @author Created by luckyAF on 2018/7/2
 */
public final class ViewClickObservable extends Observable<Object> {
    private final View view;
    public ViewClickObservable(View view) {
        this.view = view;
    }
    @Override
    protected void subscribeActual(Observer<? super Object> observer) {
        if (!checkMainThread(observer)) {
            return;
        }
        Listener listener = new Listener(view, observer);
        observer.onSubscribe(listener);
        view.setOnClickListener(listener);
    }

    static final class Listener extends MainThreadDisposable implements View.OnClickListener {
        private final View view;
        private final Observer<? super Object> observer;

        Listener(View view, Observer<? super Object> observer) {
            this.view = view;
            this.observer = observer;
        }

        @Override
        public void onClick(View v) {
            if (!isDisposed()) {
                observer.onNext(null);
            }
        }

        @Override protected void onDispose() {
            view.setOnClickListener(null);
        }
    }

}
