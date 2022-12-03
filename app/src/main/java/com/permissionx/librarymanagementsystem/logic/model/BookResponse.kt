package com.permissionx.librarymanagementsystem.logic.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import java.util.Date

data class BookResponse(
    val books: List<Book>,
    val meta: Meta
) {

    @Entity
    data class Book(
        @PrimaryKey(autoGenerate = true) val id: Long = 0,
        val body: String = "",
        @SerializedName("book_borrowers_id") @ColumnInfo(name = "book_borrowers_id") val bookBorrowersId: Long = 0,
        @SerializedName("bookshelf_location") @ColumnInfo(name = "bookshelf_location") val bookshelfLocation: String = "",
        val category: String = "",
        val count: Long = 0,
        @SerializedName("return_time") @ColumnInfo(name = "return_time") val returnTime: String = "",
        val title: String,
    )

    data class Meta(
        val msg: String,
        val status: Int
    )
}