package com.permissionx.librarymanagementsystem

import android.app.Application
import android.content.Context
import androidx.fragment.app.FragmentManager

class LibraryManagementSystemApplication : Application() {
    companion object {
        lateinit var context: Context
        //    用户token
        var token: String = "default_token"
    }

    override fun onCreate() {
        super.onCreate()
        context = applicationContext
    }

}