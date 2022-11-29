package com.permissionx.librarymanagementsystem.logic.dao

import android.content.Context
import android.content.SharedPreferences
import androidx.core.content.edit

import com.google.gson.Gson
import com.permissionx.librarymanagementsystem.LibraryManagementSystemApplication
import com.permissionx.librarymanagementsystem.logic.model.UserResponse

object UserSPDao {

    // TODO: 用户登录的逻辑和函数的参数存在问题 
    fun getUser(name:String): UserResponse.User {
        val user = sharePreference().getString(name, "")
        return Gson().fromJson(user, UserResponse.User::class.java)
    }

    fun saveUser(user: UserResponse.User) {
        sharePreference().edit {
            putString(user.name, Gson().toJson(user))
        }
    }

    fun isUserSaved(name:String) = sharePreference().contains(name)

    fun sharePreference(): SharedPreferences {
        return LibraryManagementSystemApplication.context.getSharedPreferences(
            "test",
            Context.MODE_PRIVATE
        )
    }


}