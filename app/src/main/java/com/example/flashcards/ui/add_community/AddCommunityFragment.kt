package com.example.flashcards.ui.add_community

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.flashcards.databinding.FragmentAddCommunityBinding

class AddCommunityFragment : Fragment() {
    private lateinit var addCommunityViewModel: AddCommunityViewModel
    private var _binding: FragmentAddCommunityBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        addCommunityViewModel =
            ViewModelProvider(this).get(AddCommunityViewModel::class.java)

        _binding = FragmentAddCommunityBinding.inflate(inflater, container, false)
        val root: View = binding.root

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
