package com.permissionx.librarymanagementsystem.logic.network

import android.util.Log
import com.permissionx.librarymanagementsystem.logic.model.Book
import com.permissionx.librarymanagementsystem.logic.model.TokenResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

object LibraryManagementSystemNetwork {


    val userService = ServiceCreator.create<UserService>()

    val bookService = ServiceCreator.create<BookService>()


    suspend fun login(hashMap: Map<String, String>): TokenResponse = userService.login(hashMap).await()

    suspend fun registered(hashMap: Map<String, String>) =
        userService.registered(hashMap).await()

    suspend fun addBook(
        token: String,
        book: Book
    ) = bookService.addBook("Bearer $token", book).await()

    suspend fun deleteBook(
        token: String,
        id: String
    ) = bookService.deleteBook("Bearer $token", id).await()

    suspend fun updateBook(
        token: String,
        book: Book
    ) = bookService.updateBook("Bearer $token", book).await()

    suspend fun searchBook(
        token: String,
        title: String
    ) = bookService.searchBook("Bearer $token", title).await()

    suspend fun searchBookInfo(
        token: String,
        id: String
    ) = bookService.searchBookInfo("Bearer $token", id).await()

    suspend fun borrowBook(
        token: String,
        bookId: String,
        userId: String,
        returnDate: String
    ) = bookService.borrowBook("Bearer $token", bookId, userId, returnDate).await()

    suspend fun searchUserBook(
        token: String,
        userId: String
    ) = bookService.searchUserBook("Bearer $token", userId).await()

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