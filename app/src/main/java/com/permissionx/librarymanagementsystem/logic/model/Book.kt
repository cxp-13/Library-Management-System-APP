package com.permissionx.librarymanagementsystem.logic.model

import com.google.gson.annotations.SerializedName

data class Book(
    val id: String = "",
    val body: String = "",
    @SerializedName("book_borrowers_id") var bookBorrowersId: List<String> = emptyList(),
    @SerializedName("bookshelf_location") val bookshelfLocation: String = "",
    val category: String = "",
    val count: Long = 0,
    @SerializedName("return_date") var returnDate: List<String> = emptyList(),
    val title: String = "",
)