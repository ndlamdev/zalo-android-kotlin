package com.lamnguyen.zalo.utils.cookies

import okhttp3.Cookie
import okhttp3.CookieJar
import okhttp3.HttpUrl

class MyCookieJar : CookieJar {
    private val cookieStore: MutableMap<HttpUrl, List<Cookie>> = mutableMapOf()

    private constructor()

    override fun saveFromResponse(url: HttpUrl, cookies: List<Cookie>) {
        cookieStore[url] = cookies
    }

    override fun loadForRequest(url: HttpUrl): List<Cookie> {
        return cookieStore[url] ?: emptyList()
    }

    companion object {
        private var INSTANCE: MyCookieJar = MyCookieJar()

        @JvmStatic
        fun getInstance(): MyCookieJar {
            return INSTANCE
        }
    }
}