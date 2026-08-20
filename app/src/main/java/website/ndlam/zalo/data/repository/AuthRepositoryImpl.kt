package website.ndlam.zalo.data.repository

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.dataStore
import com.google.protobuf.StringValue
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.map
import retrofit2.Response
import website.ndlam.zalo.data.local.datastore.AuthToken
import website.ndlam.zalo.data.local.datastore.AuthTokenSerializer
import website.ndlam.zalo.data.remote.api.ApiResponseSuccess
import website.ndlam.zalo.data.remote.api.LoginInfoResponse
import website.ndlam.zalo.domain.repository.IAuthRepository
import website.ndlam.zalo.network.helper.detectRefreshTokenFromCookie
import website.ndlam.zalo.network.helper.getCookieRefreshToken
import java.util.Base64
import javax.crypto.Cipher
import javax.crypto.SecretKey
import javax.crypto.spec.GCMParameterSpec

private val Context.dataStore: DataStore<AuthToken> by dataStore(
    fileName = "auth.pb",
    serializer = AuthTokenSerializer,
)

class AuthRepositoryImpl(
    private val context: Context
) : IAuthRepository {
    private val secretKey: SecretKey? = IAuthRepository.getSecretKey()
    private val cipher: Cipher = Cipher.getInstance(IAuthRepository.TRANSFORMATION)

    override suspend fun saveAccessToken(token: String?) {
        if (token.isNullOrBlank()) return

        val encryptedToken = encrypt(token)

        this.context.dataStore.updateData { auth ->
            auth.toBuilder().setAccessToken(StringValue.of(encryptedToken)).build()
        }
    }

    override suspend fun saveRefreshToken(token: String?) {
        if (token.isNullOrBlank()) return

        val encryptedToken = encrypt(token)

        context.dataStore.updateData { auth ->
            auth.toBuilder().setRefreshToken(StringValue.of(encryptedToken)).build()
        }
    }

    override suspend fun getAccessToken(): String? {
        val token = context.dataStore.data.map { it.accessToken.value }.firstOrNull()

        return if (token.isNullOrEmpty()) null else decrypt(token)
    }

    override suspend fun getRefreshToken(): String? {
        val token = context.dataStore.data.map { it.refreshToken.value }.firstOrNull()

        return if (token.isNullOrEmpty()) null else decrypt(token)
    }

    override suspend fun getCookieRefreshToken(): String? {
        val cookie = context.dataStore.data.map { it.cookieRefreshToken.value }.firstOrNull()

        return if (cookie.isNullOrEmpty()) null else decrypt(cookie)
    }

    private fun encrypt(data: String): String {
        val key = secretKey ?: throw IllegalStateException("Secret key not initialized")
        this.cipher.init(Cipher.ENCRYPT_MODE, key)
        val iv = this.cipher.iv ?: throw IllegalStateException("IV not generated")
        val encryptedData = this.cipher.doFinal(data.toByteArray())

        // Combine IV and encrypted data
        val combined = iv + encryptedData
        return Base64.getEncoder().encodeToString(combined)
    }

    private fun decrypt(data: String): String {
        val key = secretKey ?: throw IllegalStateException("Secret key not initialized")
        val combined = Base64.getDecoder().decode(data)

        // GCM IV is 12 bytes
        if (combined.size < 12) throw IllegalArgumentException("Invalid encrypted data")

        val iv = combined.sliceArray(0 until 12)
        val encryptedData = combined.sliceArray(12 until combined.size)

        val spec = GCMParameterSpec(128, iv)
        this.cipher.init(Cipher.DECRYPT_MODE, key, spec)
        val decryptedData = this.cipher.doFinal(encryptedData)

        return decryptedData.decodeToString()
    }

    override suspend fun savePhoneNumber(number: String) {
        context.dataStore.updateData { auth ->
            auth.toBuilder().setLastInputSwissNumber(StringValue.of(number)).build()
        }
    }

    override suspend fun saveRegion(code: String) {
        context.dataStore.updateData { auth ->
            auth.toBuilder().setLastInputRegionCode(StringValue.of(code)).build()
        }
    }

    override suspend fun getPhoneNumber(): String? {
        return context.dataStore.data.map { it.lastInputSwissNumber.value }.firstOrNull()
    }

    override suspend fun getRegionCode(): String? {
        return context.dataStore.data.map { it.lastInputRegionCode.value }.firstOrNull()
    }

    override suspend fun clear() {
        this.context.dataStore.updateData { auth ->
            auth.toBuilder().clear().build()
            auth.toBuilder().clear().build()
        }
    }

    override suspend fun saveAuthInfo(info: LoginInfoResponse, cookie: String): Boolean {
        val refreshToken = detectRefreshTokenFromCookie(cookie) ?: return false
        val accessTokenEncrypted = encrypt(info.token)
        val refreshTokenEncrypted = encrypt(refreshToken)
        val cookieEncrypted = encrypt(cookie)

        this.context.dataStore.updateData { auth ->
            auth.toBuilder()
                .setRefreshToken(StringValue.of(refreshTokenEncrypted))
                .setAccessToken(StringValue.of(accessTokenEncrypted))
                .setCookieRefreshToken(StringValue.of(cookieEncrypted))
                .setLastInputRegionCode(StringValue.of(info.phoneNumberCode))
                .setLastInputSwissNumber(StringValue.of(info.phoneNumber))
                .build()
        }

        return true
    }

    override suspend fun saveLoginInfo(loginResponse: Response<ApiResponseSuccess<LoginInfoResponse>>): Boolean {
        val cookie: String = loginResponse.getCookieRefreshToken() ?: return false
        val body = loginResponse.body() ?: return false
        return saveAuthInfo(body.data, cookie)
    }
}