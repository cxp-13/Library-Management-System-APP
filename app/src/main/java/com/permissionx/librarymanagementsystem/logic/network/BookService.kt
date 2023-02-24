package com.permissionx.librarymanagementsystem.logic.network

import com.permissionx.librarymanagementsystem.logic.model.*
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
        @Body book: Book
    ): Call<Meta>

    @GET("book/delete")
    fun deleteBook(
        @Header("Authorization") token: String,
        @Query("id") id: String
    ): Call<Meta>

    @POST("book/update")
    fun updateBook(
        @Header("Authorization") token: String,
        @Body book: Book
    ): Call<Meta>

    @GET("book/read")
    fun searchBook(
        @Header("Authorization") token: String,
        @Query("title") title: String
    ): Call<BookResponse>

    @GET("book/info")
    fun searchBookInfo(
        @Header("Authorization") token: String,
        @Query("id") id: String
    ): Call<BookInfoResponse>

    @GET("users/borrow")
    fun borrowBook(
        @Header("Authorization") token: String,
        @Query("book_id") bookId: String,
        @Query("user_id") userId: String,
        @Query("return_date") returnDate: String
    ): Call<MetaResponse>

    @GET("book/userborrow")
    fun searchUserBook(
        @Header("Authorization") token: String,
        @Query("user_id") userId: String
    ): Call<BookResponse>


}