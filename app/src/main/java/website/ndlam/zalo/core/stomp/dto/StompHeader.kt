package website.ndlam.zalo.core.stomp.dto

data class StompHeader(val key: String, val value: String) {

    companion object {
        const val VERSION: String = "accept-version"

        const val HEART_BEAT: String = "heart-beat"

        const val DESTINATION: String = "destination"

        const val SUBSCRIPTION: String = "subscription"

        const val CONTENT_TYPE: String = "content-type"

        const val MESSAGE_ID: String = "message-id"

        const val ID: String = "id"

        const val ACK: String = "ack"

    }
}