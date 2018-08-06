package com.luckyaf.oneforall.kotlin.mvp.module.source.local

import android.arch.persistence.room.Database
import android.arch.persistence.room.Room
import android.arch.persistence.room.RoomDatabase
import android.content.Context
import com.luckyaf.oneforall.kotlin.mvp.module.Task

/**
 * 类描述：
 * @author Created by luckyAF on 2018/7/20
 *
 */
@Database(entities = [Task::class],version = 1)

abstract class ToDoDatabase : RoomDatabase() {
    abstract fun taskDao() : TasksDao

    companion object {

        private var INSTANCE: ToDoDatabase? = null

        private val lock = Any()

        fun getInstance(context: Context): ToDoDatabase {
            synchronized(lock) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.applicationContext,
                            ToDoDatabase::class.java, "Tasks.db")
                            .build()
                }
                return INSTANCE!!
            }
        }
    }


}