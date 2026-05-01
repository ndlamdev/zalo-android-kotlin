package website.ndlam.zalo.repositories

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.dataStore
import com.google.protobuf.StringValue
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import website.ndlam.zalo.convert.serializer.AuthTokenSerializer
import website.ndlam.zalo.domains.dto.AuthToken

class AuthTokenRepository(private val context: Context) {
    val Context.dataStore: DataStore<AuthToken> by dataStore(
        fileName = "auth.json",
        serializer = AuthTokenSerializer,
    )


//    // Khởi tạo an toàn với Master Key từ Android Keystore
//    val masterKey = KeyGenerator.getInstance("AES256_GCM")
//
//    val keysetHandle = AndroidKeysetManager.Builder()
//        .withSharedPref(context, "master_keyset", "my_pref")
//        .withKeyTemplate(AesGcmKeyManager.aes256GcmTemplate())
//        .withMasterKeyUri("android-keystore://master_key")
//        .build()
//        .keysetHandle


    suspend fun saveAccessToken(token: String?) {
        context.dataStore.updateData { auth ->
            auth.toBuilder().setAccessToken(StringValue.of(token)).build()
        }
    }

    suspend fun saveRefreshToken(token: String?) {
        context.dataStore.updateData { auth ->
            auth.toBuilder().setRefreshToken(StringValue.of(token)).build()
        }
    }

    fun isSignIn(): Flow<Boolean> {
        return context.dataStore.data.map { authToken ->
            !authToken.accessToken.value.isEmpty() && !authToken.refreshToken.value.isEmpty()
        }
    }
}