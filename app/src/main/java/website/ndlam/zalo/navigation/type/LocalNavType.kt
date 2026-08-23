package website.ndlam.zalo.navigation.type

import androidx.navigation.NavType
import androidx.savedstate.SavedState
import website.ndlam.zalo.core.util.converter.GsonConverter

class LocalNavType<T>(isNullableAllowed: Boolean = true, val clazz: Class<T>) :
    NavType<T>(isNullableAllowed) {
    override fun put(
        bundle: SavedState,
        key: String,
        value: T
    ) {
        bundle.putString(key, GsonConverter.gson.toJson(value))
    }

    override fun get(
        bundle: SavedState,
        key: String
    ): T? {
        val json = bundle.getString(key) ?: return null
        return try {
            GsonConverter.gson.fromJson(json, clazz)
        } catch (_: Exception) {
            null
        }
    }

    override fun parseValue(value: String): T {
        return GsonConverter.gson.fromJson(value, clazz)
    }
}