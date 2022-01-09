package com.nicoalex.todo

import android.app.Application
import com.nicoalex.todo.network.Api

class App: Application() {
    override fun onCreate() {
        super.onCreate()
        Api.setUpContext(this)
    }
}
