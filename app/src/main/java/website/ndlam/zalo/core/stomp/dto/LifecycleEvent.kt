package website.ndlam.zalo.core.stomp.dto

import java.util.TreeMap


class LifecycleEvent(
    val type: Type?,
    val exception: Exception? = null,
    val message: String? = null,
    var handshakeResponseHeaders: TreeMap<String?, String?>? = TreeMap<String?, String?>()
) {
    enum class Type {
        OPENED, CLOSED, ERROR, FAILED_SERVER_HEARTBEAT
    }

    constructor(type: Type?, exception: Exception?) : this(type, exception, null)

    constructor(type: Type?, message: String?) : this(type, null, message)
}