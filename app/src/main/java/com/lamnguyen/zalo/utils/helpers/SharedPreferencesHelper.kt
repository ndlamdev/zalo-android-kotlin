package com.lamnguyen.zalo.utils.helpers

import android.content.Context
import android.content.Context.MODE_PRIVATE
import androidx.core.content.edit
import com.lamnguyen.zalo.ui.welcome.WelcomeActivity

fun pastWelcomeActivity(context: Context) {
    context.getSharedPreferences(WelcomeActivity::javaClass.name, MODE_PRIVATE)
        .edit(true) {
            putBoolean("past", true)
        }
}

fun isPastWelcomeActivity(context: Context): Boolean {
    return context.getSharedPreferences(WelcomeActivity::javaClass.name, MODE_PRIVATE)
        .getBoolean("past", false)
}