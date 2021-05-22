package com.example.flashcards.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.flashcards.R

class GroupPageFragment: Fragment() {

    private lateinit var callbackButton: ImageButton

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.group_page, container, false)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        callbackButton = view.findViewById(R.id.callback_button)
        callbackButton.setOnClickListener {
            findNavController().navigate(R.id.action_groupPageFragment_to_to_callback)
        }
    }
}