package website.ndlam.zalo.core.stomp.provider

import kotlinx.coroutines.flow.SharedFlow
import website.ndlam.zalo.core.stomp.dto.LifecycleEvent


interface IConnectionProvider {
    /**
     * Subscribe this for receive stomp messages
     */
    fun messages(): SharedFlow<String?>

    /**
     * Subscribe this for receive #LifecycleEvent events
     */
    fun lifecycle(): SharedFlow<LifecycleEvent?>

    /**
     * Sending stomp messages via you ConnectionProvider.
     * onError if not connected or error detected will be called, or onCompleted id sending started
     */
    suspend fun send(message: String?)

    suspend fun disconnect()
}