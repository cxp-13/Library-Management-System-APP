package com.permissionx.librarymanagementsystem.logic

import androidx.lifecycle.liveData
import com.permissionx.librarymanagementsystem.logic.dao.UserSPDao
import com.permissionx.librarymanagementsystem.logic.database.AppDatabase
import com.permissionx.librarymanagementsystem.logic.model.BookResponse
import com.permissionx.librarymanagementsystem.logic.model.UserResponse
import com.permissionx.librarymanagementsystem.logic.network.LibraryManagementSystemNetwork
import kotlinx.coroutines.*
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

    suspend fun searchBook(title: String) =
        withContext(Dispatchers.IO) {
            bookDao?.searchBook(title)
        }

    suspend fun getAllBook() = withContext(Dispatchers.IO) {
        bookDao?.getAllBook()
    }



    suspend fun getAllBookById(userId: Long) = withContext(Dispatchers.IO) {
        bookDao?.getAllBookById(userId)
    }


    fun login(name: String, pwd: String) = fire(Dispatchers.IO) {
        coroutineScope {
            val response = async { LibraryManagementSystemNetwork.login(name, pwd) }.await()
            if (response.meta.msg == "登录成功") {
                Result.success(response.user)
            } else {
                Result.failure(java.lang.RuntimeException("${response.meta.msg}"))
            }
        }
    }

    fun registered(name: String, pwd: String) = fire(Dispatchers.IO) {
        coroutineScope {
            val response = async { LibraryManagementSystemNetwork.registered(name, pwd) }.await()
            if (response.meta.msg == "注册成功") {
                Result.success(response.token)
            } else {
                Result.failure(java.lang.RuntimeException("${response.meta.msg}"))
            }
        }
    }


    private fun <T> fire(context: CoroutineContext, block: suspend () -> Result<T>) =
        liveData<Result<T>>(context) {
            val result = try {
                block()
            } catch (e: Exception) {
                Result.failure(e)
            }
            this.emit(result)

        }


}