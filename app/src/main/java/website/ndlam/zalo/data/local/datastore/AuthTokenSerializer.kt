package website.ndlam.zalo.data.local.datastore

import androidx.datastore.core.CorruptionException
import androidx.datastore.core.Serializer
import com.google.protobuf.InvalidProtocolBufferException
import website.ndlam.zalo.data.local.datastore.AuthToken.getDefaultInstance
import website.ndlam.zalo.data.local.datastore.AuthToken.parseFrom
import java.io.InputStream
import java.io.OutputStream

object AuthTokenSerializer : Serializer<AuthToken> {
    override suspend fun readFrom(input: InputStream): AuthToken {
        try {
            return parseFrom(input)
        } catch (exception: InvalidProtocolBufferException) {
            throw CorruptionException("Cannot read proto.", exception)
        }
    }

    override suspend fun writeTo(
        t: AuthToken,
        output: OutputStream
    ) {
        t.writeTo(output)
    }

    override val defaultValue: AuthToken
        get() = getDefaultInstance()
}