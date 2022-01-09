package com.nicoalex.todo.User

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.nicoalex.todo.R
import com.nicoalex.todo.databinding.ActivityAuthenticationBinding

class AuthenticationActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_authentication)
    }
}