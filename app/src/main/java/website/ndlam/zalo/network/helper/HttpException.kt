package website.ndlam.zalo.network.helper

import retrofit2.HttpException
import website.ndlam.zalo.core.util.converter.GsonConverter
import website.ndlam.zalo.data.remote.api.ApiResponseError

fun HttpException.getResponseError(): ApiResponseError? {
    val response = this.response() ?: return null

    val errorBody = response.errorBody() ?: return null

    val json = errorBody.string()

    return GsonConverter.gson.fromJson(json, ApiResponseError::class.java)
}