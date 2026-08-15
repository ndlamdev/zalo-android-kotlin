package website.ndlam.zalo.core.stomp

import kotlinx.coroutines.CoroutineScope
import okhttp3.OkHttpClient
import website.ndlam.zalo.core.stomp.provider.IConnectionProvider
import website.ndlam.zalo.core.stomp.provider.OkHttpConnectionProvider


object Stomp {
    fun over(
        connectionProvider: ConnectionProvider,
        uri: String?,
        scope: CoroutineScope
    ): StompClient {
        return over(connectionProvider, uri, null, null, scope)
    }

    /**
     * @param connectionProvider ConnectionProvider method
     * @param uri                URI to connect
     * @param connectHttpHeaders HTTP headers, will be passed with handshake query, may be null
     * @return StompClient for receiving and sending messages. Call #StompClient.connect
     */
    fun over(
        connectionProvider: ConnectionProvider,
        uri: String?,
        connectHttpHeaders: Map<String, String>?,
        scope: CoroutineScope
    ): StompClient {
        return over(connectionProvider, uri, connectHttpHeaders, null, scope)
    }

    /**
     * `webSocketClient` can accept the following type of clients:
     * 
     *  * `org.java_websocket.WebSocket`: cannot accept an existing client
     *  * `okhttp3.WebSocket`: can accept a non-null instance of `okhttp3.OkHttpClient`
     * 
     * 
     * @param connectionProvider ConnectionProvider method
     * @param uri                URI to connect
     * @param connectHttpHeaders HTTP headers, will be passed with handshake query, may be null
     * @param okHttpClient       Existing client that will be used to open the WebSocket connection, may be null to use default client
     * @return StompClient for receiving and sending messages. Call #StompClient.connect
     */
    fun over(
        connectionProvider: ConnectionProvider,
        uri: String?,
        connectHttpHeaders: Map<String, String>?,
        okHttpClient: OkHttpClient?,
        scope: CoroutineScope
    ): StompClient {
        if (connectionProvider === ConnectionProvider.OKHTTP) {
            return createStompClient(
                OkHttpConnectionProvider(
                    uri ?: "",
                    connectHttpHeaders,
                    okHttpClient ?: OkHttpClient(),
                    scope
                )
            )
        }

        throw IllegalArgumentException("ConnectionProvider type not supported: $connectionProvider")
    }

    private fun createStompClient(connectionProvider: IConnectionProvider): StompClient {
        return StompClient(connectionProvider)
    }

    enum class ConnectionProvider {
        OKHTTP, JWS
    }

}