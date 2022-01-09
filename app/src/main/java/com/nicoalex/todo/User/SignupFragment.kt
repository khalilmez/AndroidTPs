package com.nicoalex.todo.User

import android.os.Bundle
import android.preference.PreferenceManager
import android.util.Patterns
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.edit
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.nicoalex.todo.R
import com.nicoalex.todo.databinding.FragmentLoginBinding
import com.nicoalex.todo.databinding.FragmentSignupBinding
import com.nicoalex.todo.network.Api
import kotlinx.coroutines.launch

class SignupFragment : Fragment() {

    private var _binding: FragmentSignupBinding? = null

    private val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentSignupBinding.inflate(inflater, container, false)
        val view = binding.root

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.SignUpButton.setOnClickListener{
            if(isEmailValid(binding.emailField.text.toString()) && binding.emailField.text.toString().isNotEmpty() && binding.passwordField.text.toString().isNotEmpty()
                && binding.passwordField.text.toString().equals(binding.passwordConfirmationField.text.toString()) && binding.firstnameField.text.toString().isNotEmpty()
                && binding.lastnameField.text.toString().isNotEmpty()){
                lifecycleScope.launch{
                    val signUpForm : SignUpForm = SignUpForm(binding.firstnameField.text.toString(),binding.lastnameField.text.toString(),
                    binding.emailField.text.toString(),binding.passwordField.text.toString(),binding.passwordConfirmationField.text.toString())
                    val userWebService = Api.userWebService
                    val response = userWebService.signUp(signUpForm)
                    if(response.isSuccessful){
                        Toast.makeText(context, "Bienvenue !", Toast.LENGTH_LONG).show()
                        PreferenceManager.getDefaultSharedPreferences(context   ).edit {
                            putString(SHARED_PREF_TOKEN_KEY, response.body()!!.id.toString())
                        }
                        findNavController().navigate(R.id.action_signupFragment_to_mainActivity)
                    }else{
                        Toast.makeText(context, "Erreur de connexion", Toast.LENGTH_LONG).show()
                    }
                }
            }else{
                Toast.makeText(context, "Veuillez remplir les champs correctement", Toast.LENGTH_LONG).show()
            }
        }
    }
    fun isEmailValid(email: CharSequence?): Boolean {
        return Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }


}