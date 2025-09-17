package com.lamnguyen.zalo.ui.message.adapters

import android.text.Html
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.lamnguyen.zalo.R
import com.lamnguyen.zalo.ui.main.viewmodels.MessageFragmentViewModel
import com.lamnguyen.zalo.utils.enums.ContentMessageType
import java.util.function.Function

class ListRoomChatRecyclerViewAdapter(
    private val mapRoomChat: List<MessageFragmentViewModel.RoomChatDetail>,
    val onClickListenerCallback: Function<MessageFragmentViewModel.RoomChatDetail, Unit>,
) :
    RecyclerView.Adapter<ListRoomChatRecyclerViewAdapter.RoomChatHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RoomChatHolder {
        return RoomChatHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.fragment_card_room_chat, parent, false)
        )
    }

    override fun getItemCount(): Int = mapRoomChat.size

    override fun onBindViewHolder(holder: RoomChatHolder, position: Int) {
        holder.bindData(mapRoomChat[position], onClickListenerCallback)
    }

    class RoomChatHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val imgAvatar: ImageView = itemView.findViewById(R.id.image_avatar_room_chat)
        private val txtRoomChatTitle: TextView = itemView.findViewById(R.id.text_rom_chat_title)
        private val txtLastMessage: TextView =
            itemView.findViewById(R.id.text_last_message_in_room_chat)
        private val imgPinRoomChat: ImageView = itemView.findViewById(R.id.image_pin_room_chat)
        private val txtTimeLastAction: TextView = itemView.findViewById(R.id.text_time_last_action)
        private val txtTotalMessageUnread: TextView =
            itemView.findViewById(R.id.text_total_message_unread)

        fun bindData(
            data: MessageFragmentViewModel.RoomChatDetail,
            onClickListenerCallback: Function<MessageFragmentViewModel.RoomChatDetail, Unit>,
        ) {
            val lastMessage = data.messages.last()
            Glide.with(itemView)
                .load(data.avatar)
                .into(imgAvatar)
            txtRoomChatTitle.text = data.title
            """
            ${lastMessage.senderPhoneNumber}: ${lastMessage.content} [${
                when (lastMessage.type) {
                    ContentMessageType.AUDIO -> "Âm thanh"
                    ContentMessageType.VIDEO -> "Video"
                    ContentMessageType.IMAGE -> "Hình ảnh"
                    else -> ""
                }
            }]
        """.trimIndent().also { txtLastMessage.text = it }
            imgPinRoomChat.visibility = if (data.pin) View.VISIBLE else View.INVISIBLE
            txtTimeLastAction.text = ""
            if (data.totalMessageUnread == 0) {
                txtTotalMessageUnread.visibility = View.INVISIBLE
            } else {
                txtTotalMessageUnread.visibility = View.VISIBLE
                txtTotalMessageUnread.text = if (data.totalMessageUnread > 9) Html.fromHtml(
                    "9<sup>+</sup>",
                    Html.FROM_HTML_MODE_LEGACY
                ) else data.totalMessageUnread.toString()
            }

            itemView.setOnClickListener { v ->
                onClickListenerCallback.apply(data)
            }
        }
    }
}