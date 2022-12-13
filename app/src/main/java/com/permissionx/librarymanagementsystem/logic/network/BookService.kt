package com.permissionx.librarymanagementsystem.logic.network

import com.permissionx.librarymanagementsystem.logic.model.BookInfoResponse
import com.permissionx.librarymanagementsystem.logic.model.BookResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Query

interface BookService {
    @POST("book/publish")
    fun addBook(
        @Header("Authorization") token: String,
        @Body book: BookResponse.Book
    ): Call<BookResponse.Meta>

    @GET("book/delete")
    fun deleteBook(
        @Header("Authorization") token: String,
        @Query("title") title: String
    ): Call<BookResponse.Meta>

    @POST("book/update")
    fun updateBook(
        @Header("Authorization") token: String,
        @Body book: BookResponse.Book
    ): Call<BookResponse.Meta>

    @GET("book/read")
    fun searchBook(
        @Header("Authorization") token: String,
        @Query("title") title: String
    ): Call<BookResponse>

    @GET("book/info")
    fun searchBookInfo(
        @Header("Authorization") token: String,
        @Query("title") title: String
    ): Call<BookInfoResponse>

    @GET("user/borrow")
    fun borrowBook(
        @Header("Authorization") token: String,
        @Query("title") title: String,
        @Query("user_id") userId: String,
        @Query("return_time") returnTime: String
    ): Call<BookResponse.Meta>

    @GET("book/user")
    fun searchUserBook(
        @Header("Authorization") token: String,
        @Query("user_id") userId: String
    ): Call<BookResponse>


}