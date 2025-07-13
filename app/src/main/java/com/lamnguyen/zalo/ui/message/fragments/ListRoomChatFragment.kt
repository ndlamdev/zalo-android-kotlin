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
import com.lamnguyen.zalo.ui.roomchat.RoomChatActivity
import com.lamnguyen.zalo.utils.adapters.ListRoomChatRecyclerViewAdapter
import com.lamnguyen.zalo.utils.adapters.ListRoomChatRecyclerViewAdapter.RoomChatInfo
import com.lamnguyen.zalo.utils.enums.ContentMessageType

class ListRoomChatFragment : Fragment() {
    private val onClickCardRoomChat = object : ListRoomChatRecyclerViewAdapter.OnClickListener {
        override fun onClick(view: View?, data: RoomChatInfo?) {
            val intent = Intent(context, RoomChatActivity::class.java)
            intent.putExtra(RoomChatActivity.ARG_ROOM_CHAT_ID, data?.id)
            intent.putExtra(RoomChatActivity.ARG_ROOM_CHAT_TITLE, data?.title)
            startActivity(intent)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_list_room_chat, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val rclView = view.findViewById<RecyclerView>(R.id.recycler_list_room_chat)
        val adapter = ListRoomChatRecyclerViewAdapter(
            listOf(
                RoomChatInfo(
                    1,
                    "https://upload.wikimedia.org/wikipedia/en/thumb/0/0b/Your_Name_poster.png/250px-Your_Name_poster.png",
                    "Nguyễn Đình Lam",
                    RoomChatInfo.LastMessage("Bạn", "", ContentMessageType.VIDEO),
                    "2 giờ",
                    1,
                    true,
                    onClickCardRoomChat
                ), RoomChatInfo(
                    1,
                    "https://upload.wikimedia.org/wikipedia/en/thumb/0/0b/Your_Name_poster.png/250px-Your_Name_poster.png",
                    "Nguyễn Đình Lam",
                    RoomChatInfo.LastMessage("Bạn", "", ContentMessageType.VIDEO),
                    "2 giờ",
                    10,
                    false,
                    onClickCardRoomChat
                )
            )
        )
        rclView.layoutManager = LinearLayoutManager(context)
        rclView.adapter = adapter
    }
}