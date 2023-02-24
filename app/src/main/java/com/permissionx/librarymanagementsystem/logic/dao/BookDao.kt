package com.permissionx.librarymanagementsystem.logic.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.permissionx.librarymanagementsystem.logic.model.Book
import com.permissionx.librarymanagementsystem.logic.model.BookResponse


@Dao
interface BookDao {
    @Query("select * from Book where title like '%' || :title || '%'")
    suspend fun searchBook(title: String): List<Book>

    @Query("select * from Book where id = :id")
    suspend fun getBook(id: String): Book

    @Insert
    suspend fun addBook(book: Book)

    @Update
    suspend fun updateBook(book: Book)

    @Delete
    suspend fun deleteBook(book: Book)

    @Query("select * from Book")
    suspend fun getAllBook(): List<Book>

    @Query("select * from Book where book_borrowers_id = :userId")
    suspend fun getAllBookById(userId: String): List<Book>


}