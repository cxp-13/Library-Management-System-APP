package com.permissionx.librarymanagementsystem.logic.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Entity
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.permissionx.librarymanagementsystem.logic.model.BookResponse


@Dao
interface BookDao {
    @Query("select * from Book where title like '%' || :title || '%'")
    suspend fun searchBook(title: String): List<BookResponse.Book>

    @Insert
    suspend fun addBook(book: BookResponse.Book)

    @Update
    suspend fun updateBook(book: BookResponse.Book)

    @Delete
    suspend fun deleteBook(book: BookResponse.Book)

    @Query("select * from Book")
    suspend fun getAllBook(): List<BookResponse.Book>

    @Query("select * from Book where book_borrowers_id = :userId")
    suspend fun getAllBookById(userId: String): List<BookResponse.Book>


}