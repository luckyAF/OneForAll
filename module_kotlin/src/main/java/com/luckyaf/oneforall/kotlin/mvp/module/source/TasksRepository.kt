package com.luckyaf.oneforall.kotlin.mvp.module.source

import android.content.Context
import com.luckyaf.oneforall.kotlin.mvp.module.Task
import com.luckyaf.oneforall.kotlin.mvp.module.source.local.TasksLocalDataSource
import com.luckyaf.oneforall.kotlin.mvp.module.source.local.ToDoDatabase
import com.luckyaf.oneforall.kotlin.utils.AppExecutors
import java.util.ArrayList
import java.util.LinkedHashMap

/**
 * 类描述：
 * @author Created by luckyAF on 2018/7/19
 *
 */
class TasksRepository(
        val tasksLocalDataSource: TasksDataSource
) : TasksDataSource {

    var cachedTasks: LinkedHashMap<String, Task> = LinkedHashMap()

    var cacheIsDirty = false




    override fun getTasks(callback: TasksDataSource.LoadTasksCallback) {
        // Respond immediately with cache if available and not dirty
        if (cachedTasks.isNotEmpty() && !cacheIsDirty) {
            callback.onTasksLoaded(ArrayList(cachedTasks.values))
            return
        }

//        if (cacheIsDirty) {
//            // If the cache is dirty we need to fetch new data from the network.
//            getTasksFromRemoteDataSource(callback)
//        } else {
            // Query the local storage if available. If not, query the network.
            tasksLocalDataSource.getTasks(object : TasksDataSource.LoadTasksCallback {
                override fun onTasksLoaded(tasks: List<Task>) {
                    refreshCache(tasks)
                    callback.onTasksLoaded(ArrayList(cachedTasks.values))
                }

                override fun onDataNotAvailable() {
                    //  getTasksFromRemoteDataSource(callback)
                    callback.onDataNotAvailable()
                }
            })
        //}
    }

    override fun getTask(taskId: String, callback: TasksDataSource.GetTaskCallback) {
        val taskInCache = getTaskWithId(taskId)

        // Respond immediately with cache if available
        if (taskInCache != null) {
            callback.onTaskLoaded(taskInCache)
            return
        }
        // Load from server/persisted if needed.

        // Is the task in the local data source? If not, query the network.
        tasksLocalDataSource.getTask(taskId, object : TasksDataSource.GetTaskCallback {
            override fun onTaskLoaded(task: Task) {
                // Do in memory cache update to keep the app UI up to date
                cacheAndPerform(task) {
                    callback.onTaskLoaded(it)
                }
            }

            override fun onDataNotAvailable() {
                callback.onDataNotAvailable()
//                tasksRemoteDataSource.getTask(taskId, object : TasksDataSource.GetTaskCallback {
//                    override fun onTaskLoaded(task: Task) {
//                        // Do in memory cache update to keep the app UI up to date
//                        cacheAndPerform(task) {
//                            callback.onTaskLoaded(it)
//                        }
//                    }
//
//                    override fun onDataNotAvailable() {
//                        callback.onDataNotAvailable()
//                    }
//                })
            }
        })
      }

    override fun saveTask(task: Task) {
        // Do in memory cache update to keep the app UI up to date
        cacheAndPerform(task) {
            //tasksRemoteDataSource.saveTask(it)
            tasksLocalDataSource.saveTask(it)
        }
    }

    override fun completeTask(task: Task) {
        // Do in memory cache update to keep the app UI up to date
        cacheAndPerform(task) {
            it.isCompleted = true
            //tasksRemoteDataSource.completeTask(it)
            tasksLocalDataSource.completeTask(it)

        }
      }

    override fun completeTask(taskId: String) {
        getTaskWithId(taskId)?.let {
            completeTask(it)
        }
      }

    override fun activateTask(task: Task) {
        cacheAndPerform(task) {
            it.isCompleted = false
            //tasksRemoteDataSource.activateTask(it)
            tasksLocalDataSource.activateTask(it)
        }
          }

    override fun activateTask(taskId: String) {
        getTaskWithId(taskId)?.let {
            activateTask(it)
        }
    }

    override fun clearCompletedTasks() {
        //tasksRemoteDataSource.clearCompletedTasks()
        tasksLocalDataSource.clearCompletedTasks()

        cachedTasks = cachedTasks.filterValues {
            !it.isCompleted
        } as LinkedHashMap<String, Task>
    }

    override fun refreshTasks() {
        cacheIsDirty = true

    }

    override fun deleteAllTasks() {
        //tasksRemoteDataSource.deleteAllTasks()
        tasksLocalDataSource.deleteAllTasks()
        cachedTasks.clear()
          }

    override fun deleteTask(taskId: String) {
        //tasksRemoteDataSource.deleteTask(taskId)
        tasksLocalDataSource.deleteTask(taskId)
        cachedTasks.remove(taskId)
         }



    private fun refreshCache(tasks: List<Task>) {
        cachedTasks.clear()
        tasks.forEach {
            cacheAndPerform(it) {}
        }
        cacheIsDirty = false
    }



    private fun getTaskWithId(id: String) = cachedTasks[id]

    private inline fun cacheAndPerform(task: Task, perform: (Task) -> Unit) {
        val cachedTask = Task(task.title, task.description, task.id).apply {
            isCompleted = task.isCompleted
        }
        cachedTasks.put(cachedTask.id, cachedTask)
        perform(cachedTask)
    }


    companion object {

        private var INSTANCE: TasksRepository? = null


        @JvmStatic fun getInstance(context: Context): TasksRepository {
            return INSTANCE
                    ?: TasksRepository(TasksLocalDataSource.getInstance(AppExecutors(), ToDoDatabase.getInstance(context).taskDao())).apply { INSTANCE = this }
        }


        @JvmStatic fun destroyInstance() {
            INSTANCE = null
        }
    }
}