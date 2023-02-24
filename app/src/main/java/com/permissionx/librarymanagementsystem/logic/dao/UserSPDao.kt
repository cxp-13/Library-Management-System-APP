package com.permissionx.librarymanagementsystem.logic.dao

import android.content.Context
import android.content.SharedPreferences
import androidx.core.content.edit

import com.google.gson.Gson
import com.permissionx.librarymanagementsystem.LibraryManagementSystemApplication
import com.permissionx.librarymanagementsystem.logic.model.User

object UserSPDao {

    // TODO: 用户登录的逻辑和函数的参数存在问题 
    fun getUser(name:String): User {
        val user = sharePreference().getString(name, "")
        return Gson().fromJson(user, User::class.java)
    }

    fun saveUser(user: User) {
        sharePreference().edit {
            putString(user.username, Gson().toJson(user))
        }
    }

    fun isUserSaved(name:String, pwd:String):Boolean {
        return sharePreference().contains(name) &&
                getUser(name).password == pwd

    }

    fun sharePreference(): SharedPreferences {
        return LibraryManagementSystemApplication.context.getSharedPreferences(
            "test",
            Context.MODE_PRIVATE
        )
    }


}