package com.lamnguyen.zalo.entities;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.databind.PropertyNamingStrategies
import com.fasterxml.jackson.databind.annotation.JsonNaming
import com.lamnguyen.zalo.utils.enums.ContentMessageType
import java.io.Serializable
import java.time.Instant

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy::class)
@Entity(tableName = "messages")
class Message(
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0,

    @ColumnInfo(name = "owner_phone_number")
    var ownerPhoneNumber: String = "",

    @ColumnInfo(name = "sender_phone_number")
    var senderPhoneNumber: String = "",

    @ColumnInfo(name = "room_chat_id")
    var roomChatId: String = "",

    @ColumnInfo(name = "content")
    var content: String = "",

    @ColumnInfo(name = "type")
    var type: ContentMessageType = ContentMessageType.TEXT,

    @ColumnInfo(name = "url_media")
    var urlMedia: String = "",

    @ColumnInfo(name = "timestamp")
    var timestamp: Instant = Instant.now(),

    @ColumnInfo(name = "read")
    var read: Boolean = false,
) : Serializable {
    constructor(
        @JsonProperty("room_chat_id") roomChatId: String,
        @JsonProperty("sender_phone_number") senderPhoneNumber: String,
        @JsonProperty("content") content: String,
        @JsonProperty("type") type: String,
        @JsonProperty("timestamp") timestamp: Instant,
    ) : this() {
        this.senderPhoneNumber = senderPhoneNumber
        this.roomChatId = roomChatId
        this.content = content
        this.type = ContentMessageType.valueOf(type)
        this.timestamp = timestamp
    }
}