package com.lamnguyen.zalo.utils.adapters

import android.text.Html
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.lamnguyen.zalo.R
import com.lamnguyen.zalo.utils.enums.ContentMessageType
import java.io.Serializable

class ListRoomChatRecyclerViewAdapter(private val dataSet: List<RoomChatInfo>) :
    RecyclerView.Adapter<ListRoomChatRecyclerViewAdapter.RoomChatHolder>() {

    class RoomChatHolder(private val view: View) : RecyclerView.ViewHolder(view) {
        private val imgAvatar: ImageView = view.findViewById(R.id.image_avatar_room_chat)
        private val txtRoomChatTitle: TextView = view.findViewById(R.id.text_rom_chat_title)
        private val txtLastMessage: TextView =
            view.findViewById(R.id.text_last_message_in_room_chat)
        private val imgPinRoomChat: ImageView = view.findViewById(R.id.image_pin_room_chat)
        private val txtTimeLastAction: TextView = view.findViewById(R.id.text_time_last_action)
        private val txtTotalMessageUnread: TextView =
            view.findViewById(R.id.text_total_message_unread)

        fun bindData(data: RoomChatInfo) {
            Glide.with(this.itemView)
                .load(data.image)
                .into(imgAvatar)
            txtRoomChatTitle.text = data.title
            """
            ${data.lastMessage.sender}: ${data.lastMessage.content} [${
                when (data.lastMessage.type) {
                    ContentMessageType.AUDIO -> "Âm thanh"
                    ContentMessageType.VIDEO -> "Video"
                    ContentMessageType.IMAGE -> "Hình ảnh"
                    else -> ""
                }
            }]
        """.trimIndent().also { txtLastMessage.text = it }
            imgPinRoomChat.visibility = if (data.pin) View.VISIBLE else View.INVISIBLE
            txtTimeLastAction.text = data.timeLastAction
            if (data.totalMessageUnread == 0) {
                txtTotalMessageUnread.visibility = View.INVISIBLE
            } else {
                txtTotalMessageUnread.visibility = View.VISIBLE
                txtTotalMessageUnread.text = if (data.totalMessageUnread > 9) Html.fromHtml(
                    "9<sup>+</sup>",
                    Html.FROM_HTML_MODE_LEGACY
                ) else data.totalMessageUnread.toString()
            }

            this.view.setOnClickListener { v ->
                data.onClickListener.onClick(v, data)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RoomChatHolder {
        return RoomChatHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.fragment_card_room_chat, parent, false)
        )
    }

    override fun getItemCount(): Int {
        return dataSet.size
    }

    override fun onBindViewHolder(holder: RoomChatHolder, position: Int) {
        holder.bindData(dataSet[position])
    }

    data class RoomChatInfo(
        var id: Long,
        var image: String,
        var title: String,
        var lastMessage: LastMessage,
        var timeLastAction: String,
        var totalMessageUnread: Int,
        var pin: Boolean,
        var onClickListener: OnClickListener
    ) : Serializable {
        data class LastMessage(
            val sender: String, val content: String, val type: ContentMessageType
        ) : Serializable
    }

    interface OnClickListener : Serializable {
        fun onClick(view: View?, data: RoomChatInfo?)
    }
}