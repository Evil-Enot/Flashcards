package com.example.flashcards.ui.add_group

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.flashcards.R
import com.example.flashcards.databinding.FragmentAddGroupBinding
import com.example.flashcards.ui.create_group.CreateNewGroupFragment
import com.example.flashcards.ui.find_group.FindGroupFragment

class AddGroupFragment : Fragment() {

    private var userAction: Int = 2

    private lateinit var addGroupViewModel: AddGroupViewModel
    private var _binding: FragmentAddGroupBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        addGroupViewModel =
            ViewModelProvider(this).get(AddGroupViewModel::class.java)

        _binding = FragmentAddGroupBinding.inflate(inflater, container, false)
        val root: View = binding.root

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.findNewGroupMainBtn.setOnClickListener {
            activity?.supportFragmentManager?.beginTransaction()
                ?.replace(R.id.add_group_container, FindGroupFragment())
                ?.commit()
        }
        binding.createNewGroupMainBtn.setOnClickListener {
            activity?.supportFragmentManager?.beginTransaction()
                ?.replace(R.id.add_group_container, CreateNewGroupFragment())
                ?.commit()
        }
    }
}
