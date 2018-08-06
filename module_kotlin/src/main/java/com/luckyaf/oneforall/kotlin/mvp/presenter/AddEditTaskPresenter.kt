package com.luckyaf.oneforall.kotlin.mvp.presenter

import com.luckyaf.oneforall.kotlin.base.RxPresenter
import com.luckyaf.oneforall.kotlin.mvp.contract.AddEditTaskContract
import com.luckyaf.oneforall.kotlin.mvp.module.Task
import com.luckyaf.oneforall.kotlin.mvp.module.source.TasksDataSource

/**
 * 类描述：
 * @author Created by luckyAF on 2018/7/20
 *
 */
class AddEditTaskPresenter (
        private val taskId: String?,
        val tasksRepository: TasksDataSource,
        val addTaskView: AddEditTaskContract.View,
        override var isDataMissing: Boolean

) :RxPresenter(),AddEditTaskContract.Presenter,TasksDataSource.GetTaskCallback {

    override fun start() {
        if (taskId != null && isDataMissing) {
            populateTask()
        }
    }
    override fun saveTask(title: String, description: String) {
        if (taskId == null) {
            createTask(title, description)
        } else {
            updateTask(title, description)
        }
    }


    override fun populateTask() {
        if (taskId == null) {
            throw RuntimeException("populateTask() was called but task is new.")
        }
        tasksRepository.getTask(taskId, this)
    }

    override fun onTaskLoaded(task: Task) {
        // The view may not be able to handle UI updates anymore
        if (addTaskView.isActive) {
            addTaskView.setTitle(task.title)
            addTaskView.setDescription(task.description)
        }
        isDataMissing = false
    }

    override fun onDataNotAvailable() {
        // The view may not be able to handle UI updates anymore
        if (addTaskView.isActive) {
            addTaskView.showEmptyTaskError()
        }
    }

    private fun createTask(title: String, description: String) {
        val newTask = Task(title, description)
        if (newTask.isEmpty) {
            addTaskView.showEmptyTaskError()
        } else {
            tasksRepository.saveTask(newTask)
            addTaskView.showTasksList()
        }
    }

    private fun updateTask(title: String, description: String) {
        if (taskId == null) {
            throw RuntimeException("updateTask() was called but task is new.")
        }
        tasksRepository.saveTask(Task(title, description, taskId))
        addTaskView.showTasksList() // After an edit, go back to the list.
    }




}