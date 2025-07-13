package com.lamnguyen.zalo.ui.roomchat.headers

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.Fragment
import com.lamnguyen.zalo.R

class OptionRoomChatHeaderFragment : Fragment() {
    var onClickBackListener: View.OnClickListener? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_option_room_chat_header, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        view.findViewById<ImageView>(R.id.image_button_back).setOnClickListener { v ->
            onClickBackListener?.onClick(v)
        }
    }
}