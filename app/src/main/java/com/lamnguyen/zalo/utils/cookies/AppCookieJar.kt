package com.lamnguyen.zalo.utils.cookies

import android.annotation.SuppressLint
import android.content.Context
import com.lamnguyen.zalo.repositories.AppDatabase
import com.lamnguyen.zalo.repositories.CookieRepository
import okhttp3.Cookie
import okhttp3.CookieJar
import okhttp3.HttpUrl

class AppCookieJar : CookieJar {
    private val cookeRepository: CookieRepository

    private constructor(context: Context) {
        this.cookeRepository = AppDatabase.getDatabase(context).cookieRepository()
    }

    override fun saveFromResponse(url: HttpUrl, cookies: List<Cookie>) {
        cookies.forEach {
            cookeRepository.deleteAllByUrlAndName(
                urlKey(url), it.name
            )
            cookeRepository.insert(
                com.lamnguyen.zalo.entities.Cookie.parse(
                    urlKey(url),
                    it
                )
            )
        }
    }

    override fun loadForRequest(url: HttpUrl): List<Cookie> {
        val result = cookeRepository.findAllByUrl(urlKey(url))
            .mapNotNull {
                val cookie = Cookie.parse(url, it.setCookie)
                return@mapNotNull cookie
            }

        return result
    }

    private fun urlKey(url: HttpUrl): String {
        return "${url.scheme}://${url.host}:${url.port}"
    }

    companion object {
        @SuppressLint("StaticFieldLeak")
        private var INSTANCE: AppCookieJar? = null

        @JvmStatic
        fun getInstance(context: Context): AppCookieJar {
            return INSTANCE ?: synchronized(this) {
                val instance = AppCookieJar(context)
                INSTANCE = AppCookieJar(context)
                instance
            }
        }
    }
}