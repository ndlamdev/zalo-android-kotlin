package com.lamnguyen.zalo.entities;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import com.lamnguyen.zalo.utils.enums.ContentMessageType
import java.io.Serializable
import java.time.Instant

@Entity(tableName = "messages")
data class Message(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    @ColumnInfo(name = "owner_phone_number") var ownerPhoneNumber: String = "",
    @ColumnInfo(name = "sender_phone_number") val senderPhoneNumber: String,
    @ColumnInfo(name = "sender_display_name") val senderDisplayName: String,
    @ColumnInfo(name = "sender_avatar") val senderAvatar: String,
    @ColumnInfo(name = "room_chat_id") val roomChatId: Long,
    @ColumnInfo(name = "content") val content: String,
    @ColumnInfo(name = "type") val type: ContentMessageType,
    @ColumnInfo(name = "url_media") val urlMedia: String,
    @ColumnInfo(name = "timestamp") val timestamp: Instant,
    @ColumnInfo(name = "read") val read: Boolean,
) : Serializable