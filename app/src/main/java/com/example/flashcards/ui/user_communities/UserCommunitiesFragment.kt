package com.example.flashcards.ui.user_communities

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.flashcards.R
import com.example.flashcards.databinding.FragmentUserCommunititesBinding

class UserCommunitiesFragment : Fragment() {

    private lateinit var addNewCommunity: ImageButton
    private lateinit var userCommunitiesViewModel: UserCommunitiesViewModel
    private var _binding: FragmentUserCommunititesBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        userCommunitiesViewModel =
            ViewModelProvider(this).get(UserCommunitiesViewModel::class.java)

        _binding = FragmentUserCommunititesBinding.inflate(inflater, container, false)
        val root: View = binding.root

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        addNewCommunity = view.findViewById(R.id.add_new_community)
        addNewCommunity.setOnClickListener {
            findNavController().navigate(R.id.action_userCommunitiesPageFragment_to_addNewCommunityPageFragment)
        }
    }
}