package com.lamnguyen.zalo.ui.message.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.lamnguyen.zalo.R
import com.lamnguyen.zalo.ui.main.viewmodels.MessageFragmentViewModel
import com.lamnguyen.zalo.ui.message.adapters.ListRoomChatRecyclerViewAdapter
import com.lamnguyen.zalo.ui.roomchat.RoomChatActivity
import java.util.function.Function

class ListRoomChatFragment(val messageFragmentViewModel: MessageFragmentViewModel) : Fragment() {
    private val onClickCardRoomChat = Function<MessageFragmentViewModel.RoomChatDetail, Unit> {
        val intent = Intent(context, RoomChatActivity::class.java).apply {
            putExtra(RoomChatActivity.ARG_ROOM_CHAT_ID, it)
        }
        this@ListRoomChatFragment.startActivity(intent)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?,
    ): View? {
        return inflater.inflate(R.layout.fragment_list_room_chat, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val rclView = view.findViewById<RecyclerView>(R.id.recycler_list_room_chat)

        rclView.layoutManager = LinearLayoutManager(context)

        messageFragmentViewModel.roomChatLiveData.observe(viewLifecycleOwner) {
            val adapter = ListRoomChatRecyclerViewAdapter(
                it.values.toList(),
                onClickCardRoomChat
            )

            rclView.adapter = adapter
        }
    }
}