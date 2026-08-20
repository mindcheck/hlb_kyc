package com.hlb.kyc.app.utils

import android.app.Activity
import android.util.TypedValue
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

private const val STATUS_BAR_SCRIM_TAG = "hlb_kyc_status_bar_scrim"

/**
 * Edge-to-edge is enforced from Android 15 (API 35) and can no longer be opted out of
 * when targeting API 36, so every screen has to inset its own content.
 */
fun applySystemBarInsets(activity: Activity) {
    val content = activity.findViewById<View>(android.R.id.content) ?: return
    ViewCompat.setOnApplyWindowInsetsListener(content) { view, insets ->
        val bars = insets.getInsets(
            WindowInsetsCompat.Type.systemBars() or WindowInsetsCompat.Type.displayCutout()
        )
        view.setPadding(bars.left, bars.top, bars.right, bars.bottom)
        updateStatusBarScrim(activity, bars.top)
        WindowInsetsCompat.CONSUMED
    }
    ViewCompat.requestApplyInsets(content)
}

fun applySystemBarInsets(view: View) {
    ViewCompat.setOnApplyWindowInsetsListener(view) { v, insets ->
        val bars = insets.getInsets(
            WindowInsetsCompat.Type.systemBars() or WindowInsetsCompat.Type.displayCutout()
        )
        v.setPadding(bars.left, bars.top, bars.right, bars.bottom)
        WindowInsetsCompat.CONSUMED
    }
    ViewCompat.requestApplyInsets(view)
}

/** Paints the (now transparent) status bar area so it keeps its pre-edge-to-edge look. */
private fun updateStatusBarScrim(activity: Activity, height: Int) {
    val decor = activity.window.decorView as? ViewGroup ?: return
    val existing = decor.findViewWithTag<View>(STATUS_BAR_SCRIM_TAG)
    if (height <= 0) {
        existing?.let { decor.removeView(it) }
        return
    }
    if (existing == null) {
        val scrim = View(activity)
        scrim.tag = STATUS_BAR_SCRIM_TAG
        scrim.setBackgroundColor(statusBarScrimColor(activity))
        decor.addView(
            scrim,
            FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, height, Gravity.TOP)
        )
    } else if (existing.layoutParams.height != height) {
        existing.layoutParams = existing.layoutParams.also { it.height = height }
    }
}

private fun statusBarScrimColor(activity: Activity): Int {
    val value = TypedValue()
    val resolved = activity.theme.resolveAttribute(
        androidx.appcompat.R.attr.colorPrimaryDark, value, true
    )
    return if (resolved && value.data != 0) value.data else android.graphics.Color.BLACK
}
