package com.lamnguyen.zalo.utils.functions

import android.view.Window
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.lamnguyen.zalo.R

fun AppCompatActivity.setStatusBarGradiant() {
    val window: Window = this.window
    val background = ContextCompat.getDrawable(this, R.drawable.bg_header)
    window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
    window.statusBarColor = ContextCompat.getColor(this,android.R.color.transparent)
    window.navigationBarColor = ContextCompat.getColor(this,android.R.color.transparent)
    window.setBackgroundDrawable(background)
}