package com.nicoalex.todo

import android.app.Activity
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.lifecycle.lifecycleScope
import coil.load
import com.nicoalex.todo.databinding.ActivityMainBinding
import com.nicoalex.todo.network.Api
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
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
            binding.imageProfil.load("https://goo.gl/gEgYUd")
            val userInfo = Api.userWebService.getInfo().body()!!
            binding.Profile.text = "${userInfo.firstName} ${userInfo.lastName}"
        }
    }
}