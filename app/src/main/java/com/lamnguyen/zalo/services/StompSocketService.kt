package com.lamnguyen.zalo.services

import android.annotation.SuppressLint
import android.app.Service
import android.content.Intent
import android.os.Binder
import android.os.IBinder
import android.util.Log
import androidx.core.app.NotificationCompat
import com.fasterxml.jackson.databind.ObjectMapper
import com.lamnguyen.zalo.R
import com.lamnguyen.zalo.entities.Message
import com.lamnguyen.zalo.repositories.AppDatabase
import com.lamnguyen.zalo.utils.helpers.TokenHelper
import io.reactivex.disposables.CompositeDisposable
import ua.naiksoftware.stomp.Stomp
import ua.naiksoftware.stomp.StompClient
import ua.naiksoftware.stomp.dto.LifecycleEvent
import ua.naiksoftware.stomp.dto.StompMessage

class StompSocketService : Service() {
    private val TAG = this.javaClass.name

    private val stompClient: StompClient by lazy {
        Stomp.over(Stomp.ConnectionProvider.OKHTTP, "ws://10.0.2.2:8080/ws/websocket")
    }

    private val disposables = CompositeDisposable()
    private val messageRepository = AppDatabase.getDatabase(this).messageRepository()

    private val binder = LocalBinder()
    private val objectMapper = ObjectMapper()

    private val listRoomChatId = mutableListOf<String>()

    inner class LocalBinder : Binder() {
        fun getService(): StompSocketService = this@StompSocketService
    }

    override fun onBind(intent: Intent?): IBinder = binder

    override fun onCreate() {
        super.onCreate()
        connect()
    }

    override fun onStartCommand(
        intent: Intent?,
        flags: Int,
        startId: Int,
    ): Int {
        val notification = NotificationCompat.Builder(this, "123")
            .setContentTitle(resources.getString(R.string.app_name))
            .setContentText("Bạn có một tin nhắn mới.")
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .build()

        startForeground(1, notification) // chạy foreground
        return START_STICKY
    }

    @SuppressLint("CheckResult")
    private fun connect() {
        stompClient.lifecycle()
            .subscribe { event ->
                when (event.type) {
                    LifecycleEvent.Type.OPENED -> Log.d(TAG, "Connected")
                    LifecycleEvent.Type.CLOSED -> Log.d(TAG, "Closed")
                    LifecycleEvent.Type.ERROR -> Log.e(TAG, "Error", event.exception)
                    else -> {}
                }
            }
        stompClient.connect()
    }

    fun subscribe(roomChatId: String, callback: (Message) -> Unit) {
        val disposable = stompClient.topic(roomChatId)
            .subscribe { msg: StompMessage ->
                Log.d(TAG, "Message from $roomChatId: ${msg.payload}")
                val message = objectMapper.convertValue(msg.payload, Message::class.java)
                listRoomChatId.add(roomChatId)
                val ownerPhoneNumber = TokenHelper.getAccessTokenPayload(this.baseContext)?.phoneNumber
                message.ownerPhoneNumber = ownerPhoneNumber ?: ""
                messageRepository.insert(message)
                callback(message)
            }
        disposables.add(disposable)
    }

    fun send(roomChatId: String, message: Message) {
        stompClient.send(roomChatId, objectMapper.writeValueAsString(message)).subscribe()
    }

    override fun onDestroy() {
        disposables.clear()
        stompClient.disconnect()
        super.onDestroy()
        Log.d(TAG, "Service destroyed, socket closed")
    }
}