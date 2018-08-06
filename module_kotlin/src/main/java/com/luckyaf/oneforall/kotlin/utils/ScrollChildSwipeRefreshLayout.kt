package com.luckyaf.oneforall.kotlin.utils

import android.content.Context
import android.support.v4.widget.SwipeRefreshLayout
import android.util.AttributeSet
import android.view.View

/**
 * 类描述：
 * @author Created by luckyAF on 2018/7/19
 *
 */
class ScrollChildSwipeRefreshLayout @JvmOverloads constructor(context: Context,
                                                              attributes: AttributeSet? = null)
    : SwipeRefreshLayout(context, attributes) {

    var scrollUpChild : View?= null

    override fun canChildScrollUp(): Boolean {
        return scrollUpChild?.canScrollVertically(-1) ?: super.canChildScrollUp()
    }

}