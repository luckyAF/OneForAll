package com.luckyaf.oneforall.kotlin.mvp.ui.fragment

import android.os.Bundle
import android.support.design.widget.FloatingActionButton
import android.support.v4.content.ContextCompat
import android.support.v4.widget.SwipeRefreshLayout
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.widget.*
import com.luckyaf.oneforall.kotlin.R
import com.luckyaf.oneforall.kotlin.base.BaseFragment
import com.luckyaf.oneforall.kotlin.mvp.contract.TasksContract
import com.luckyaf.oneforall.kotlin.mvp.module.Task
import com.luckyaf.oneforall.kotlin.mvp.module.source.TasksRepository
import com.luckyaf.oneforall.kotlin.mvp.presenter.TasksPresenter
import com.luckyaf.oneforall.kotlin.mvp.ui.adpater.TasksAdapter
import com.luckyaf.oneforall.kotlin.utils.ScrollChildSwipeRefreshLayout
import com.luckyaf.oneforall.kotlin.utils.TasksFilterType

/**
 * 类描述：
 * @author Created by luckyAF on 2018/7/19
 *
 */
class TasksFragment : BaseFragment<TasksPresenter>(), TasksContract.View {


    override var isActive: Boolean = false
        get() = isAdded

    private lateinit var noTasksView: View
    private lateinit var noTaskIcon: ImageView
    private lateinit var noTaskMainView: TextView
    private lateinit var noTaskAddView: TextView
    private lateinit var tasksView: LinearLayout
    private lateinit var filteringLabelView: TextView


    private var itemListener: TasksAdapter.TaskItemListener = object : TasksAdapter.TaskItemListener {
        override fun onTaskClick(clickedTask: Task) {
            mPresenter.openTaskDetails(clickedTask)
        }

        override fun onCompleteTaskClick(completedTask: Task) {
            mPresenter.completeTask(completedTask)
        }

        override fun onActivateTaskClick(activatedTask: Task) {
            mPresenter.activateTask(activatedTask)
        }
    }

    private var listAdapter = TasksAdapter(ArrayList(0), itemListener)


    override fun getLayoutId(): Int {
        return R.layout.kotlin_fragment_tasks
    }

    override fun getPresenter(): TasksPresenter {
        return TasksPresenter(TasksRepository.getInstance(mContext), this)
    }

    override fun initParams(bundle: Bundle?) {
    }

    override fun initView() {
        val listView = findView<ListView>(R.id.tasks_list).apply {
            adapter = listAdapter
        }
        // Set up progress indicator
        findView<ScrollChildSwipeRefreshLayout>(R.id.refresh_layout).apply {
            setColorSchemeColors(
                    ContextCompat.getColor(context, R.color.common_colorPrimary),
                    ContextCompat.getColor(context, R.color.common_colorAccent),
                    ContextCompat.getColor(context, R.color.common_colorPrimaryDark)
            )
            // Set the scrolling view in the custom SwipeRefreshLayout.
            scrollUpChild = listView
            setOnRefreshListener { mPresenter.loadTasks(false) }
        }

        filteringLabelView = findView(R.id.filteringLabel)
        tasksView = findView(R.id.tasksLL)

        // Set up  no tasks view
        noTasksView = findView(R.id.noTasks)
        noTaskIcon = findView(R.id.noTasksIcon)
        noTaskMainView = findView(R.id.noTasksMain)
        noTaskAddView = (findView<TextView>(R.id.noTasksAdd)).also {
            it.setOnClickListener { showAddTask() }
        }
        activity?.findViewById<FloatingActionButton>(R.id.fab_add_task)?.apply {
            setImageResource(R.drawable.kotlin_ic_add)
            setOnClickListener { mPresenter.addNewTask() }
        }
        setHasOptionsMenu(true)
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.menu_clear -> mPresenter.clearCompletedTasks()
            R.id.menu_filter -> showFilteringPopUpMenu()
            R.id.menu_refresh -> mPresenter.loadTasks(true)
        }
        return true
    }

    override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater) {
        inflater.inflate(R.menu.kotlin_fragment_tasks_menu, menu)
    }

    override fun initData() {
        mPresenter.start()

    }

    override fun setLoadingIndicator(active: Boolean) {
        val root = view ?: return
        with(root.findViewById<SwipeRefreshLayout>(R.id.refresh_layout)) {
            // Make sure setRefreshing() is called after the layout is done with everything else.
            post { isRefreshing = active }
        }
    }

    override fun showTasks(tasks: List<Task>) {
        listAdapter.tasks = tasks
        tasksView.visibility = View.VISIBLE
        noTasksView.visibility = View.GONE
    }

    override fun showAddTask() {
       // val intent = Intent(context, AddEditTaskActivity::class.java)
       // startActivityForResult(intent, AddEditTaskActivity.REQUEST_ADD_TASK)
    }

    override fun showTaskDetailsUi(taskId: String) {
//        val intent = Intent(context, TaskDetailActivity::class.java).apply {
//            putExtra(TaskDetailActivity.EXTRA_TASK_ID, taskId)
//        }
//        startActivity(intent)
    }

    override fun showTaskMarkedComplete() {
        showMessage(getString(R.string.task_marked_complete))
    }

    override fun showTaskMarkedActive() {
        showMessage(getString(R.string.task_marked_active))
    }

    override fun showCompletedTasksCleared() {
        showMessage(getString(R.string.completed_tasks_cleared))
    }

    override fun showLoadingTasksError() {
        showMessage(getString(R.string.loading_tasks_error))
    }

    override fun showNoTasks() {
        showNoTasksViews(resources.getString(R.string.no_tasks_all), R.drawable.kotlin_ic_assignment_turned_in, false)
    }

    override fun showActiveFilterLabel() {
        filteringLabelView.text = resources.getString(R.string.label_active)
    }

    override fun showCompletedFilterLabel() {
        filteringLabelView.text = resources.getString(R.string.label_completed)
    }

    override fun showAllFilterLabel() {
        filteringLabelView.text = resources.getString(R.string.label_all)
    }

    override fun showNoActiveTasks() {
        showNoTasksViews(resources.getString(R.string.no_tasks_active), R.drawable.kotlin_ic_check_circle, false)
    }

    override fun showNoCompletedTasks() {
        showNoTasksViews(resources.getString(R.string.no_tasks_all), R.drawable.kotlin_ic_assignment_turned_in, false)

    }

    private fun showNoTasksViews(mainText: String, iconRes: Int, showAddView: Boolean) {
        tasksView.visibility = View.GONE
        noTasksView.visibility = View.VISIBLE

        noTaskMainView.text = mainText
        noTaskIcon.setImageResource(iconRes)
        noTaskAddView.visibility = if (showAddView) View.VISIBLE else View.GONE
    }


    override fun showSuccessfullySavedMessage() {
        showMessage(getString(R.string.successfully_saved_task_message))
    }

    override fun showFilteringPopUpMenu() {
        PopupMenu(context, activity?.findViewById(R.id.menu_filter)).apply {
            menuInflater.inflate(R.menu.kotlin_fileter_tasks, menu)
            setOnMenuItemClickListener { item ->
                when (item.itemId) {
                    R.id.active -> mPresenter.currentFiltering = TasksFilterType.ACTIVE_TASKS
                    R.id.completed -> mPresenter.currentFiltering = TasksFilterType.COMPLETED_TASKS
                    else -> mPresenter.currentFiltering = TasksFilterType.ALL_TASKS
                }
                mPresenter.loadTasks(false)
                true
            }
            show()
        }
    }


    companion object {
        fun newInstance() = TasksFragment()
    }


}