package com.lamnguyen.zalo.ui.welcome.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.lamnguyen.zalo.R

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_IMAGE = "image"
private const val ARG_TITLE = "title"
private const val ARG_DESCRIPTION = "description"

/**
 * A simple [Fragment] subclass.
 * Use the [IntroductionFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class IntroductionFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var image: Int = 0
    private var title: String? = null
    private var description: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            image = it.getInt(ARG_IMAGE)
            title = it.getString(ARG_TITLE)
            description = it.getString(ARG_DESCRIPTION)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_introduction, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        view.findViewById<ImageView>(R.id.image).setImageResource(image)
        view.findViewById<TextView>(R.id.text_title).text = title
        view.findViewById<TextView>(R.id.text_description).text = description
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param image Parameter 1.
         * @param title Parameter 2.
         * @param description Parameter 3.
         * @return A new instance of fragment IntroductionFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(image: Int, title: String, description: String) =
            IntroductionFragment().apply {
                arguments = Bundle().apply {
                    putInt(ARG_IMAGE, image)
                    putString(ARG_TITLE, title)
                    putString(ARG_DESCRIPTION, description)
                }
            }
    }
}