package com.permissionx.librarymanagementsystem.logic

import com.permissionx.librarymanagementsystem.logic.dao.UserSPDao
import com.permissionx.librarymanagementsystem.logic.model.UserResponse

object Repository {
    fun saveUser(user: UserResponse.User) = UserSPDao.saveUser(user)

    fun getUser(name: String) = UserSPDao.getUser(name)

    fun isUserSaved(name: String) = UserSPDao.isUserSaved(name)


}