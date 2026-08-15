package website.ndlam.zalo.core.stomp.dto

import website.ndlam.zalo.core.stomp.enums.StompCommand
import java.io.StringReader
import java.util.Scanner
import java.util.regex.Pattern


class StompMessage(
    val command: StompCommand, val headers: List<StompHeader>?, val payload: String?
) {
    fun findHeader(key: String?): String? {
        if (headers == null) return null
        for (header in headers) {
            if (header.key == key) return header.value
        }
        return null
    }

    fun compile(): String {
        return compile(false)
    }

    fun compile(legacyWhitespace: Boolean): String {
        val builder = StringBuilder()
        builder.append(command).append('\n')
        headers?.forEach { header ->
            builder.append(header.key).append(':').append(header.value).append('\n')
        }
        builder.append('\n')
        if (!payload.isNullOrBlank()) {
            builder.append(payload)
            if (legacyWhitespace) builder.append("\n\n")
        }
        builder.append(TERMINATE_MESSAGE_SYMBOL)
        return builder.toString()
    }

    companion object {
        const val TERMINATE_MESSAGE_SYMBOL: String = "\u0000"

        private val PATTERN_HEADER: Pattern? = Pattern.compile("([^:\\s]+)\\s*:\\s*([^:\\s]+)")

        @JvmStatic
        fun from(data: String?): StompMessage {
            if (data == null || data.trim { it <= ' ' }.isEmpty()) {
                return StompMessage(StompCommand.UNKNOWN, null, data)
            }
            val reader = Scanner(StringReader(data))
            reader.useDelimiter("\\n")
            val command = reader.next()
            val headers: MutableList<StompHeader> = ArrayList()

            while (reader.hasNext(PATTERN_HEADER)) {
                val matcher = PATTERN_HEADER!!.matcher(reader.next())
                matcher.find()
                val key = matcher.group(1)
                val value = matcher.group(2)
                if (key.isNullOrBlank() || value.isNullOrBlank()) continue
                headers.add(StompHeader(key, value))
            }

            reader.skip("\n\n")

            reader.useDelimiter(TERMINATE_MESSAGE_SYMBOL)
            val payload = if (reader.hasNext()) reader.next() else null

            return StompMessage(StompCommand.valueOf(command), headers, payload)
        }
    }
}