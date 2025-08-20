/**
 * Nguyen Dinh Lam
 * Email: kiminonawa1305@gmail.com
 * Phone number: +84 855354919
 * Create at: 4:56 PM-28/07/2025
 *  User: kimin
 **/

package com.lamnguyen.zalo.entities

import androidx.room.Entity


@Entity("room_chats")
class RoomChat : BaseEntity() {
    var id: Long? = null
    lateinit var title: String
    lateinit var avatar: String
    lateinit var theme: String
}