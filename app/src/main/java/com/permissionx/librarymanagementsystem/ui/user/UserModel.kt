package com.permissionx.librarymanagementsystem.ui.user

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.permissionx.librarymanagementsystem.logic.Repository
import com.permissionx.librarymanagementsystem.logic.model.UserResponse

class UserModel : ViewModel() {

    private val user = MutableLiveData<UserResponse.User>()

    val username = ""
    val password = ""

//    val userLiveData = Transformations.switchMap(user){
//
//    }

    fun login(name:String, pwd:Long){
        user.value = UserResponse.User(name = name, password = pwd)
    }


}