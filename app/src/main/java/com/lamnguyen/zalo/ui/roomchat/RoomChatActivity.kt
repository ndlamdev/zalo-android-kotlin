package com.lamnguyen.zalo.ui.roomchat

import android.content.res.Resources
import android.os.Bundle
import android.view.View.OnClickListener
import android.widget.LinearLayout
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.FragmentContainerView
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.viewModelFactory
import com.lamnguyen.zalo.R
import com.lamnguyen.zalo.ui.roomchat.headers.OptionRoomChatHeaderFragment
import com.lamnguyen.zalo.ui.roomchat.headers.RoomChatHeaderFragment
import com.lamnguyen.zalo.ui.roomchat.viewmodels.RoomChatViewModel

class RoomChatActivity : AppCompatActivity() {
    private lateinit var viewModel: RoomChatViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_room_chat)


        viewModel = ViewModelProvider(this)[RoomChatViewModel::class.java].apply {
            id.value = intent.extras?.getLong(ARG_ROOM_CHAT_ID)
            title.value = intent.extras?.getString(ARG_ROOM_CHAT_TITLE)
        }

        val drawerLayout = findViewById<DrawerLayout>(R.id.drawer_layout)

        drawerLayout.findViewById<LinearLayout>(R.id.right_drawer).apply {
            layoutParams.width = Resources.getSystem().displayMetrics.widthPixels
            requestLayout()
        }

        findViewById<FragmentContainerView>(R.id.fragment_header).apply {
            getFragment<RoomChatHeaderFragment>().apply {
                onClickMenuListener = OnClickListener {
                    if (drawerLayout.isOpen) {
                        drawerLayout.closeDrawer(GravityCompat.END)
                    } else {
                        drawerLayout.openDrawer(GravityCompat.END)
                    }
                }
            }
        }

        findViewById<FragmentContainerView>(R.id.fragment_option_header).apply {
            getFragment<OptionRoomChatHeaderFragment>().onClickBackListener = OnClickListener {
                drawerLayout.closeDrawer(GravityCompat.END)
            }
        }
    }

    companion object {
        const val ARG_ROOM_CHAT_TITLE = "room_chat_title"
        const val ARG_ROOM_CHAT_ID = "room_chat_id"
    }
}