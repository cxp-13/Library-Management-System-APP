package com.permissionx.librarymanagementsystem.ui.user

import androidx.fragment.app.FragmentManager
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.permissionx.librarymanagementsystem.logic.Repository
import com.permissionx.librarymanagementsystem.logic.model.TokenResponse
import com.permissionx.librarymanagementsystem.logic.model.User

class UserModel : ViewModel() {

    private var _user = MutableLiveData<User>()

    val userLiveData: LiveData<User>
        get() = _user

    private var _newUser = MutableLiveData<User>()

    var supportFragmentManager: FragmentManager? = null

    //获取token和id

    val loginDataLiveData: LiveData<Result<TokenResponse.Data>> = Transformations.switchMap(_user) {
       Repository.login(mapOf("username" to it.username, "password" to it.password))
    }

    //   获取是否注册成功的信息
    val registeredStatusLiveData = Transformations.switchMap(_newUser) {
        Repository.registered(mapOf("username" to it.username, "password" to it.password))
    }

    fun setUserId(userId: String) {
        _user.value = _user.value?.copy(id = userId)
    }

    //网络请求
    fun login(username: String, password: String) {
        _user.value = User(username = username, password = password)


        Repository.login(mapOf("username" to username, "password" to password)).value
    }

    fun registered(username: String, password: String) {
        _newUser.value = User(username = username, password = password)
    }

    //share preference
    fun saveUser(user: User) {
        Repository.saveUser(user)
    }

    fun getUser(name: String): User = Repository.getUser(name)

    fun isUserSaved(name: String, pwd: String) = Repository.isUserSaved(name, pwd)
//数据库
//     fun getUserDataBase(username: String): UserResponse.User? = Repository.getUserDataBase(username)
//
//     fun saveUserDataBase(user: UserResponse.User) {
//        Repository.saveUserDataBase(user)
//    }
}