package com.lamnguyen.zalo.ui.roomchat.footers

import android.content.Context.BIND_AUTO_CREATE
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.commit
import com.lamnguyen.zalo.R
import com.lamnguyen.zalo.services.StompSocketService
import com.lamnguyen.zalo.ui.roomchat.viewmodels.RoomChatViewModel

class RoomChatFooterFragment() : Fragment() {
    private val stompWebsocketServiceContext = StompSocketService.StompSocketServiceContext()
    private val connection = StompSocketService.initConnection(stompWebsocketServiceContext)
    private val viewModel: RoomChatViewModel by activityViewModels()
    val listRightBottom =
        listOf(RightBottomNotMessageFragment(), RightBottomHasMessageFragment { sendMessage() })
    lateinit var editMessage: EditText
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        return inflater.inflate(R.layout.fragment_room_chat_footer, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        childFragmentManager.commit {
            for (frg in listRightBottom) {
                add(R.id.frame_right_room_chat_footer, frg)
                hide(frg)
            }

            show(listRightBottom[0])
        }

        editMessage = view.findViewById(R.id.edit_message)
        editMessage.addTextChangedListener(
            onTextChanged = { text, _, _, _ ->
                if (text?.isEmpty() == true) {
                    childFragmentManager.commit {
                        show(listRightBottom[0])
                        hide(listRightBottom[1])
                    }
                } else {
                    childFragmentManager.commit {
                        show(listRightBottom[1])
                        hide(listRightBottom[0])
                    }
                }
            }
        )
    }

    override fun onStart() {
        super.onStart()
        Intent(requireActivity(), StompSocketService::class.java).also {
            requireActivity().bindService(it, connection, BIND_AUTO_CREATE)
        }
    }

    private fun sendMessage() {
        stompWebsocketServiceContext.sendMessage(
            viewModel.roomChatIdLiveData.value.toString(),
            editMessage.text.toString()
        )
        editMessage.text.clear()
    }
}