package com.permissionx.librarymanagementsystem.logic.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.permissionx.librarymanagementsystem.logic.model.User
import com.permissionx.librarymanagementsystem.logic.model.UserResponse

@Dao
interface UserDao {

    @Query("select * from user where username = :username limit 1")
   suspend fun getUser(name:String): User

    @Insert
    suspend fun saveUser(user: User)
}