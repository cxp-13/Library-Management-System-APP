package com.permissionx.librarymanagementsystem.logic.database

import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.permissionx.librarymanagementsystem.LibraryManagementSystemApplication
import com.permissionx.librarymanagementsystem.logic.dao.UserDao
import com.permissionx.librarymanagementsystem.logic.model.UserResponse

@Database(entities = [UserResponse.User::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao


    companion object {
        private var INSTANCE: AppDatabase? = null
        fun getInstance(): AppDatabase? {
            if (INSTANCE == null) {
                synchronized(this) {
                    if (INSTANCE == null) {
                        INSTANCE = Room.databaseBuilder(
                            LibraryManagementSystemApplication.context,
                            AppDatabase::class.java,
                            "database-lbm"
                        ).build()
                    }
                }
            }
            return INSTANCE
        }
    }
}