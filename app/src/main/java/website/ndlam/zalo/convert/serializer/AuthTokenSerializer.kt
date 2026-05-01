package website.ndlam.zalo.convert.serializer

import androidx.datastore.core.CorruptionException
import website.ndlam.zalo.domains.dto.AuthToken
import androidx.datastore.core.Serializer
import com.google.protobuf.InvalidProtocolBufferException
import java.io.InputStream
import java.io.OutputStream

object AuthTokenSerializer : Serializer<AuthToken> {
    override suspend fun readFrom(input: InputStream): AuthToken {
        try {
            return AuthToken.parseFrom(input)
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
        get() = AuthToken.getDefaultInstance()
}