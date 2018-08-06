package com.luckyaf.oneforall.kotlin.mvp.module.source.local

import android.arch.persistence.room.*
import com.luckyaf.oneforall.kotlin.mvp.module.Task

/**
 * 类描述：
 * @author Created by luckyAF on 2018/7/20
 *
 */
@Dao
interface TasksDao {
    @Query("SELECT * FROM TASKS ") fun getTasks():List<Task>

    @Query("SELECT * FROM Tasks WHERE entryid = :taskId") fun getTaskById(taskId: String): Task?

    @Insert(onConflict = OnConflictStrategy.REPLACE) fun insertTask(task: Task)

    @Update fun updateTask(task: Task): Int

    @Query("UPDATE tasks SET completed = :completed WHERE entryId = :taskId")
    fun updateCompleted(taskId: String, completed: Boolean)

    @Query("DELETE FROM Tasks WHERE entryid = :taskId") fun deleteTaskById(taskId: String): Int

    @Query("DELETE FROM Tasks") fun deleteTasks()

    @Query("DELETE FROM Tasks WHERE completed = 1") fun deleteCompletedTasks(): Int

}