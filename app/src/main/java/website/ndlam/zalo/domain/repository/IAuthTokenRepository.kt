package website.ndlam.zalo.domain.repository

import android.security.keystore.KeyGenParameterSpec
import android.security.keystore.KeyProperties
import android.util.Log
import kotlinx.coroutines.flow.Flow
import java.security.KeyPairGenerator
import java.security.KeyStore

interface IAuthTokenRepository {
    suspend fun saveAccessToken(token: String?)
    suspend fun saveRefreshToken(token: String?)
    fun isSignIn(): Flow<Boolean>
    suspend fun getAccessToken(): String?

    companion object {
        private const val KEY_STORE_ALIAS = "ZolaApp"
        private const val KEY_STORE_TYPE = "AndroidKeyStore"

        fun initKey() {
            val ks = getPrivateKeyEntry()

            if (ks != null) return

            val kpg: KeyPairGenerator = KeyPairGenerator.getInstance(
                KeyProperties.KEY_ALGORITHM_EC,
                KEY_STORE_TYPE
            )
            val parameterSpec: KeyGenParameterSpec = KeyGenParameterSpec.Builder(
                KEY_STORE_ALIAS,
                KeyProperties.PURPOSE_SIGN or KeyProperties.PURPOSE_VERIFY
            ).run {
                setDigests(KeyProperties.DIGEST_SHA256, KeyProperties.DIGEST_SHA512)
                build()
            }

            kpg.initialize(parameterSpec)

            kpg.generateKeyPair()
        }

        fun getPrivateKeyEntry(): KeyStore.PrivateKeyEntry? {
            val ks = KeyStore.getInstance(KEY_STORE_TYPE).apply {
                load(null)
            }

            if (ks == null) return null

            val entry: KeyStore.Entry? = ks.getEntry(KEY_STORE_ALIAS, null)

            if (entry !is KeyStore.PrivateKeyEntry) {
                Log.w(this.javaClass.name, "Not an instance of a PrivateKeyEntry")
                return null
            }

            return entry
        }
    }
}