package com.example.flashcards.ui.callback

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.flashcards.databinding.FragmentCallbackBinding

class CallbackFragment : Fragment() {

    private lateinit var callbackViewModel: CallbackViewModel
    private var _binding: FragmentCallbackBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        callbackViewModel =
            ViewModelProvider(this).get(CallbackViewModel::class.java)

        _binding = FragmentCallbackBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val callbackText = binding.callbackText.text
        val callbackSubmit = binding.callbackSubmit

        callbackSubmit.setOnClickListener {
            Toast.makeText(context, "Thanks for your callback", Toast.LENGTH_SHORT).show()
        }

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
