package com.example.flashcards.ui.add_friend

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.flashcards.databinding.FragmentAddFriendBinding

class AddFriendFragment : Fragment() {

    private lateinit var addFriendViewModel: AddFriendViewModel
    private var _binding: FragmentAddFriendBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        addFriendViewModel =
            ViewModelProvider(this).get(AddFriendViewModel::class.java)

        _binding = FragmentAddFriendBinding.inflate(inflater, container, false)
        val root: View = binding.root

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}