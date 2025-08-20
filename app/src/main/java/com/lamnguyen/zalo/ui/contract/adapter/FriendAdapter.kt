package com.lamnguyen.zalo.ui.contract.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.lamnguyen.zalo.R
import com.lamnguyen.zalo.domain.dtos.User

class FriendAdapter(users: List<User>) :
    RecyclerView.Adapter<FriendAdapter.FriendViewHolder>() {
    private val data = mutableListOf<User>()

    init {
        var preChar = ""
        users.sortedWith { user, user1 ->
            return@sortedWith user.displayName?.compareTo(user1.displayName ?: "") ?: 0
        }
            .forEach { user ->
                val currentChar = user.displayName?.get(0)!!.lowercase()
                if (currentChar != preChar) {
                    preChar = currentChar
                    data.add(User().apply {
                        displayName = preChar.uppercase()
                        fullName = preChar.uppercase()
                    })
                }
                data.add(user)
            }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): FriendViewHolder {
        if (viewType == TYPE_CONTENT) {
            val view = LayoutInflater
                .from(parent.context).inflate(R.layout.item_friend, parent, false)
            return FriendViewHolder(view)
        }

        val view = TextView(parent.context).apply {
            id = R.id.text_title
        }
        return FriendViewHolder(view)
    }

    override fun onBindViewHolder(
        holder: FriendViewHolder,
        position: Int,
    ) {
        val viewType = getItemViewType(position)
        if (viewType == TYPE_HEADER)
            holder.bindingTitle(data[position].displayName!!)
        else holder.binding(data[position])
    }

    override fun getItemViewType(position: Int): Int {
        val user = data[position]
        return if (user.phoneNumber == null) TYPE_HEADER else TYPE_CONTENT
    }

    override fun getItemCount(): Int = data.size

    class FriendViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        fun binding(user: User) {
            val imgAvatar = itemView.findViewById<ImageView>(R.id.image_avatar)
            val txtName = itemView.findViewById<TextView>(R.id.text_name)
            Glide.with(itemView)
                .load(user.avatar)
                .into(imgAvatar)

            txtName.text = user.displayName
        }

        fun bindingTitle(title: String) {
            val txtName = itemView.findViewById<TextView>(R.id.text_title)
            txtName.setPadding(
                itemView.resources.getDimension(R.dimen.small).toInt(),
                0,
                0,
                0
            )
            txtName.text = title
        }
    }

    companion object {
        private const val TYPE_HEADER = 1
        private const val TYPE_CONTENT = 2
    }
}
