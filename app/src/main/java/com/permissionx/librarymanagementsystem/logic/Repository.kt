package com.permissionx.librarymanagementsystem.logic

import android.util.Log
import androidx.lifecycle.liveData
import com.permissionx.librarymanagementsystem.logic.dao.UserSPDao
import com.permissionx.librarymanagementsystem.logic.database.AppDatabase
import com.permissionx.librarymanagementsystem.logic.model.BookResponse
import com.permissionx.librarymanagementsystem.logic.model.UserResponse
import com.permissionx.librarymanagementsystem.logic.network.LibraryManagementSystemNetwork
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.withContext
import kotlin.coroutines.CoroutineContext

object Repository {


    val userDao = AppDatabase.getDatabase()?.userDao()

    val bookDao = AppDatabase.getDatabase()?.bookDao()

    fun saveUser(user: UserResponse.User) = UserSPDao.saveUser(user)

    fun getUser(name: String) = UserSPDao.getUser(name)

    fun isUserSaved(name: String, pwd: String): Boolean {
        return UserSPDao.isUserSaved(name, pwd)
    }

    suspend fun saveUserDataBase(user: UserResponse.User) {
        withContext(Dispatchers.IO) {
            userDao?.saveUser(user)
        }
    }

    suspend fun getUserDataBase(name: String): UserResponse.User? {
        return withContext(Dispatchers.IO) {
            userDao?.getUser(name)
        }
    }

    suspend fun addBook(book: BookResponse.Book) {
        withContext(Dispatchers.IO) {
            bookDao?.addBook(book)
        }
    }

    suspend fun deleteBook(book: BookResponse.Book) {
        withContext(Dispatchers.IO) {
            bookDao?.deleteBook(book)
        }
    }

    suspend fun updateBook(book: BookResponse.Book) {
        withContext(Dispatchers.IO) {
            bookDao?.updateBook(book)
        }
    }

    fun searchBook(title: String) =
        liveData(Dispatchers.IO) {
            val books =
                bookDao?.searchBook(title)
            if (books != null) {
                this.emit(books)
            }
        }


    suspend fun getAllBook() = withContext(Dispatchers.IO) {
        bookDao?.getAllBook()
    }


    suspend fun getAllBookById(userId: String) = withContext(Dispatchers.IO) {
        bookDao?.getAllBookById(userId)
    }


    fun login(name: String, pwd: String) = fire(Dispatchers.IO) {
        coroutineScope {
            val response = withContext(Dispatchers.Default) {
                LibraryManagementSystemNetwork.login(
                    name,
                    pwd
                )
            }
            Log.d("test", "login: $response")
            if (response.meta.msg == "登录成功") {
                Result.success(response.user)
            } else {
                Result.failure(java.lang.RuntimeException("response meta is ${response.meta}"))
            }
        }
    }

    fun registered(hashMap: Map<String, String>) = fire(Dispatchers.IO) {
        coroutineScope {
            val response =
                withContext(Dispatchers.IO) { LibraryManagementSystemNetwork.registered(hashMap) }
            Log.d("test", "registered: $response")
            if (response.meta.msg == "注册成功") {
                Result.success(response.token)
            } else {
                Result.failure(java.lang.RuntimeException("response meta is ${response.meta}"))
            }
        }


    }


    private fun <T> fire(context: CoroutineContext, block: suspend () -> Result<T>) =
        liveData(context) {
            val result = try {
                block()
            } catch (e: Exception) {
                Result.failure(e)
            }
            this.emit(result)

        }
}