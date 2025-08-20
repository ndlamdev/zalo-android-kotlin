package com.lamnguyen.zalo.utils.helpers

import android.content.Context
import android.util.Log
import android.widget.Toast
import com.lamnguyen.zalo.configs.RetrofitClient
import retrofit2.HttpException

class LogHelper {
    companion object {
        @JvmStatic
        fun infoWithClassName(any: Any, message: String): Int {
            return Log.i(any::class.java.name, message)
        }

        @JvmStatic
        fun errorWithClassName(any: Any, t: Throwable?, message: String? = "Error"): Int {
            return Log.e(any::class.java.name, t?.message ?: message, t)
        }

        @JvmStatic
        fun showToastError(context: Context?, e: Exception, duration: Int? = Toast.LENGTH_SHORT) {
            Toast.makeText(context, e.message ?: "Lỗi hệ thống", duration ?: Toast.LENGTH_SHORT)
                .show()
        }

        @JvmStatic
        fun showToastApiResponseError(
            context: Context?,
            e: HttpException,
            duration: Int? = Toast.LENGTH_SHORT,
        ) {
            val apiResponseError = RetrofitClient.convertToResponseError(e)
            Toast.makeText(context, apiResponseError.error, duration ?: Toast.LENGTH_SHORT)
                .show()
        }
    }
}