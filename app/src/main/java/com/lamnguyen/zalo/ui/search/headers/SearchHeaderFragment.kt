package com.lamnguyen.zalo.ui.search.headers

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.Fragment
import com.lamnguyen.zalo.R
import com.lamnguyen.zalo.ui.main.MainActivity

class SearchHeaderFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_search_header, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        view.findViewById<ImageView>(R.id.image_button_back).apply {
            setOnClickListener {
                startActivity(Intent(requireActivity(), MainActivity::class.java))
            }
        }
    }
}