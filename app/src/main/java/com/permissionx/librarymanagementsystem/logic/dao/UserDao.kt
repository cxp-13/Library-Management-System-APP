package com.permissionx.librarymanagementsystem.logic.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.permissionx.librarymanagementsystem.logic.model.UserResponse

@Dao
interface UserDao {

    @Query("select * from user where name = :name limit 1")
   suspend fun getUser(name:String): UserResponse.User

    @Insert
    suspend fun saveUser(user: UserResponse.User)
}