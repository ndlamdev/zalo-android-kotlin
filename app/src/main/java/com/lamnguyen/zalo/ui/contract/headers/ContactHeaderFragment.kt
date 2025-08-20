package com.lamnguyen.zalo.ui.contract.headers

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.lamnguyen.zalo.R
import com.lamnguyen.zalo.ui.search.SearchActivity


class ContactHeaderFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_contact_header, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val imgSearch = view.findViewById<ImageView>(R.id.image_button_search)
        imgSearch.setOnClickListener {
            startActivity(Intent(requireActivity(), SearchActivity::class.java))
        }
        val tvSearch = view.findViewById<TextView>(R.id.text_search)
        tvSearch.setOnClickListener {
            startActivity(Intent(requireActivity(), SearchActivity::class.java))
        }
    }
}