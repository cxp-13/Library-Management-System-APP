package com.permissionx.librarymanagementsystem.logic.model


data class BookInfoResponse(
    val data: BookInfo,
    val meta: Meta
) {
    data class BookInfo(val book: Book)
}