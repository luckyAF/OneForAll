package com.luckyaf.oneforall.kotlin.mvp.ui.activity

import android.os.Bundle
import android.support.design.widget.NavigationView
import android.support.v4.view.GravityCompat
import android.support.v4.widget.DrawerLayout
import android.view.MenuItem
import com.alibaba.android.arouter.facade.annotation.Route
import com.luckyaf.oneforall.kotlin.R
import com.luckyaf.oneforall.kotlin.base.SimpleActivity
import com.luckyaf.oneforall.kotlin.mvp.ui.fragment.TasksFragment
import com.luckyaf.oneforall.kotlin.utils.DisplayUtil
import com.luckyaf.onforall.common.core.RouterHub

import com.luckyaf.oneforall.kotlin.utils.replaceFragmentInActivity;
import com.luckyaf.oneforall.kotlin.utils.setupActionBar

/**
 * 类描述：
 * @author Created by luckyAF on 2018/7/18
 *
 */
@Route(path = RouterHub.KOTLIN_MAIN)
class TasksActivity : SimpleActivity() {

    private lateinit var drawerLayout: DrawerLayout

    override fun getLayoutId(): Int {
        return R.layout.kotlin_activity_tasks
    }

    override fun initParams(bundle: Bundle?) {

    }

    override fun initView() {

        DisplayUtil.setCustomDensity(this,application)
        // Set up the toolbar.
        setupActionBar(R.id.toolbar) {
            setHomeAsUpIndicator(R.drawable.kotlin_ic_menu)
            setDisplayHomeAsUpEnabled(true)
        }
        // Set up the navigation drawer.
        drawerLayout = (findViewById<DrawerLayout>(R.id.drawer_layout)).apply {
            setStatusBarBackground(R.color.base_colorPrimaryDark)
        }

        setupDrawerContent(findViewById(R.id.nav_view))

        supportFragmentManager.findFragmentById(R.id.contentFrame)
                as TasksFragment? ?: TasksFragment.newInstance().also {
            replaceFragmentInActivity(it, R.id.contentFrame)
        }

    }

    override fun initData() {
         }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            // Open the navigation drawer when the home icon is selected from the toolbar.
            drawerLayout.openDrawer(GravityCompat.START)
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    private fun setupDrawerContent(navigationView: NavigationView) {
        navigationView.setNavigationItemSelectedListener { menuItem ->
            if (menuItem.itemId == R.id.statistics_navigation_menu_item) {
                //val intent = Intent(this@TasksActivity, StatisticsActivity::class.java)
               // startActivity(intent)
            }
            // Close the navigation drawer when an item is selected.
            menuItem.isChecked = true
            drawerLayout.closeDrawers()
            true
        }
    }
}