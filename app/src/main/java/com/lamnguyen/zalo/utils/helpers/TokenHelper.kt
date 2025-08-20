package com.lamnguyen.zalo.utils.helpers

import android.content.Context
import android.security.keystore.KeyGenParameterSpec
import android.security.keystore.KeyProperties
import android.util.Base64
import androidx.core.content.edit
import com.fasterxml.jackson.databind.ObjectMapper
import com.lamnguyen.zalo.domain.dtos.AccessTokenPayload
import com.lamnguyen.zalo.utils.enums.SharedPreferenceNames
import java.security.KeyStore
import javax.crypto.Cipher
import javax.crypto.KeyGenerator
import javax.crypto.SecretKey
import javax.crypto.spec.GCMParameterSpec

object TokenHelper {
    private const val ANDROID_KEYSTORE = "AndroidKeyStore"
    private const val ALGORITHM = "AES"
    private const val TRANSFORMATION = "AES/GCM/NoPadding"
    private const val TAG_LENGTH = 128 // GCM tag length
    private const val JWT_ALIAS = "JWT_ALIAS"

    init {
        createKey(JWT_ALIAS)
    }

    /**
     * Gọi 1 lần để tạo khóa với alias
     */
    fun createKey(alias: String) {
        val keyStore = KeyStore.getInstance(ANDROID_KEYSTORE).apply { load(null) }

        // Nếu đã tồn tại thì bỏ qua
        if (keyStore.containsAlias(alias)) return

        val keyGenerator = KeyGenerator.getInstance(ALGORITHM, ANDROID_KEYSTORE)
        val keyGenParameterSpec = KeyGenParameterSpec.Builder(
            alias,
            KeyProperties.PURPOSE_ENCRYPT or KeyProperties.PURPOSE_DECRYPT
        )
            .setBlockModes(KeyProperties.BLOCK_MODE_GCM)
            .setEncryptionPaddings(KeyProperties.ENCRYPTION_PADDING_NONE)
            .build()

        keyGenerator.init(keyGenParameterSpec)
        keyGenerator.generateKey()
    }

    /**
     * Mã hóa dữ liệu, trả về Base64(iv):Base64(cipher)
     */
    private fun encrypt(alias: String, plainText: String): String {
        val secretKey = getSecretKey(alias)
        val cipher = Cipher.getInstance(TRANSFORMATION)
        cipher.init(Cipher.ENCRYPT_MODE, secretKey)

        val iv = cipher.iv
        val encryptedBytes = cipher.doFinal(plainText.toByteArray(Charsets.UTF_8))

        val ivBase64 = Base64.encodeToString(iv, Base64.NO_WRAP)
        val encryptedBase64 = Base64.encodeToString(encryptedBytes, Base64.NO_WRAP)

        return "$ivBase64:$encryptedBase64"
    }

    /**
     * Giải mã dữ liệu từ chuỗi Base64(iv):Base64(cipher)
     */
    private fun decrypt(alias: String, encryptedInput: String): String {
        val secretKey = getSecretKey(alias)
        val parts = encryptedInput.split(":")
        if (parts.size != 2) throw IllegalArgumentException("Invalid encrypted input format")

        val iv = Base64.decode(parts[0], Base64.NO_WRAP)
        val encryptedBytes = Base64.decode(parts[1], Base64.NO_WRAP)

        val cipher = Cipher.getInstance(TRANSFORMATION)
        val spec = GCMParameterSpec(TAG_LENGTH, iv)
        cipher.init(Cipher.DECRYPT_MODE, secretKey, spec)

        val decryptedBytes = cipher.doFinal(encryptedBytes)
        return String(decryptedBytes, Charsets.UTF_8)
    }

    private fun getSecretKey(alias: String): SecretKey {
        val keyStore = KeyStore.getInstance(ANDROID_KEYSTORE).apply { load(null) }
        val secretKey = keyStore.getKey(alias, null)
            ?: throw IllegalStateException("Key with alias $alias not found")
        return secretKey as SecretKey
    }

    fun saveAccessToken(token: String?, context: Context) {
        if (token == null) {
            cleanAccessToken(context)
            return
        }

        context.getSharedPreferences(
            SharedPreferenceNames.AUTHENTICATION.name,
            Context.MODE_PRIVATE
        )
            .edit(true) {
                putString("ACCESS_TOKEN", encrypt(JWT_ALIAS, token))
            }
    }

    fun getAccessToken(context: Context): String? {
        val tokenEncrypt = context.getSharedPreferences(
            SharedPreferenceNames.AUTHENTICATION.name,
            Context.MODE_PRIVATE
        ).getString("ACCESS_TOKEN", "")
        if (tokenEncrypt.isNullOrEmpty()) return null
        return decrypt(JWT_ALIAS, tokenEncrypt)
    }

    fun cleanAccessToken(context: Context?) {
        context?.getSharedPreferences(
            SharedPreferenceNames.AUTHENTICATION.name,
            Context.MODE_PRIVATE
        )?.edit(true) {
            remove("ACCESS_TOKEN")
        }
    }

    fun getAccessTokenPayload(context: Context): AccessTokenPayload? {
        return getAccessToken(context)?.let {
            val bodyTokenEncode = it.split(".")[1]
            val bodyTokenString = Base64.decode(bodyTokenEncode, Base64.NO_WRAP)
            return ObjectMapper().convertValue(bodyTokenString, AccessTokenPayload::class.java)
        }
    }
}