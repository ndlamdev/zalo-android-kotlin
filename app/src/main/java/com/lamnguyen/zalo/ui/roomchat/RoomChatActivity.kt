package com.lamnguyen.zalo.ui.roomchat

import android.content.Intent
import android.content.res.Resources
import android.os.Bundle
import android.view.View.OnClickListener
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.FragmentContainerView
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.lamnguyen.zalo.R
import com.lamnguyen.zalo.entities.Message
import com.lamnguyen.zalo.repositories.AppDatabase
import com.lamnguyen.zalo.repositories.MessageRepository
import com.lamnguyen.zalo.services.StompSocketService
import com.lamnguyen.zalo.ui.roomchat.adapters.MessageAdapter
import com.lamnguyen.zalo.ui.roomchat.headers.OptionRoomChatHeaderFragment
import com.lamnguyen.zalo.ui.roomchat.headers.RoomChatHeaderFragment
import com.lamnguyen.zalo.ui.roomchat.viewmodels.RoomChatViewModel
import com.lamnguyen.zalo.utils.helpers.TokenHelper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class RoomChatActivity : AppCompatActivity() {
    private lateinit var viewModel: RoomChatViewModel
    private val stompServiceContext = StompSocketService.StompSocketServiceContext()
    private var adapter: MessageAdapter? = null
    private lateinit var rclMessage: RecyclerView
    private lateinit var messageRepository: MessageRepository
    private val oldMessages = mutableListOf<Message>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_room_chat)
        messageRepository = AppDatabase.getDatabase(this).messageRepository()

        viewModel = ViewModelProvider(this)[RoomChatViewModel::class.java].apply {
            intent.extras?.also {
                roomChatIdLiveData.value = it.getString(ARG_ROOM_CHAT_ID)
                roomChatTitleLiveData.value = it.getString(ARG_ROOM_CHAT_TITLE)
                roomChatIsGroupLiveData.value = it.getBoolean(ARG_ROOM_CHAT_IS_GROUP)
            }
        }

        val drawerLayout = findViewById<DrawerLayout>(R.id.drawer_layout)

        drawerLayout.findViewById<LinearLayout>(R.id.right_drawer).apply {
            layoutParams.width = Resources.getSystem().displayMetrics.widthPixels
            requestLayout()
        }

        findViewById<FragmentContainerView>(R.id.frame_header).apply {
            getFragment<RoomChatHeaderFragment>().apply {
                onClickMenuListener = OnClickListener {
                    if (drawerLayout.isOpen) {
                        drawerLayout.closeDrawer(GravityCompat.END)
                    } else {
                        drawerLayout.openDrawer(GravityCompat.END)
                    }
                }
            }
        }

        findViewById<FragmentContainerView>(R.id.fragment_option_header).apply {
            getFragment<OptionRoomChatHeaderFragment>().onClickBackListener = OnClickListener {
                drawerLayout.closeDrawer(GravityCompat.END)
            }
        }

        rclMessage = findViewById(R.id.recycler_message)
        rclMessage.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, true)
        adapter = MessageAdapter(
            oldMessages,
            false
        )
        rclMessage.adapter = adapter
        loadOldMessage()
    }

    private val connection = StompSocketService.initConnection(stompServiceContext) {
        it.subscribeDestinationMessage { message ->
            runOnUiThread {
                adapter?.messages?.add(0, message)
                adapter?.notifyItemInserted(0)
                rclMessage.smoothScrollToPosition(0)
            }
        }
    }

    override fun onStart() {
        super.onStart()
        Intent(this, StompSocketService::class.java).also { intent ->
            bindService(intent, connection, BIND_AUTO_CREATE)
            startService(intent)
        }
    }

    companion object {
        const val ARG_ROOM_CHAT_TITLE = "room_chat_title"
        const val ARG_ROOM_CHAT_ID = "room_chat_id"
        const val ARG_ROOM_CHAT_IS_GROUP = "room_chat_is_group"
    }

    private fun loadOldMessage() {
        lifecycleScope.launch(Dispatchers.IO) {
            val result = messageRepository.findAllByOwnerPhoneNumberAndRoomId(
                TokenHelper.getAccessTokenPayload(this@RoomChatActivity)?.phoneNumber!!,
                viewModel.roomChatIdLiveData.value as String
            )
            oldMessages.addAll(result)
        }
    }
}