package com.lamnguyen.zalo.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import okhttp3.Cookie

@Entity(tableName = "cookies")
data class Cookie(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    @ColumnInfo(name = "url") val url: String,
    @ColumnInfo(name = "name") val name: String,
    @ColumnInfo(name = "value") val value: String,
    @ColumnInfo(name = "expires_at") val expiresAt: Long,
    @ColumnInfo(name = "domain") val domain: String,
    @ColumnInfo(name = "path") val path: String,
    @ColumnInfo(name = "secure") val secure: Boolean,
    @ColumnInfo(name = "http_only") val httpOnly: Boolean,
    @ColumnInfo(name = "persistent") val persistent: Boolean,
    @ColumnInfo(name = "host_only") val hostOnly: Boolean,
    @ColumnInfo(name = "set_cookie") val setCookie: String
) {
    companion object {
        fun parse(url: String, cookie: Cookie): com.lamnguyen.zalo.entities.Cookie {
            return Cookie(
                0,
                url,
                cookie.name,
                cookie.value,
                cookie.expiresAt,
                cookie.domain,
                cookie.path,
                cookie.secure,
                cookie.httpOnly,
                cookie.persistent,
                cookie.hostOnly,
                cookie.toString()
            )
        }
    }
}