package website.ndlam.zalo.domain.repository

import android.security.keystore.KeyGenParameterSpec
import android.security.keystore.KeyProperties
import retrofit2.Response
import website.ndlam.zalo.data.remote.api.ApiResponseSuccess
import website.ndlam.zalo.data.remote.api.LoginInfoResponse
import java.security.KeyStore
import javax.crypto.KeyGenerator
import javax.crypto.SecretKey

interface IAuthRepository {
    suspend fun getAccessToken(): String?
    suspend fun getRefreshToken(): String?
    suspend fun getCookieRefreshToken(): String?
    suspend fun getPhoneNumber(): String?
    suspend fun getRegionCode(): String?

    suspend fun saveAccessToken(token: String?)
    suspend fun saveRefreshToken(token: String?)
    suspend fun savePhoneNumber(number: String)
    suspend fun saveRegion(code: String)

    suspend fun clear()
    suspend fun saveAuthInfo(info: LoginInfoResponse, cookie: String): Boolean
    suspend fun saveLoginInfo(loginResponse: Response<ApiResponseSuccess<LoginInfoResponse>>): Boolean


    companion object {
        const val TRANSFORMATION = "AES/GCM/NoPadding"
        private const val KEY_STORE_ALIAS = "ZolaApp"
        private const val KEY_STORE_TYPE = "AndroidKeyStore"

        fun initKey() {
            if (getSecretKey() != null) return

            val kg: KeyGenerator = KeyGenerator.getInstance(
                KeyProperties.KEY_ALGORITHM_AES,
                KEY_STORE_TYPE
            )
            val parameterSpec: KeyGenParameterSpec = KeyGenParameterSpec.Builder(
                KEY_STORE_ALIAS,
                KeyProperties.PURPOSE_ENCRYPT or KeyProperties.PURPOSE_DECRYPT
            ).run {
                setBlockModes(KeyProperties.BLOCK_MODE_GCM)
                setEncryptionPaddings(KeyProperties.ENCRYPTION_PADDING_NONE)
                setKeySize(256)
                build()
            }

            kg.init(parameterSpec)
            kg.generateKey()
        }

        fun getSecretKey(): SecretKey? {
            val ks = KeyStore.getInstance(KEY_STORE_TYPE).apply {
                load(null)
            }

            return ks.getKey(KEY_STORE_ALIAS, null) as? SecretKey
        }
    }
}