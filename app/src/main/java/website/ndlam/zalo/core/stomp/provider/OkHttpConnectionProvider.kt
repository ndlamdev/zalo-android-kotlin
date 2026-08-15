package website.ndlam.zalo.core.stomp.provider

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import okhttp3.Headers
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import okhttp3.WebSocket
import okhttp3.WebSocketListener
import okio.ByteString
import website.ndlam.zalo.core.stomp.dto.LifecycleEvent
import java.util.TreeMap


class OkHttpConnectionProvider(
    val uri: String,
    val headers: Map<String, String>?,
    val okHttpClient: OkHttpClient?,
    scope: CoroutineScope
) : AConnectionProvider(scope) {
    private var openSocket: WebSocket? = null

    override suspend fun rawDisconnect() {
        openSocket?.close(1000, "")
    }

    override suspend fun createWebSocketConnection() {
        val requestBuilder: Request.Builder = Request.Builder()
            .url(uri)

        if (headers != null)
            addConnectionHeadersToBuilder(requestBuilder, headers)

        openSocket = okHttpClient?.newWebSocket(
            requestBuilder.build(),
            object : WebSocketListener() {
                override fun onOpen(webSocket: WebSocket, response: Response) {
                    val openEvent = LifecycleEvent(LifecycleEvent.Type.OPENED)

                    val headersAsMap: TreeMap<String?, String?> = headersAsMap(response)

                    openEvent.handshakeResponseHeaders = headersAsMap
                    scope.launch { emitLifecycleEvent(openEvent) }
                }

                override fun onMessage(webSocket: WebSocket, text: String) {
                    scope.launch { emitMessage(text) }
                }

                override fun onMessage(webSocket: WebSocket, bytes: ByteString) {
                    scope.launch { emitMessage(bytes.utf8()) }
                }

                override fun onClosed(webSocket: WebSocket, code: Int, reason: String) {
                    openSocket = null
                    scope.launch { emitLifecycleEvent(LifecycleEvent(LifecycleEvent.Type.CLOSED)) }
                }

                override fun onFailure(
                    webSocket: WebSocket,
                    t: Throwable,
                    response: Response?
                ) {
                    // in OkHttp, a Failure is equivalent to a JWS-Error *and* a JWS-Close
                    scope.launch {
                        emitLifecycleEvent(LifecycleEvent(LifecycleEvent.Type.ERROR, Exception(t)))
                        openSocket = null
                        emitLifecycleEvent(LifecycleEvent(LifecycleEvent.Type.CLOSED))
                    }
                }

                override fun onClosing(webSocket: WebSocket, code: Int, reason: String) {
                    webSocket.close(code, reason)
                }
            }

        )
    }

    override fun rawSend(stompMessage: String?) {
        openSocket?.send(stompMessage ?: "")
    }

    override fun getSocket(): Any? {
        return openSocket
    }

    private fun headersAsMap(response: Response): TreeMap<String?, String?> {
        val headersAsMap = TreeMap<String?, String?>()
        val headers: Headers = response.headers
        for (key in headers.names()) {
            if (headers[key] != null)
                headersAsMap[key] = headers[key]!!
        }
        return headersAsMap
    }

    private fun addConnectionHeadersToBuilder(
        requestBuilder: Request.Builder,
        headers: Map<String, String>
    ) {
        for (headerEntry in headers.entries) {
            requestBuilder.addHeader(headerEntry.key, headerEntry.value)
        }
    }

    companion object {
        val TAG: String = OkHttpConnectionProvider::class.java.name
    }
}