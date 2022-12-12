package com.permissionx.librarymanagementsystem.logic.database

import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.permissionx.librarymanagementsystem.LibraryManagementSystemApplication
import com.permissionx.librarymanagementsystem.logic.dao.BookDao
import com.permissionx.librarymanagementsystem.logic.dao.UserDao
import com.permissionx.librarymanagementsystem.logic.model.BookResponse
import com.permissionx.librarymanagementsystem.logic.model.UserResponse

@Database(entities = [UserResponse.User::class, BookResponse.Book::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao

    abstract fun bookDao(): BookDao

    companion object {
        private var instance: AppDatabase? = null

        @Synchronized
        fun getDatabase(): AppDatabase? {
            instance?.let {
                return it
            }
            return Room.databaseBuilder(
                LibraryManagementSystemApplication.context,
                AppDatabase::class.java,
                "database-lbm"
            ).fallbackToDestructiveMigration().build().apply {
                instance = this
            }
        }
    }
}