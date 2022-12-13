package com.permissionx.librarymanagementsystem.ui.user

import androidx.fragment.app.FragmentManager
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.permissionx.librarymanagementsystem.logic.Repository
import com.permissionx.librarymanagementsystem.logic.dao.UserSPDao
import com.permissionx.librarymanagementsystem.logic.model.TokenResponse
import com.permissionx.librarymanagementsystem.logic.model.UserResponse

class UserModel : ViewModel() {

    private var _user = MutableLiveData<UserResponse.User>()

    private var _newUser = MutableLiveData<UserResponse.User>()

    var supportFragmentManager: FragmentManager? = null

    //抽屉的用户界面展示
    val userLiveData = Transformations.switchMap(_user) {
        Repository.login(it.name, it.password)
    }

    //注册的用户token
    val tokenLiveData = Transformations.switchMap(_newUser) {
        Repository.registered(mapOf("name" to it.name, "password" to it.password))
    }
//网络请求
    fun login(name: String, pwd: String) {
        _user.value = UserResponse.User(name = name, password = pwd)
    }

    fun registered(name: String, pwd: String) {
        _newUser.value = UserResponse.User(name = name, password = pwd)
    }
//share preference
    fun saveUser(user: UserResponse.User) {
        Repository.saveUser(user)
    }

    fun getUser(name: String): UserResponse.User = Repository.getUser(name)

    fun isUserSaved(name: String, pwd: String) = Repository.isUserSaved(name, pwd)
//数据库
     fun getUserDataBase(name: String): UserResponse.User? = Repository.getUserDataBase(name)

     fun saveUserDataBase(user: UserResponse.User) {
        Repository.saveUserDataBase(user)
    }
}