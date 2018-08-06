package com.luckyaf.oneforall.kotlin.mvp.contract

import com.luckyaf.oneforall.kotlin.base.BasePresenter
import com.luckyaf.oneforall.kotlin.base.BaseView

/**
 * 类描述：
 * @author Created by luckyAF on 2018/7/20
 *
 */
interface AddEditTaskContract {
    interface View : BaseView{
        var isActive: Boolean

        fun showEmptyTaskError()

        fun showTasksList()

        fun setTitle(title: String)

        fun setDescription(description: String)

    }

    interface Presenter : BasePresenter{

        var isDataMissing: Boolean

        fun saveTask(title: String, description: String)

        fun populateTask()
    }
}