package com.permissionx.librarymanagementsystem.logic.network

import android.util.Log
import com.permissionx.librarymanagementsystem.logic.model.BookResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

object LibraryManagementSystemNetwork {


    val userService = ServiceCreator.create<UserService>()

    val bookService = ServiceCreator.create<BookService>()


    suspend fun login(name: String, pwd: String) = userService.login(name, pwd).await()

    suspend fun registered(hashMap: Map<String, String>) =
        userService.registered(hashMap).await()

    suspend fun addBook(
        token: String,
        book: BookResponse.Book
    ) = bookService.addBook(token, book).await()

    suspend fun deleteBook(
        token: String,
        title: String
    ) = bookService.deleteBook(token, title).await()

    suspend fun updateBook(
        token: String,
        book: BookResponse.Book
    ) = bookService.updateBook(token,book).await()

    suspend fun searchBook(
        token: String,
        title: String
    ) = bookService.searchBook(token, title).await()

    suspend fun searchBookInfo(
        token: String,
        title: String
    ) = bookService.searchBookInfo(token, title).await()

    suspend fun borrowBook(
        token: String,
        title: String,
        userId: String,
        returnTime: String
    ) = bookService.borrowBook(token, title, userId, returnTime).await()

    suspend fun searchUserBook(
        token: String,
        userId: String
    ) = bookService.searchUserBook(token, userId).await()

    private suspend fun <T> Call<T>.await(): T {
        return suspendCoroutine {
            enqueue(object : Callback<T> {
                override fun onResponse(call: Call<T>, response: Response<T>) {
                    val body = response.body()
                    Log.d("test", "onResponse: $body")
                    if (body == null) it.resumeWithException(RuntimeException("body is null"))
                    else
                        it.resume(body)
                }
                override fun onFailure(call: Call<T>, t: Throwable) {
                    it.resumeWithException(t)
                }
            })
        }
    }
}