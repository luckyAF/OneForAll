package com.luckyaf.oneforall.kotlin.utils

import android.app.Activity
import android.app.Application
import android.content.ComponentCallbacks
import android.content.res.Configuration

/**
 * 类描述：
 * @author Created by luckyAF on 2018/8/3
 *
 */
@Suppress("unused")
class DisplayUtil{
    companion object {
        private var sNowCompatDensity = -1.0f
        private var sNowCompatScaleDensity  = -1.0f

        fun setCustomDensity(activity: Activity,app:Application){
            val appDisplayMetrics = app.resources.displayMetrics
            if(sNowCompatDensity < 0){
                sNowCompatDensity = appDisplayMetrics.density
                sNowCompatScaleDensity = appDisplayMetrics.scaledDensity
                app.registerComponentCallbacks(object :ComponentCallbacks{
                    override fun onLowMemory() {
                    }
                    override fun onConfigurationChanged(newConfig: Configuration?) {
                        if(null != newConfig && newConfig.fontScale > 0){
                            sNowCompatScaleDensity = app.resources.displayMetrics.scaledDensity
                        }
                    }
                })
            }

            val  targetDensity = appDisplayMetrics.widthPixels / 360
            val  targetScaleDensity = targetDensity * (sNowCompatScaleDensity / sNowCompatDensity)
            val  targetDensityDpi = 160 * targetDensity

            appDisplayMetrics.density = targetDensity.toFloat()
            appDisplayMetrics.scaledDensity = targetScaleDensity
            appDisplayMetrics.densityDpi = targetDensityDpi

            val activityDisplayMetrics = activity.resources.displayMetrics
            activityDisplayMetrics.density = targetDensity.toFloat()
            activityDisplayMetrics.scaledDensity = targetScaleDensity
            activityDisplayMetrics.densityDpi = targetDensityDpi

        }

    }

}