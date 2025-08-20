import androidx.room.Entity
import com.lamnguyen.zalo.entities.BaseEntity

/**
 * Nguyen Dinh Lam
 * Email: kiminonawa1305@gmail.com
 * Phone number: +84 855354919
 * Create at: 4:58 PM-28/07/2025
 *  User: kimin
 **/

@Entity("room_chat_members")
class RoomChatMember : BaseEntity() {
    val id: Long = 0
    lateinit var phoneNumber: String
    val romChatId: Long = 0
    lateinit var username: String
    lateinit var displayName: String
}