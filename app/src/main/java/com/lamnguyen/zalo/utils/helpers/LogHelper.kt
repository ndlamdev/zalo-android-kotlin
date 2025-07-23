package com.lamnguyen.zalo.utils.helpers

import android.util.Log

class LogHelper {
    companion object {
        @JvmStatic
        fun infoWithClassName(any: Any, message: String): Int{
            return Log.i(any::class.java.name, message)
        }
    }
}