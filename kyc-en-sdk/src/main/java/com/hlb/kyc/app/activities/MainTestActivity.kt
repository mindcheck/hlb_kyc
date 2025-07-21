package com.hlb.kyc.app.activities

import android.app.ActivityManager
import android.content.Context
import android.content.res.Configuration
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity

open class MainTestActivity: AppCompatActivity() {

    override fun attachBaseContext(newBase: Context?) {

        val newOverride = Configuration(newBase?.resources?.configuration)
        newOverride.fontScale = 1.0f
        applyOverrideConfiguration(newOverride)

        super.attachBaseContext(newBase)
    }


    private lateinit var rootView: ViewGroup
    private var willDisplayMask: Boolean = false


    /**
     * addView() function is slower then GONE/VISIBLE
     * so, I focused on to make GONE and VISIBLE of mask view
     */

    fun setWillDisplayMask(boolean: Boolean) {
        willDisplayMask = boolean
        Handler(Looper.getMainLooper()).postDelayed({
            willDisplayMask = true
        }, 50)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        rootView = window.decorView.rootView as ViewGroup
        println("onCreate from base activity")
        println("onCreate Activity: ${this::class.simpleName}")

        setWillDisplayMask(true)

    }

    override fun onUserLeaveHint() {

        super.onUserLeaveHint()
    }


    @Deprecated("Deprecated in Java")
    override fun onBackPressed() {

        setWillDisplayMask(false)

        // Back press exit mask
        val tasks = (getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager).appTasks
        if (tasks[0].taskInfo.numActivities == 1) {
            setWillDisplayMask(true)
        }
        super.onBackPressed()

    } // onBackPressed

    private fun isTablet(): Boolean {
        if (Build.MODEL.equals("J9210", ignoreCase = true)) {
            return false
        }
        return (resources.configuration.screenLayout and Configuration.SCREENLAYOUT_SIZE_MASK) >= Configuration.SCREENLAYOUT_SIZE_LARGE
    }


}