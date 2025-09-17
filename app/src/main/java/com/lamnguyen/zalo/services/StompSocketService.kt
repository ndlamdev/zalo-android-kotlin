package com.lamnguyen.zalo.services

import android.annotation.SuppressLint
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.Service
import android.content.ComponentName
import android.content.Intent
import android.content.ServiceConnection
import android.os.Binder
import android.os.IBinder
import android.util.Log
import androidx.core.app.NotificationCompat
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.PropertyNamingStrategies
import com.fasterxml.jackson.databind.annotation.JsonNaming
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
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

    private lateinit var stompClient: StompClient

    private val disposables = CompositeDisposable()
    private val messageRepository = AppDatabase.getDatabase(this).messageRepository()

    private val binder = LocalBinder()
    private val objectMapper = ObjectMapper()
        .registerModule(JavaTimeModule())

    private val listRoomChatId = mutableListOf<String>()

    inner class LocalBinder : Binder() {
        fun getService(): StompSocketService = this@StompSocketService
    }

    override fun onBind(intent: Intent?): IBinder {
        return binder
    }

    override fun onCreate() {
        super.onCreate()
        val token = TokenHelper.getAccessToken(this)
        stompClient = Stomp.over(
            Stomp.ConnectionProvider.OKHTTP, "https://zalo.ndlamdev.website/chat-ws/chat-websocket",
            mapOf("Authorization" to "Bearer $token")
        )
        createNotificationChannel()
        connect()
    }

    override fun onStartCommand(
        intent: Intent?,
        flags: Int,
        startId: Int,
    ): Int {
        val notification = NotificationCompat.Builder(this, CHANNEL_ID)
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
                    LifecycleEvent.Type.CLOSED -> stompClient.connect()
                    LifecycleEvent.Type.ERROR -> Log.e(TAG, "Error", event.exception)
                    else -> {}
                }
            }
        stompClient.connect()
    }

    fun subscribeDestinationMessage(callback: (Message) -> Unit) {
        val phoneNumber = TokenHelper.getAccessTokenPayload(this)?.phoneNumber ?: ""
        val disposable = stompClient.topic("/user/queue/messages")
            .subscribe { msg: StompMessage ->
                Log.d(TAG, "Message from $phoneNumber: ${msg.payload}")
                val message = objectMapper.readValue(msg.payload, Message::class.java)
                listRoomChatId.add(phoneNumber)
                message.ownerPhoneNumber = phoneNumber
                messageRepository.insert(message)
                callback(message)
            }
        disposables.add(disposable)
    }

    fun subscribe(destinationPath: String, callback: (StompMessage) -> Unit) {
        val disposable = stompClient.topic(destinationPath)
            .subscribe { msg: StompMessage ->
                Log.d(TAG, "Message from $destinationPath: ${msg.payload}")
                callback(msg)
            }
        disposables.add(disposable)
    }

    fun sendText(roomChatId: String, message: String) {
        stompClient
            .send(
                "/app/chat.text",
                objectMapper.writeValueAsString(TextMessage(roomChatId, message))
            )
            .subscribe()
    }

    override fun onDestroy() {
        disposables.clear()
        stompClient.disconnect()
        super.onDestroy()
        Log.d(TAG, "Service destroyed, socket closed")
    }

    @JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy::class)
    class TextMessage(
        var roomChatId: String,
        var content: String,
    )

    class StompSocketServiceContext {
        var stompService: StompSocketService? = null
        var bound: Boolean = false

        fun sendMessage(roomChatId: String, message: String) {
            if (bound) {
                stompService?.sendText(roomChatId, message)
            }
        }
    }


    private fun createNotificationChannel() {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is not in the Support Library.
        val name = getString(R.string.app_name)
        val descriptionText = getString(R.string.app_name)
        val importance = NotificationManager.IMPORTANCE_DEFAULT
        val channel = NotificationChannel(CHANNEL_ID, name, importance).apply {
            description = descriptionText
        }
        val notificationManager: NotificationManager =
            getSystemService(NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.createNotificationChannel(channel)
    }

    companion object {
        const val CHANNEL_ID = "123456"
        fun initConnection(
            field: StompSocketServiceContext,
            callback: (service: StompSocketService) -> Unit = {},
        ): ServiceConnection {
            return object : ServiceConnection {
                override fun onServiceConnected(name: ComponentName?, binder: IBinder?) {
                    val localBinder = binder as LocalBinder
                    field.stompService = localBinder.getService()
                    field.bound = true

                    callback(field.stompService!!)
                }

                override fun onServiceDisconnected(name: ComponentName?) {
                    field.bound = false
                    field.stompService = null
                }
            }
        }
    }

}