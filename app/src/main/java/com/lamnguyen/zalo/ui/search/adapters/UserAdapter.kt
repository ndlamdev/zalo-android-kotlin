package com.lamnguyen.zalo.ui.search.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.widget.AppCompatButton
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.lamnguyen.zalo.R
import com.lamnguyen.zalo.configs.RetrofitClient
import com.lamnguyen.zalo.domain.dtos.User
import com.lamnguyen.zalo.domain.requests.InviteAddFriendRequest
import com.lamnguyen.zalo.utils.helpers.LogHelper
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import retrofit2.HttpException

class UserAdapter(val users: List<User>, private val lifecycleScope: CoroutineScope) :
    RecyclerView.Adapter<UserAdapter.UserHolder>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): UserHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_user, parent, false)
        return UserHolder(view)
    }

    override fun onBindViewHolder(
        holder: UserHolder,
        position: Int,
    ) {
        holder.binding(users[position], lifecycleScope)
    }

    override fun getItemCount(): Int = users.size

    class UserHolder(view: View) : RecyclerView.ViewHolder(view) {
        private var imgAvatar: ImageView = itemView.findViewById(R.id.image_avatar)
        private var txtName: TextView = itemView.findViewById(R.id.text_name)
        private var txtPhoneNumber: TextView = itemView.findViewById(R.id.text_phone_number)
        private var btnAddFriend: AppCompatButton = itemView.findViewById(R.id.button_add_friend)

        fun binding(data: User?, lifecycleScope: CoroutineScope) {
            if (data == null) return
            Glide.with(itemView)
                .load(data.avatar)
                .into(imgAvatar)

            txtName.text = data.fullName
            txtPhoneNumber.text = data.phoneNumber
            btnAddFriend.setOnClickListener {
                if (btnAddFriend.tag == TAG_ADD_FRIEND)
                    addFriend(data, lifecycleScope)
            }
            if (data.isFriend) {
                btnAddFriend.visibility = View.INVISIBLE
            } else {
                btnAddFriend.visibility = View.VISIBLE
                if (data.addFriendRequested) {
                    btnAddFriend.text = itemView.resources.getString(R.string.sent)
                    btnAddFriend.tag = TAG_ADDED
                } else {
                    btnAddFriend.text = itemView.resources.getString(R.string.add_friend)
                    btnAddFriend.tag = TAG_ADD_FRIEND
                }
            }
        }

        private fun addFriend(user: User, lifecycleScope: CoroutineScope) {
            val inviteFriendService = RetrofitClient.inviteFriendService(itemView.context)
            lifecycleScope.launch {
                try {
                    val response = inviteFriendService.addFriend(InviteAddFriendRequest().apply {
                        phoneNumber = user.phoneNumber ?: ""
                        message = ""
                    })

                    if (response.code == 200) {
                        btnAddFriend.text = itemView.resources.getString(R.string.sent)
                        btnAddFriend.tag = TAG_ADDED
                    }
                } catch (e: HttpException) {
                    LogHelper.errorWithClassName(itemView, e)
                    LogHelper.showToastError(itemView.context, e)
                } catch (e: Exception) {
                    LogHelper.errorWithClassName(itemView, e)
                    LogHelper.showToastError(itemView.context, e)
                }
            }
        }

        companion object {
            private const val TAG_ADD_FRIEND = "1"
            private const val TAG_ADDED = "2"
        }
    }
}