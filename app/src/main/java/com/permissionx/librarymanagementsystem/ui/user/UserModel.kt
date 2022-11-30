package com.permissionx.librarymanagementsystem.ui.user

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.permissionx.librarymanagementsystem.logic.Repository
import com.permissionx.librarymanagementsystem.logic.dao.UserSPDao
import com.permissionx.librarymanagementsystem.logic.model.UserResponse

class UserModel : ViewModel() {

    val user = MutableLiveData<UserResponse.User>()

    val username = ""
    val password = ""

//    val userLiveData = Transformations.switchMap(user){
//
//    }


    fun saveUser(user: UserResponse.User) {
        this.user.value = user
        Repository.saveUser(user)
    }


    fun getUser(name: String): UserResponse.User = Repository.getUser(name)

    fun isUserSaved(name: String, pwd: String) = Repository.isUserSaved(name, pwd)

    suspend fun getUserDataBase(name: String): UserResponse.User? = Repository.getUserDataBase(name)

    suspend fun saveUserDataBase(user: UserResponse.User) {
        Repository.saveUserDataBase(user)
    }


//    fun login(name: String, pwd: Long) {
//        user.value = UserResponse.User(name = name, password = pwd)
//    }


}