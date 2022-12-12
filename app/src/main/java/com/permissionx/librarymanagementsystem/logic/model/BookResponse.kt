package com.permissionx.librarymanagementsystem.logic.model

import android.os.Parcelable
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
        @SerializedName("book_borrowers_id") @ColumnInfo(name = "book_borrowers_id") var bookBorrowersId: String = "",
        @SerializedName("bookshelf_location") @ColumnInfo(name = "bookshelf_location") val bookshelfLocation: String = "",
        val category: String = "",
        val count: Long = 0,
        @SerializedName("return_date") @ColumnInfo(name = "return_date") var returnDate: Long = 0,
        val title: String,
    )

    data class Meta(
        val msg: String,
        val status: Int
    )
}