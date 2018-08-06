package com.luckyaf.oneforall.kotlin.mvp.presenter

import com.luckyaf.oneforall.kotlin.base.RxPresenter
import com.luckyaf.oneforall.kotlin.mvp.contract.TasksContract
import com.luckyaf.oneforall.kotlin.mvp.module.Task
import com.luckyaf.oneforall.kotlin.mvp.module.source.TasksDataSource
import com.luckyaf.oneforall.kotlin.utils.TasksFilterType
import java.util.ArrayList

/**
 * 类描述：
 * @author Created by luckyAF on 2018/7/19
 *
// */

class TasksPresenter(private val tasksDataSource: TasksDataSource, val tasksView : TasksContract.View ) : RxPresenter(), TasksContract.Presenter{

    override var currentFiltering = TasksFilterType.ALL_TASKS

    private var firstLoad = true

    override fun start() {
        loadTasks(false)
    }

    override fun result(requestCode: Int, resultCode: Int) {
        //onActivityResult
        //TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun loadTasks(forceUpdate: Boolean) {
        loadTasks(forceUpdate || firstLoad, true)
        firstLoad = false
    }

    private fun loadTasks(forceUpdate: Boolean, showLoadingUi: Boolean) {
        if (showLoadingUi) {
            tasksView.setLoadingIndicator(true)
        }
        if (forceUpdate) {
            tasksDataSource.refreshTasks()
        }

        tasksDataSource.getTasks(object : TasksDataSource.LoadTasksCallback {
            override fun onTasksLoaded(tasks: List<Task>) {
                val tasksToShow = ArrayList<Task>()
                for (task in tasks) {
                    when (currentFiltering) {
                        TasksFilterType.ALL_TASKS -> tasksToShow.add(task)
                        TasksFilterType.ACTIVE_TASKS -> if (task.isActive) {
                            tasksToShow.add(task)
                        }
                        TasksFilterType.COMPLETED_TASKS -> if (task.isCompleted) {
                            tasksToShow.add(task)
                        }
                    }
                }
                if (!tasksView.isActive) {
                    return
                }
                if (showLoadingUi) {
                    tasksView.setLoadingIndicator(false)
                }
                processTasks(tasksToShow)

            }

            override fun onDataNotAvailable() {
                if (!tasksView.isActive) {
                    return
                }
                if (showLoadingUi) {
                    tasksView.setLoadingIndicator(false)
                }
                tasksView.showLoadingTasksError()
            }

        })
    }

    private fun processTasks(tasks: List<Task>) {
        if (tasks.isEmpty()) {
            processEmptyTasks()
        } else {
            tasksView.showTasks(tasks)
            showFilterLabel()
        }
    }

    private fun processEmptyTasks() {
        when (currentFiltering) {
            TasksFilterType.ACTIVE_TASKS -> tasksView.showNoActiveTasks()
            TasksFilterType.COMPLETED_TASKS -> tasksView.showNoCompletedTasks()
            else -> tasksView.showNoTasks()
        }
    }

    private fun showFilterLabel() {
        when (currentFiltering) {
            TasksFilterType.ACTIVE_TASKS -> tasksView.showActiveFilterLabel()
            TasksFilterType.COMPLETED_TASKS -> tasksView.showCompletedFilterLabel()
            else -> tasksView.showAllFilterLabel()
        }
    }

    override fun addNewTask() {
        tasksView.showAddTask()
    }

    override fun openTaskDetails(requestedTask: Task) {
        tasksView.showTaskDetailsUi(requestedTask.id)
    }

    override fun completeTask(completedTask: Task) {
        tasksDataSource.completeTask(completedTask)
        tasksView.showTaskMarkedComplete()
        loadTasks(false, false)
    }

    override fun activateTask(activeTask: Task) {
        tasksDataSource.activateTask(activeTask)
        tasksView.showTaskMarkedActive()
        loadTasks(false, false)
    }

    override fun clearCompletedTasks() {
        tasksDataSource.clearCompletedTasks()
        tasksView.showCompletedTasksCleared()
        loadTasks(false, false)
    }

}