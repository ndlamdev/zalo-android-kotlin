package website.ndlam.zalo.data.repository

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.dataStore
import com.google.protobuf.StringValue
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.map
import website.ndlam.zalo.data.local.datastore.AuthTokenSerializer
import website.ndlam.zalo.domain.model.AuthToken
import website.ndlam.zalo.domain.repository.IAuthTokenRepository
import java.security.KeyStore
import java.util.Base64
import javax.crypto.Cipher

private val Context.dataStore: DataStore<AuthToken> by dataStore(
    fileName = "auth.json",
    serializer = AuthTokenSerializer,
)

class AuthTokenRepositoryImpl : IAuthTokenRepository {
    private var privateKeyEntry: KeyStore.PrivateKeyEntry? = null
    private var cipher: Cipher
    private val transformation = "RSA/ECB/OAEPPadding"
    private val context: Context


    constructor(context: Context) {
        this.context = context

        this.cipher = Cipher.getInstance(transformation)

        this.privateKeyEntry = IAuthTokenRepository.getPrivateKeyEntry()
    }

    override suspend fun saveAccessToken(token: String?) {
        if (token == null) return

        val token = encrypt(token)

        this.context.dataStore.updateData { auth ->
            auth.toBuilder().setAccessToken(StringValue.of(token)).build()
        }
    }

    override suspend fun saveRefreshToken(token: String?) {
        if (token == null) return

        val token = encrypt(token)

        context.dataStore.updateData { auth ->
            auth.toBuilder().setRefreshToken(StringValue.of(token)).build()
        }
    }

    override fun isSignIn(): Flow<Boolean> {
        return context.dataStore.data.map { authToken ->
            !authToken.accessToken.value.isEmpty() && !authToken.refreshToken.value.isEmpty()
        }
    }

    override suspend fun getAccessToken(): String? {
        val token = context.dataStore.data.map { it.accessToken.value }.firstOrNull()

        return if (token == null) null else decrypt(token)
    }

    private fun encrypt(data: String): String {
        this.cipher.init(Cipher.ENCRYPT_MODE, this.privateKeyEntry?.certificate?.publicKey)
        val encryptedData = this.cipher.doFinal(data.toByteArray())

        return Base64.getEncoder().encodeToString(encryptedData)
    }

    private fun decrypt(data: String): String {
        this.cipher.init(Cipher.ENCRYPT_MODE, this.privateKeyEntry?.privateKey)
        val bytesData = Base64.getDecoder().decode(data)

        val byteDecrypt = this.cipher.doFinal(bytesData)

        return byteDecrypt.decodeToString()
    }
}