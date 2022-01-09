package com.nicoalex.todo.User

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.nicoalex.todo.R
import com.nicoalex.todo.databinding.FragmentAuthenticationBinding

class AuthenticationFragment : Fragment() {
    private var _binding: FragmentAuthenticationBinding? = null

    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentAuthenticationBinding.inflate(inflater, container, false)
        val view = binding.root

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.LogInButtonActivity.setOnClickListener{
            findNavController().navigate(R.id.action_authenticationFragment_to_loginFragment)
        }
        binding.SignUpButtonActivity.setOnClickListener{
            findNavController().navigate(R.id.action_authenticationFragment_to_signupFragment)
        }
    }
}