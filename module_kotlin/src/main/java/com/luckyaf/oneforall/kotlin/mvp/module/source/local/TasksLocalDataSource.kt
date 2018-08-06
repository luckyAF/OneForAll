package com.luckyaf.oneforall.kotlin.mvp.module.source.local

import android.support.annotation.VisibleForTesting
import com.luckyaf.oneforall.kotlin.mvp.module.Task
import com.luckyaf.oneforall.kotlin.mvp.module.source.TasksDataSource
import com.luckyaf.oneforall.kotlin.utils.AppExecutors

/**
 * 类描述：
 * @author Created by luckyAF on 2018/7/20
 *
 */
class TasksLocalDataSource private constructor(
        val appExecutors: AppExecutors,
        val tasksDao: TasksDao
): TasksDataSource{
    override fun getTasks(callback: TasksDataSource.LoadTasksCallback) {
        appExecutors.diskIO.execute {
            val tasks = tasksDao.getTasks()
            appExecutors.mainThread.execute {
                if (tasks.isEmpty()) {
                    // This will be called if the table is new or just empty.
                    callback.onDataNotAvailable()
                } else {
                    callback.onTasksLoaded(tasks)
                }
            }
        }
    }

    override fun getTask(taskId: String, callback: TasksDataSource.GetTaskCallback) {
        appExecutors.diskIO.execute {
            val task = tasksDao.getTaskById(taskId)
            appExecutors.mainThread.execute {
                if (task != null) {
                    callback.onTaskLoaded(task)
                } else {
                    callback.onDataNotAvailable()
                }
            }
        }
    }

    override fun saveTask(task: Task) {
        appExecutors.diskIO.execute { tasksDao.insertTask(task) }

    }

    override fun completeTask(task: Task) {
        appExecutors.diskIO.execute { tasksDao.updateCompleted(task.id, true) }
    }

    override fun completeTask(taskId: String) {
        appExecutors.diskIO.execute { tasksDao.updateCompleted(taskId, true) }

    }

    override fun activateTask(task: Task) {
        appExecutors.diskIO.execute { tasksDao.updateCompleted(task.id, false) }
    }

    override fun activateTask(taskId: String) {
        appExecutors.diskIO.execute { tasksDao.updateCompleted(taskId, false) }

    }

    override fun clearCompletedTasks() {
        appExecutors.diskIO.execute { tasksDao.deleteCompletedTasks() }
    }

    override fun refreshTasks() {
    }

    override fun deleteAllTasks() {
        appExecutors.diskIO.execute { tasksDao.deleteTasks() }

    }

    override fun deleteTask(taskId: String) {
        appExecutors.diskIO.execute { tasksDao.deleteTaskById(taskId) }
    }

    companion object {
        private var INSTANCE: TasksLocalDataSource? = null

        @JvmStatic
        fun getInstance(appExecutors: AppExecutors, tasksDao: TasksDao): TasksLocalDataSource {
            if (INSTANCE == null) {
                synchronized(TasksLocalDataSource::javaClass) {
                    INSTANCE = TasksLocalDataSource(appExecutors, tasksDao)
                }
            }
            return INSTANCE!!
        }

        @VisibleForTesting
        fun clearInstance() {
            INSTANCE = null
        }
    }
}