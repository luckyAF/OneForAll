package com.luckyaf.oneforall.kotlin.utils

import android.content.Context
import android.widget.Toast

/**
 * 类描述：
 * @author Created by luckyAF on 2018/7/18
 *
 */
object ToastUtil {

    private var  toast : Toast ?= null
    fun showMessage(context: Context?,message : CharSequence){
        if(null == toast){
            toast = Toast.makeText(context,message,Toast.LENGTH_SHORT)
        }else{
            toast?.setText(message)
        }
        toast?.show()
    }
    fun showMessage(context: Context?,resourceId:Int){
        if(null == toast){
            toast = Toast.makeText(context,resourceId,Toast.LENGTH_SHORT)
        }else{
            toast?.setText(resourceId)
        }
        toast?.show()
    }
}