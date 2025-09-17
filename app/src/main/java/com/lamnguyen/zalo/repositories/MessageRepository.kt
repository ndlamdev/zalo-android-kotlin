package com.lamnguyen.zalo.repositories

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.lamnguyen.zalo.entities.Message

@Dao
interface MessageRepository {
    @Query(
        """
        SELECT *
        FROM messages
        WHERE owner_phone_number = :ownerPhoneNumber
        AND room_chat_id = :roomId
    """
    )
    fun findAllByOwnerPhoneNumberAndRoomId(ownerPhoneNumber: String, roomId: String): List<Message>

    @Insert
    fun insert(message: Message)

    @Query("DELETE FROM cookies WHERE id = :id")
    fun deleteById(id: Long)

    @Query(
        """
        SELECT *
        FROM messages
        WHERE owner_phone_number = :ownerPhoneNumber
        AND room_chat_id = :roomId
        ORDER BY id DESC
        LIMIT 1
    """
    )
    fun findLastMessageByOwnerPhoneNumberAndRoomId(ownerPhoneNumber: String, roomId: String): Message

    @Query(
        """
        SELECT COUNT(*)
        FROM messages
        WHERE owner_phone_number = :ownerPhoneNumber
        AND room_chat_id = :roomId
        AND read = 0
    """
    )
    fun countMessageByOwnerPhoneNumberAndRoomIdAndReadIsFalse(
        ownerPhoneNumber: String,
        roomId: String,
    ): String
}