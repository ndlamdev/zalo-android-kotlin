package website.ndlam.zalo.observers

import kotlinx.coroutines.flow.Flow

interface ConnectivityObserver {
    val isConnected: Flow<Boolean>
    fun isCurrentlyConnected(): Boolean
}