package com.nicoalex.todo

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.preference.PreferenceManager
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.edit
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import coil.load
import com.nicoalex.todo.User.AuthenticationActivity
import com.nicoalex.todo.User.SHARED_PREF_TOKEN_KEY
import com.nicoalex.todo.User.UserInfoActivity
import com.nicoalex.todo.databinding.ActivityMainBinding
import com.nicoalex.todo.network.Api
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        binding.modifyPicture.setOnClickListener{
            val intent = Intent(this, UserInfoActivity::class.java)
            startActivity(intent)
        }
        binding.disconnect.setOnClickListener{
            PreferenceManager.getDefaultSharedPreferences(Api.appContext).edit {
                putString(SHARED_PREF_TOKEN_KEY, "")
            }
            val intent = Intent (this,AuthenticationActivity::class.java)
            startActivity(intent)
        }
        setContentView(view)

        //setContentView(R.layout.activity_main)
    }


    override fun onResume() {
        super.onResume()

        lifecycleScope.launch {

            if(Api.userWebService.getInfo().isSuccessful){
                val userInfo = Api.userWebService?.getInfo()
                if(userInfo!=null && userInfo.isSuccessful){
                    binding.Profile.text = "${userInfo?.body()!!.firstName} ${userInfo?.body()!!.lastName} \n ${userInfo?.body()!!.email}"

                    if(userInfo?.body()!!.avatar !=null){
                        binding.imageProfil.load(userInfo?.body()!!.avatar){
                            error(R.drawable.ic_launcher_background)
                        }
                    }

                }else{
                    binding.imageProfil.load("https://goo.gl/gEgYUd")
                }
            }
        }
    }

}