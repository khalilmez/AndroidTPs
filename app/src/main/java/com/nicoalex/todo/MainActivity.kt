package com.nicoalex.todo

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.lifecycle.lifecycleScope
import coil.load
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
        setContentView(view)
    }

    public fun getBinding () : ActivityMainBinding {
        return binding
    }

    public fun getCurrentActivity() : Activity{
        return this;
    }

    override fun onResume() {
        super.onResume()
        lifecycleScope.launch {
            val userInfo = Api.userWebService.getInfo().body()!!
            binding.imageProfil.load(userInfo.avatar)
            binding.Profile.text = "${userInfo.firstName} ${userInfo.lastName} \n ${userInfo.email}"
        }
    }
}