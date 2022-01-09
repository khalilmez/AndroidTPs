package com.nicoalex.todo.User

import android.content.Intent
import android.os.Bundle
import android.preference.PreferenceManager
import android.provider.Settings.Global.putString
import android.util.Patterns
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.edit
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.nicoalex.todo.MainActivity
import com.nicoalex.todo.R
import com.nicoalex.todo.databinding.FragmentLoginBinding
import com.nicoalex.todo.network.Api
import com.nicoalex.todo.network.UserWebService
import kotlinx.coroutines.launch

const val SHARED_PREF_TOKEN_KEY = "auth_token_key"

class LoginFragment : Fragment() {
    private var _binding: FragmentLoginBinding? = null

    private val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        val view = binding.root

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.LogInButton.setOnClickListener{
            if(isEmailValid(binding.emailLogIn.text) && binding.emailLogIn.text.toString().isNotEmpty() && binding.passwordLogIn.text.toString().isNotEmpty()){
                lifecycleScope.launch{
                    val loginForm : LoginForm = LoginForm(binding.emailLogIn.text.toString(),binding.passwordLogIn.text.toString())
                    val userWebService = Api.userWebService
                    val response = userWebService.login(loginForm)
                    if(response.isSuccessful){
                        Toast.makeText(context, "Bienvenue !", Toast.LENGTH_LONG).show()
                        PreferenceManager.getDefaultSharedPreferences(context   ).edit {
                            putString(SHARED_PREF_TOKEN_KEY, response.body()!!.id.toString())
                        }
                        findNavController().navigate(R.id.action_loginFragment_to_mainActivity)
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