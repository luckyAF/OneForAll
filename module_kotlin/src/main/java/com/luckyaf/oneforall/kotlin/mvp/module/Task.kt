package com.luckyaf.oneforall.kotlin.mvp.module

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import java.util.*

/**
 * 类描述：
 * Model class for a Task.
 * @param title       title of the task
 * @param description description of the task
 * @param id          id of the task
 * @author Created by luckyAF on 2018/7/19
 */
@Entity(tableName = "tasks")
class Task @JvmOverloads constructor(
        @ColumnInfo(name = "title") var title : String = "",
        @ColumnInfo(name = "description") var description : String = "",
        @PrimaryKey @ColumnInfo(name = "entryId") var id : String = UUID.randomUUID().toString()
){

    /**
     * True if the task is completed, false if it's active.
     */
    @ColumnInfo(name = "completed") var isCompleted =  false

    val titleForList : String
        get() = if (title.isNotEmpty()) title else description

    val isActive : Boolean
        get() = !isCompleted

    val isEmpty : Boolean
        get() = title.isEmpty() && description.isEmpty()


}