package website.ndlam.zalo.core.util.converter

import com.google.gson.FieldNamingPolicy
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import retrofit2.converter.gson.GsonConverterFactory
import website.ndlam.zalo.core.util.converter.adapter.LocalDateTimeAdapter
import java.nio.charset.StandardCharsets.UTF_8
import java.time.LocalDateTime

object GsonConverter {
    val gson: Gson = GsonBuilder()
        .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
        .registerTypeAdapter(LocalDateTime::class.java, LocalDateTimeAdapter())
        .create()

    val converter: GsonConverterFactory = GsonConverterFactory.create(gson)
}

inline fun <reified T> GsonConverter.convert(byteArray: ByteArray?): T? {
    if (byteArray == null) return null
    return try {
        this.gson.fromJson<T>(String(byteArray, UTF_8), T::class.java)
    } catch (_: Exception) {
        null
    }
}