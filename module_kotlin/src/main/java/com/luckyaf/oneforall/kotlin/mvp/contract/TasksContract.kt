package com.luckyaf.oneforall.kotlin.mvp.contract

import com.luckyaf.oneforall.kotlin.base.BasePresenter
import com.luckyaf.oneforall.kotlin.base.BaseView
import com.luckyaf.oneforall.kotlin.mvp.module.Task
import com.luckyaf.oneforall.kotlin.utils.TasksFilterType

/**
 * 类描述：
 * @author Created by luckyAF on 2018/7/19
 *
 */
@Suppress("unused")
interface TasksContract {

    interface View : BaseView {

        var isActive: Boolean

        fun setLoadingIndicator(active: Boolean)

        fun showTasks(tasks: List<Task>)

        fun showAddTask()

        fun showTaskDetailsUi(taskId: String)

        fun showTaskMarkedComplete()

        fun showTaskMarkedActive()

        fun showCompletedTasksCleared()

        fun showLoadingTasksError()

        fun showNoTasks()

        fun showActiveFilterLabel()

        fun showCompletedFilterLabel()

        fun showAllFilterLabel()

        fun showNoActiveTasks()

        fun showNoCompletedTasks()

        fun showSuccessfullySavedMessage()

        fun showFilteringPopUpMenu()
    }

    interface Presenter : BasePresenter {

        var currentFiltering: TasksFilterType

        fun result(requestCode: Int, resultCode: Int)

        fun loadTasks(forceUpdate: Boolean)

        fun addNewTask()

        fun openTaskDetails(requestedTask: Task)

        fun completeTask(completedTask: Task)

        fun activateTask(activeTask: Task)

        fun clearCompletedTasks()
    }
}