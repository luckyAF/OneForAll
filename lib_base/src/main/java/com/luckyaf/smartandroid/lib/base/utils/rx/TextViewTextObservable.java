package com.luckyaf.smartandroid.lib.base.utils.rx;

import android.text.Editable;
import android.text.TextWatcher;
import android.widget.TextView;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.MainThreadDisposable;

import static com.luckyaf.smartandroid.lib.base.utils.PreconditionUtil.checkMainThread;

/**
 * 类描述：
 *
 * @author Created by luckyAF on 2018/7/2
 */
public final class TextViewTextObservable extends Observable<CharSequence> {
    private final TextView textView;
    public TextViewTextObservable(TextView textView) {
        this.textView = textView;
    }
    @Override
    protected void subscribeActual(Observer<? super CharSequence> observer) {
        if (!checkMainThread(observer)) {
            return;
        }
        Listener listener = new Listener(textView, observer);
        observer.onSubscribe(listener);
        textView.addTextChangedListener(listener);
    }

    final static class Listener extends MainThreadDisposable implements TextWatcher {
        private final TextView view;
        private final Observer<? super CharSequence> observer;

        Listener(TextView view, Observer<? super CharSequence> observer) {
            this.view = view;
            this.observer = observer;
        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            if (!isDisposed()) {
                observer.onNext(s);
            }
        }

        @Override
        public void afterTextChanged(Editable s) {
        }

        @Override
        protected void onDispose() {
            view.removeTextChangedListener(this);
        }
    }
}
