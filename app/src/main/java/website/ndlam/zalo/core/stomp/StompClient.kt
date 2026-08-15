package website.ndlam.zalo.core.stomp

import android.util.Log
import kotlinx.coroutines.Job
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import website.ndlam.zalo.core.stomp.dto.LifecycleEvent
import website.ndlam.zalo.core.stomp.dto.StompHeader
import website.ndlam.zalo.core.stomp.dto.StompMessage
import website.ndlam.zalo.core.stomp.enums.StompCommand
import website.ndlam.zalo.core.stomp.matcher.IPathMatcher
import website.ndlam.zalo.core.stomp.matcher.SimplePathMatcher
import website.ndlam.zalo.core.stomp.provider.IConnectionProvider
import java.util.UUID
import java.util.concurrent.ConcurrentHashMap


class StompClient {
    private var connectionProvider: IConnectionProvider? = null
    private var topics: ConcurrentHashMap<String, String>? = null
    private var legacyWhitespace = false

    private var messageStream: MutableSharedFlow<StompMessage?>? = null
    private var connectionStream: MutableStateFlow<Boolean>? = null

    /**
     * Map save SharedFlow Message by destPath
     */
    private var streamMap: ConcurrentHashMap<String, SharedFlow<StompMessage?>> =
        ConcurrentHashMap<String, SharedFlow<StompMessage?>>()
    private var pathMatcher: IPathMatcher = SimplePathMatcher()
    private var lifecycleJob: Job? = null
    private var messagesJob: Job? = null
    private var lifecyclePublishSubject: MutableSharedFlow<LifecycleEvent?> =
        MutableSharedFlow(0, 0)
    private var headers: MutableList<StompHeader>? = null

    constructor(connectionProvider: IConnectionProvider) {
        this.connectionProvider = connectionProvider
    }

    /**
     * Connect without reconnect if connected
     */
    suspend fun connect() {
        connect(null)
    }

    suspend fun connect(headers: Map<String, String>) {
        val stompHeaders =
            headers.entries.map { entry -> StompHeader(entry.key, entry.value) }.toList()
        connect(stompHeaders)
    }

    /**
     * Connect to websocket. If already connected, this will silently fail.
     * 
     * @param headers HTTP headers to send in the INITIAL REQUEST, i.e. during the protocol upgrade
     */
    suspend fun connect(headers: List<StompHeader>?) {
        if (connectionProvider == null) return
        Log.d(TAG, "Connect")

        this.headers = headers?.toMutableList()

        if (isConnected()) {
            Log.d(TAG, "Already connected, ignore")
            return
        }

        coroutineScope {
            lifecycleJob = launch {
                connectionProvider!!.lifecycle()
                    .collect { lifecycleEvent ->
                        when (lifecycleEvent?.type) {
                            LifecycleEvent.Type.OPENED -> {
                                val currentHeaders: MutableList<StompHeader> = ArrayList()
                                currentHeaders.add(
                                    StompHeader(
                                        StompHeader.VERSION,
                                        SUPPORTED_VERSIONS
                                    )
                                )

                                if (headers != null) currentHeaders.addAll(headers)

                                val message = StompMessage(
                                    StompCommand.CONNECT,
                                    currentHeaders,
                                    null
                                ).compile(legacyWhitespace)

                                connectionProvider!!.send(message)
                                Log.d(TAG, "Publish open")
                                lifecyclePublishSubject.emit(lifecycleEvent)
                            }

                            LifecycleEvent.Type.CLOSED -> {
                                Log.d(TAG, "Socket closed")
                                disconnect()
                            }

                            LifecycleEvent.Type.ERROR -> {
                                Log.d(TAG, "Socket closed with error")
                                lifecyclePublishSubject.emit(lifecycleEvent)
                            }

                            else -> {}
                        }
                    }
            }
            messagesJob = launch {
                connectionProvider!!.messages()
                    .map(StompMessage::from)
                    .onEach(getMessageStream()::emit)
                    .filter { msg -> msg.command == StompCommand.CONNECTED }
                    .collect { getConnectionStream().emit(true) }
            }
        }


    }


    suspend fun send(destination: String) {
        return send(destination, null)
    }

    suspend fun send(destination: String, data: String?) {
        val message = StompMessage(
            StompCommand.SEND,
            mutableListOf(StompHeader(StompHeader.DESTINATION, destination)),
            data
        )

        return send(message)
    }

    suspend fun send(stompMessage: StompMessage) {
        connectionProvider?.send(stompMessage.compile(legacyWhitespace))
    }

    fun lifecycle(): Flow<LifecycleEvent?> {
        return lifecyclePublishSubject
    }

    /**
     * Disconnect from server, and then reconnect with the last-used headers
     */
    suspend fun reconnect() {
        try {
            disconnectCompletable()
            connect(headers)
        } catch (e: Exception) {
            Log.e(TAG, "Disconnect error", e)
        }
    }

    suspend fun disconnect() {
        try {
            disconnectCompletable()
        } catch (e: Exception) {
            Log.e(TAG, "Disconnect error", e)
        }
    }

    suspend fun disconnectCompletable() {
        if (lifecycleJob != null) {
            lifecycleJob?.cancel()
        }
        if (messagesJob != null) {
            messagesJob?.cancel()
        }

        connectionProvider?.disconnect()

        Log.d(TAG, "Stomp disconnected")
        lifecyclePublishSubject.emit(LifecycleEvent(LifecycleEvent.Type.CLOSED))
    }

    suspend fun topic(destinationPath: String?): SharedFlow<StompMessage?>? {
        return topic(destinationPath, null)
    }

    suspend fun topic(
        destPath: String?,
        headerList: MutableList<StompHeader>?
    ): SharedFlow<StompMessage?>? {
        if (destPath == null) throw IllegalArgumentException("Topic path cannot be null")
        if (!streamMap.containsKey(destPath)) {
            subscribePath(destPath, headerList)
            val data = getMessageStream()
                .filter { msg -> pathMatcher.matches(destPath, msg) }
                .onCompletion { unsubscribePath(destPath) }

            streamMap[destPath] = data as SharedFlow<StompMessage?>
        }



        return streamMap[destPath]
    }


    /**
     * Set the wildcard or other matcher for Topic subscription.
     *
     *
     * Right now, the only options are simple, rmq supported.
     * But you can write you own matcher by implementing [IPathMatcher]
     *
     *
     * When set to [website.ndlam.zalo.core.stomp.matcher.RabbitPathMatcher], topic subscription allows for RMQ-style wildcards.
     *
     *
     *
     * @param pathMatcher Set to [website.ndlam.zalo.core.stomp.matcher.SimplePathMatcher] by default
     */
    fun setPathMatcher(pathMatcher: IPathMatcher) {
        this.pathMatcher = pathMatcher
    }

    suspend fun isConnected(): Boolean {
        return getConnectionStream().first()
    }

    /**
     * Reverts to the old frame formatting, which included two newlines between the message body
     * and the end-of-frame marker.
     *
     *
     * Legacy: Body\n\n^@
     *
     *
     * Default: Body^@
     *
     * @param legacyWhitespace whether to append an extra two newlines
     * @see [The STOMP spec](http://stomp.github.io/stomp-specification-1.2.html.STOMP_Frames)
     */
    fun setLegacyWhitespace(legacyWhitespace: Boolean) {
        this.legacyWhitespace = legacyWhitespace
    }

    /** returns the to topic (subscription id) corresponding to a given destination
     * @param dest the destination
     * @return the topic (subscription id) or null if no topic corresponds to the destination
     */
    fun getTopicId(dest: String?): String? {
        return topics!![dest]
    }

    private suspend fun subscribePath(
        destinationPath: String,
        headerList: MutableList<StompHeader>?
    ) {
        val topicId = UUID.randomUUID().toString()

        if (topics == null) topics = ConcurrentHashMap<String, String>()

        // Only continue if we don't already have a subscription to the topic
        if (topics!!.containsKey(destinationPath)) {
            Log.d(TAG, "Attempted to subscribe to already-subscribed path!")
            return
        }

        topics!![destinationPath] = topicId
        val headers: MutableList<StompHeader> = ArrayList()
        headers.add(StompHeader(StompHeader.ID, topicId))
        headers.add(StompHeader(StompHeader.DESTINATION, destinationPath))
        headers.add(StompHeader(StompHeader.ACK, DEFAULT_ACK))
        if (headerList != null) headers.addAll(headerList)

        val message = StompMessage(
            StompCommand.SUBSCRIBE,
            headers,
            null
        )

        try {
            send(message)
        } catch (_: Exception) {
            unsubscribePath(destinationPath)
        }
    }


    private suspend fun unsubscribePath(dest: String) {
        streamMap.remove(dest)

        val topicId = topics!![dest] ?: return

        topics!!.remove(dest)

        Log.d(TAG, "Unsubscribe path: $dest id: $topicId")
        val message = StompMessage(
            StompCommand.UNSUBSCRIBE,
            mutableListOf(StompHeader(StompHeader.ID, topicId)),
            null
        )

        try {
            send(message)
        } catch (_: Exception) {
        }
    }

    @Synchronized
    private fun getConnectionStream(): MutableStateFlow<Boolean> {
        if (connectionStream == null) {
            connectionStream = MutableStateFlow(false)
        }

        return connectionStream!!
    }

    @Synchronized
    private fun getMessageStream(): MutableSharedFlow<StompMessage?> {
        if (messageStream == null || messagesJob?.isCompleted == true) {
            messageStream = MutableSharedFlow(0, 0)
        }
        return messageStream!!
    }

    companion object {
        private val TAG: String = StompClient::class.java.simpleName


        const val SUPPORTED_VERSIONS: String = "1.1,1.2"

        const val DEFAULT_ACK: String = "auto"
    }
}