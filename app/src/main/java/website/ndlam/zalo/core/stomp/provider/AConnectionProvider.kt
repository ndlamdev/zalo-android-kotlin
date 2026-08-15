package website.ndlam.zalo.core.stomp.provider

import android.util.Log
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.shareIn
import website.ndlam.zalo.core.stomp.dto.LifecycleEvent


abstract class AConnectionProvider(
    val scope: CoroutineScope
) : IConnectionProvider {
    val messagesStream: MutableSharedFlow<String?> = MutableSharedFlow(0, 0)
    val lifecycle: MutableSharedFlow<LifecycleEvent?> = MutableSharedFlow(0, 0)

    override fun messages(): SharedFlow<String?> {
        return this.messagesStream.onStart { initSocket() }.shareIn(scope, SharingStarted.Eagerly)
    }

    private suspend fun initSocket() {
        return this.createWebSocketConnection()
    }

    /**
     * Simply close socket.
     *
     *
     * For example:
     * <pre>
     * webSocket.close();
    </pre> *
     */
    protected abstract suspend fun rawDisconnect()

    override suspend fun disconnect() {
        this.rawDisconnect()
    }


     override suspend fun send(message: String?) {
        checkNotNull(getSocket() != null) { "Not connected" }
        Log.d(TAG, "Send STOMP message: $message")
        rawSend(message)
    }


    protected suspend fun emitLifecycleEvent(lifecycleEvent: LifecycleEvent) {
        Log.d(TAG, "Emit lifecycle event: " + lifecycleEvent.type!!.name)
        lifecycle.emit(lifecycleEvent)
    }

    protected suspend fun emitMessage(stompMessage: String?) {
        Log.d(TAG, "Receive STOMP message: $stompMessage")
        messagesStream.emit(stompMessage)
    }


    override fun lifecycle(): SharedFlow<LifecycleEvent?> {
        return lifecycle
    }

    /**
     * Most important method: connects to websocket and notifies program of messages.
     *
     *
     * See implementations in OkHttpConnectionProvider and WebSocketsConnectionProvider.
     */
    protected abstract suspend fun createWebSocketConnection()

    /**
     * Just a simple message send.
     *
     *
     * For example:
     * <pre>
     * webSocket.send(stompMessage);
    </pre> *
     *
     * @param stompMessage message to send
     */
    protected abstract fun rawSend(stompMessage: String?)

    /**
     * Get socket object.
     * Used for null checking; this object is expected to be null when the connection is not yet established.
     *
     *
     * For example:
     * <pre>
     * return webSocket;
    </pre> *
     */

    protected abstract fun getSocket(): Any?

    companion object {
        private val TAG: String = AConnectionProvider::class.java.simpleName
    }
}