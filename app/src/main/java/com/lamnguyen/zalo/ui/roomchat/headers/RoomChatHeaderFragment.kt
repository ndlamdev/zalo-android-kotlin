package com.lamnguyen.zalo.ui.roomchat.headers

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.View.OnClickListener
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.lamnguyen.zalo.R
import com.lamnguyen.zalo.ui.main.MainActivity
import com.lamnguyen.zalo.ui.roomchat.viewmodels.RoomChatViewModel

class RoomChatHeaderFragment : Fragment() {
    var onClickMenuListener: OnClickListener? = null
    private val viewModel: RoomChatViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_room_chat_header, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val imgMenu = view.findViewById<ImageView>(R.id.image_button_menu)
        imgMenu.setOnClickListener { v -> onClickMenuListener?.onClick(v) }
        view.findViewById<ImageView>(R.id.image_button_back).apply {
            setOnClickListener {
                startActivity(Intent(requireActivity(), MainActivity::class.java))
            }
        }

        viewModel.title.observe(viewLifecycleOwner) { titleLiveData ->
            view.findViewById<TextView>(R.id.text_room_chat_title).text = titleLiveData
        }

    }
}