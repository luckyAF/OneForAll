package com.luckyaf.smartandroid.lib.base.utils;

import android.content.Context;
import android.widget.Toast;

/**
 * 类描述：
 *
 * @author Created by luckyAF on 2018/6/29
 */
@SuppressWarnings("unused")
public class ToastUtil {
    private static Toast toast;

    private ToastUtil() {
        throw new UnsupportedOperationException("cannot be instantiated");
    }


    public static void showMessage(Context context, CharSequence message) {
        if (toast != null) {
            toast.setText(message);
            toast.show();
        } else {
            toast = Toast.makeText(context, message, Toast.LENGTH_SHORT);
            toast.show();
        }
    }

    public static void showMessage(Context context, int message) {
        if (toast != null) {
            toast.setText(message);
            toast.show();
        } else {
            toast = Toast.makeText(context, message, Toast.LENGTH_SHORT);
            toast.show();
        }
    }

}
