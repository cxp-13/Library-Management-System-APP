package com.permissionx.librarymanagementsystem.logic.model

data class BookResponse(
    val data: Books = Books(books = emptyList()),
    val meta: Meta
) {
    data class Books(val books: List<Book>)
}