package website.ndlam.zalo.network.helper

import retrofit2.Response
import website.ndlam.zalo.BuildConfig

fun Response<*>.getRefreshToken(): String? {
    val cookieStr = this.getCookieRefreshToken() ?: return null

    return detectRefreshTokenFromCookie(cookieStr)
}

fun detectRefreshTokenFromCookie(cookie: String?): String? {
    if (cookie == null || !cookie.startsWith(BuildConfig.REFRESH_TOKEN_COOKIE)) return null
    val data = cookie.split(";")
    return data[0].substring(BuildConfig.REFRESH_TOKEN_COOKIE.length)
}

fun Response<*>.getCookieRefreshToken(): String? {
    val cookies = this.headers().values("set-cookie")

    for (cookie in cookies) {
        if (cookie.startsWith(BuildConfig.REFRESH_TOKEN_COOKIE)) {

            return cookie
        }
    }

    return null
}