package com.luckyaf.oneforall.kotlin.mvp.ui.adpater

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.CheckBox
import com.luckyaf.oneforall.kotlin.R
import com.luckyaf.oneforall.kotlin.mvp.module.Task

/**
 * 类描述：
 * @author Created by luckyAF on 2018/7/20
 *
 */
class TasksAdapter(tasks: List<Task>, private val itemListener: TaskItemListener) : BaseAdapter() {

    var tasks: List<Task> = tasks
        set(tasks) {
            field = tasks
            notifyDataSetChanged()
        }


    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val task = tasks[position]
        val rowView = convertView ?: LayoutInflater.from(parent.context)
                .inflate(R.layout.kotlin_item_task,parent,false)
        with(rowView.findViewById<CheckBox>(R.id.complete)) {
            // Active/completed task UI
            isChecked = task.isCompleted
            val rowViewBackground =
                    if (task.isCompleted) R.drawable.kotlin_completed_touch_feedback
                    else R.drawable.common_touch_feedback
            rowView.setBackgroundResource(rowViewBackground)
            setOnClickListener {
                if (!task.isCompleted) {
                    itemListener.onCompleteTaskClick(task)
                } else {
                    itemListener.onActivateTaskClick(task)
                }
            }
        }
        rowView.setOnClickListener { itemListener.onTaskClick(task) }
        return rowView

    }

    override fun getItem(position: Int) = tasks[position]

    override fun getItemId(position: Int) = position.toLong()

    override fun getCount() = tasks.size

    interface TaskItemListener {

        fun onTaskClick(clickedTask: Task)

        fun onCompleteTaskClick(completedTask: Task)

        fun onActivateTaskClick(activatedTask: Task)
    }
}