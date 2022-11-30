package com.permissionx.librarymanagementsystem.logic

import com.permissionx.librarymanagementsystem.logic.dao.UserSPDao
import com.permissionx.librarymanagementsystem.logic.database.AppDatabase
import com.permissionx.librarymanagementsystem.logic.model.UserResponse
import kotlinx.coroutines.*

object Repository {


    val userDao = AppDatabase.getInstance()?.userDao()

    fun saveUser(user: UserResponse.User) = UserSPDao.saveUser(user)

    fun getUser(name: String) = UserSPDao.getUser(name)

    fun isUserSaved(name: String, pwd: String): Boolean {
        return UserSPDao.isUserSaved(name, pwd)
    }

    fun saveUserDataBase(user: UserResponse.User) {
        CoroutineScope(Dispatchers.IO).launch {

            userDao?.saveUser(user)

        }
    }

    suspend fun getUserDataBase(name: String): UserResponse.User? {


        return withContext(Dispatchers.IO) {
            userDao?.getUser(name)
        }

    }


}