package com.lamnguyen.zalo.ui.roomchat.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.lamnguyen.zalo.R
import com.lamnguyen.zalo.entities.Message
import java.time.ZoneId
import java.time.format.DateTimeFormatter


const val MYSELF = 0
const val OTHER_PERSON = 1

class MessageAdapter(val messages: List<Message>, val isGroup: Boolean? = false) :
    RecyclerView.Adapter<MessageAdapter.ContentMessage>() {
    val dateTimeFormatter = DateTimeFormatter.ofPattern("HH:MM")!!
    var preType: Int? = null
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): ContentMessage {
        if (viewType == MYSELF)
            return ContentMessageMyself(parent, dateTimeFormatter)

        return ContentMessageOtherPerson(parent, dateTimeFormatter, isGroup)
    }

    override fun onBindViewHolder(
        holder: ContentMessage,
        position: Int,
    ) {
        val type = getItemViewType(position)
        val message = messages[position]
        holder.binding(message, if (preType == null) null else type == preType)
        preType = type
    }

    override fun getItemCount(): Int = messages.size

    override fun getItemViewType(position: Int): Int {
        val message = messages[position]
        return if (message.senderPhoneNumber == message.ownerPhoneNumber) MYSELF else OTHER_PERSON
    }

    abstract class ContentMessage(
        view: View,
        val dateTimeFormatter: DateTimeFormatter,
    ) : RecyclerView.ViewHolder(view) {
        val txtContent = itemView.findViewById<TextView>(R.id.text_content)!!
        val txtTime = itemView.findViewById<TextView>(R.id.text_time)!!

        val cardHeart = itemView.findViewById<CardView>(R.id.card_heart)!!

        open fun binding(message: Message, isSamePreMessageType: Boolean?) {
            txtContent.text = message.content
            txtTime.text =
                dateTimeFormatter
                    .format(message.timestamp.atZone(ZoneId.systemDefault()))

            val layoutParams = itemView.layoutParams
            if (isSamePreMessageType != null && layoutParams is ViewGroup.MarginLayoutParams) {
                var margin = itemView.resources.getDimension(R.dimen._7dp).toInt()
                if (isSamePreMessageType)
                    margin = itemView.resources.getDimension(R.dimen._2dp).toInt()
                layoutParams.bottomMargin = margin
            }
            itemView.requestLayout()
        }
    }

    private class ContentMessageOtherPerson(
        parent: ViewGroup,
        dateTimeFormatter: DateTimeFormatter,
        val isGroup: Boolean? = false,
    ) :
        ContentMessage(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.fragment_content_message_other_person, parent, false),
            dateTimeFormatter
        ) {
        val imgAvatar = itemView.findViewById<ImageView>(R.id.image_avatar)!!
        val txtName = itemView.findViewById<TextView>(R.id.text_name)!!

        init {
            if (firstMessage)
                firstMessage = false
            else {
                cardHeart.visibility = View.GONE
            }
        }

        override fun binding(message: Message, isSamePreMessageType: Boolean?) {
            Glide.with(itemView).load(message.senderAvatar).into(imgAvatar)
            if (isGroup == true) {
                txtName.text = message.senderDisplayName
            } else {
                txtName.visibility = View.GONE
            }
            super.binding(message, isSamePreMessageType)
        }

        companion object {
            var firstMessage = true
        }
    }

    private class ContentMessageMyself(
        parent: ViewGroup,
        dateTimeFormatter: DateTimeFormatter,
    ) :
        ContentMessage(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.fragment_content_message_myself, parent, false),
            dateTimeFormatter
        ) {

        override fun binding(message: Message, isSamePreMessageType: Boolean?) {
            super.binding(message, isSamePreMessageType)
        }

        init {
            if (firstMessage)
                firstMessage = false
            else {
                cardHeart.visibility = View.GONE
            }
        }

        companion object {
            var firstMessage = true
        }
    }
}