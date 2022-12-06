package com.permissionx.librarymanagementsystem.ui.user

import androidx.fragment.app.FragmentManager
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.permissionx.librarymanagementsystem.logic.Repository
import com.permissionx.librarymanagementsystem.logic.dao.UserSPDao
import com.permissionx.librarymanagementsystem.logic.model.UserResponse

class UserModel : ViewModel() {

    private var user = MutableLiveData<UserResponse.User>()

    var supportFragmentManager: FragmentManager? = null


    val username = ""
    val password = ""

    val userLiveData = Transformations.switchMap(user) {
        Repository.login(user.value!!.name, user.value!!.password)
    }

    fun saveUser(user: UserResponse.User) {
        Repository.saveUser(user)
    }


    fun setUser(user: UserResponse.User) {
        this.user.value = user
    }

    // TODO: 给外界的数据应该不变
    fun getUser() = this.user.value


    fun getUser(name: String): UserResponse.User = Repository.getUser(name)

    fun isUserSaved(name: String, pwd: String) = Repository.isUserSaved(name, pwd)

    suspend fun getUserDataBase(name: String): UserResponse.User? = Repository.getUserDataBase(name)

    suspend fun saveUserDataBase(user: UserResponse.User) {
        Repository.saveUserDataBase(user)
    }


    fun login(name: String, pwd: String) {
        Repository.login(name, pwd)
    }

    fun registered(name: String, pwd: String): LiveData<Result<String>> {
        return Repository.registered(name, pwd)
    }


}