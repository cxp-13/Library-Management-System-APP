package com.permissionx.librarymanagementsystem.logic

import android.util.Log
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

    fun saveUserDataBase(user: UserResponse.User) {
        CoroutineScope(Dispatchers.IO).launch {
            userDao?.saveUser(user)
        }
    }

    fun getUserDataBase(name: String): UserResponse.User? {
        var user: UserResponse.User? = null
        CoroutineScope(Dispatchers.IO).launch {
            user = withContext(Dispatchers.Default) {
                userDao?.getUser(name)
            }
        }
        return user
    }


    fun getBookDataBase(id: String) =
        liveData(Dispatchers.IO) {
            val books =
                bookDao?.getBook(id)
            if (books != null) {
                this.emit(books)
            }
        }


    fun addBookDataBase(book: BookResponse.Book) {
        CoroutineScope(Dispatchers.IO).launch {
            bookDao?.addBook(book)
        }
    }

    fun deleteBookDataBase(book: BookResponse.Book) {

        CoroutineScope(Dispatchers.IO).launch {
            bookDao?.deleteBook(book)
        }
    }

    fun updateBookDataBase(book: BookResponse.Book) {
        CoroutineScope(Dispatchers.IO).launch {
            bookDao?.updateBook(book)
        }
    }

    fun searchBookDataBase(title: String) =
        liveData(Dispatchers.IO) {
            val books =
                bookDao?.searchBook(title)
            if (books != null) {
                this.emit(books)
            }
        }


    fun getAllBookDataBase() = liveData(Dispatchers.IO) {
        val books =
            bookDao?.getAllBook()
        if (books != null) {
            this.emit(books)
        }
    }


    fun getAllBookByIdDataBase(userId: String) = liveData(Dispatchers.IO) {
        val books =
            bookDao?.getAllBookById(userId)
        if (books != null) {
            this.emit(books)
        }
    }

    suspend fun addBook(
        token: String,
        book: BookResponse.Book
    ): Result<String> {
        val response = withContext(Dispatchers.IO) {
            LibraryManagementSystemNetwork.addBook(
                token,
                book
            )
        }
        Log.d("test", "addBook: $response")
        return if (response.status == 200) {
            Result.success(response.msg)
        } else {
            Result.failure(java.lang.RuntimeException("response meta is ${response}"))
        }
    }

    suspend fun deleteBook(
        token: String,
        title: String
    ): Result<String> {
        val response = withContext(Dispatchers.IO) {
            LibraryManagementSystemNetwork.deleteBook(
                token,
                title
            )
        }
        Log.d("test", "deleteBook: $response")
        return if (response.status == 200) {
            Result.success(response.msg)
        } else {
            Result.failure(java.lang.RuntimeException("response meta is $response"))
        }
    }

    suspend fun updateBook(
        token: String,
        book: BookResponse.Book
    ): Result<String> {
        val response = withContext(Dispatchers.IO) {
            LibraryManagementSystemNetwork.updateBook(
                token,
                book
            )
        }
        Log.d("test", "updateBook: $response")
        return if (response.status == 200) {
            Result.success(response.msg)
        } else {
            Result.failure(java.lang.RuntimeException("response meta is $response"))
        }
    }

    fun searchBook(
        token: String,
        title: String
    ) = fire(Dispatchers.IO) {
        coroutineScope {
            val response = withContext(Dispatchers.Default) {
                LibraryManagementSystemNetwork.searchBook(
                    token,
                    title
                )
            }
            Log.d("test", "searchBook: $response")
            if (response.meta.status == 200) {
                Result.success(response.books)
            } else {
                Result.failure(java.lang.RuntimeException("response meta is ${response.meta.msg}"))
            }
        }
    }

    fun searchBookInfo(
        token: String,
        title: String
    ) = fire(Dispatchers.IO) {
        coroutineScope {
            val response = withContext(Dispatchers.Default) {
                LibraryManagementSystemNetwork.searchBookInfo(
                    token,
                    title
                )
            }
            Log.d("test", "searchBookInfo: $response")
            if (response.meta.status == 200) {
                Result.success(response.book)
            } else {
                Result.failure(java.lang.RuntimeException("response meta is ${response.meta.msg}"))
            }
        }
    }

    fun searchUserBook(
        token: String,
        userId: String
    ) = fire(Dispatchers.IO) {
        coroutineScope {
            val response = withContext(Dispatchers.Default) {
                LibraryManagementSystemNetwork.searchUserBook(
                    token,
                    userId
                )
            }
            Log.d("test", "searchUserBook: $response")
            if (response.meta.status == 200) {
                Result.success(response.books)
            } else {
                Result.failure(java.lang.RuntimeException("response meta is ${response.meta.msg}"))
            }
        }
    }

    suspend fun borrowBook(
        token: String,
        title: String,
        userId: String,
        returnTime: String
    ) {
        val response = withContext(Dispatchers.IO) {
            LibraryManagementSystemNetwork.borrowBook(
                token,
                title,
                userId,
                returnTime
            )
        }
        Log.d("test", "borrowBook: $response")
        if (response.status == 200) {
            Result.success(response.msg)
        } else {
            Result.failure(java.lang.RuntimeException("response meta is $response"))
        }

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