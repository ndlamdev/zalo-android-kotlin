package website.ndlam.zalo.data.repository

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.dataStore
import com.google.protobuf.StringValue
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.map
import website.ndlam.zalo.data.local.datastore.AuthToken
import website.ndlam.zalo.data.local.datastore.AuthTokenSerializer
import website.ndlam.zalo.domain.repository.IAuthTokenRepository
import java.util.Base64
import javax.crypto.Cipher
import javax.crypto.SecretKey
import javax.crypto.spec.GCMParameterSpec

private val Context.dataStore: DataStore<AuthToken> by dataStore(
    fileName = "auth.pb",
    serializer = AuthTokenSerializer,
)

class AuthTokenRepositoryImpl(private val context: Context) : IAuthTokenRepository {
    private val secretKey: SecretKey? = IAuthTokenRepository.getSecretKey()
    private val cipher: Cipher = Cipher.getInstance(IAuthTokenRepository.TRANSFORMATION)

    override suspend fun saveAccessToken(token: String?) {
        if (token == null) return

        val encryptedToken = encrypt(token)

        this.context.dataStore.updateData { auth ->
            auth.toBuilder().setAccessToken(StringValue.of(encryptedToken)).build()
        }
    }

    override suspend fun saveRefreshToken(token: String?) {
        if (token == null) return

        val encryptedToken = encrypt(token)

        context.dataStore.updateData { auth ->
            auth.toBuilder().setRefreshToken(StringValue.of(encryptedToken)).build()
        }
    }

    override fun isSignIn(): Flow<Boolean> {
        return context.dataStore.data.map { authToken ->
            !authToken.accessToken.value.isEmpty() && !authToken.refreshToken.value.isEmpty()
        }
    }

    override suspend fun getAccessToken(): String? {
        val token = context.dataStore.data.map { it.accessToken.value }.firstOrNull()

        return if (token.isNullOrEmpty()) null else decrypt(token)
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
}