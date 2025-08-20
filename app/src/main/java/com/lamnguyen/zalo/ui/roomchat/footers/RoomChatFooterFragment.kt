package com.lamnguyen.zalo.ui.roomchat.footers

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.commit
import com.lamnguyen.zalo.R

class RoomChatFooterFragment : Fragment() {
    val listRightBottom = listOf(RightBottomNotMessageFragment(), RightBottomHasMessageFragment())
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
}