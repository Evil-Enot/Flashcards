package com.example.flashcards.ui.group_card

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.flashcards.databinding.GroupCardBinding

class GroupCardFragment: Fragment() {
    private lateinit var groupCardViewModel: GroupCardViewModel
    private var _binding: GroupCardBinding? = null

    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        groupCardViewModel =
            ViewModelProvider(this).get(GroupCardViewModel::class.java)

        _binding = GroupCardBinding.inflate(inflater, container, false)
        val root: View = binding.root


        return root
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}