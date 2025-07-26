package com.lamnguyen.zalo.utils.helpers

import android.util.Log

class LogHelper {
    companion object {
        @JvmStatic
        fun infoWithClassName(any: Any, message: String): Int {
            return Log.i(any::class.java.name, message)
        }

        @JvmStatic
        fun errorWithClassName(any: Any, message: String, t: Throwable?): Int {
            return Log.e(any::class.java.name, message, t)
        }
    }
}