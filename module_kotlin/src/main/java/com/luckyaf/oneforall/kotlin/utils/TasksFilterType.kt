package com.luckyaf.oneforall.kotlin.utils

/**
 * 类描述：任务显示筛选
 * @author Created by luckyAF on 2018/7/19
 *
 */
enum class TasksFilterType {
    /**
     * Do not filter tasks.
     */
    ALL_TASKS,

    /**
     * Filters only the active (not completed yet) tasks.
     */
    ACTIVE_TASKS,

    /**
     * Filters only the completed tasks.
     */
    COMPLETED_TASKS
}