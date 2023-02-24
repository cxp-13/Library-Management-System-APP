package com.permissionx.librarymanagementsystem.logic.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

data class BookResponse(
    val data: Books,
    val meta: Meta
) {
    data class Books(val books: List<Book>)
}