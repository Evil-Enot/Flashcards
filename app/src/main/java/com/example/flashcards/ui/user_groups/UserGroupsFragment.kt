package com.example.flashcards.ui.user_groups

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.flashcards.R
import com.example.flashcards.databinding.FragmentUserGroupsBinding

class UserGroupsFragment : Fragment() {

    private lateinit var addNewGroupButton: ImageButton
    private lateinit var userGroupsViewModel: UserGroupsViewModel
    private var _binding: FragmentUserGroupsBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        userGroupsViewModel =
            ViewModelProvider(this).get(UserGroupsViewModel::class.java)

        _binding = FragmentUserGroupsBinding.inflate(inflater, container, false)
        val root: View = binding.root

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        addNewGroupButton = view.findViewById(R.id.add_friend)
        addNewGroupButton.setOnClickListener {
            findNavController().navigate(R.id.action_to_user_groups_to_addNewGroupPageFragment)
        }
    }
}