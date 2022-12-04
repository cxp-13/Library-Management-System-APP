package com.permissionx.librarymanagementsystem.ui.book

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.permissionx.librarymanagementsystem.logic.Repository
import com.permissionx.librarymanagementsystem.logic.model.BookResponse

class BookViewModel : ViewModel() {


    val booksLiveData = MutableLiveData<List<BookResponse.Book>>()

    val bookLiveData = MutableLiveData<BookResponse.Book>()

    suspend fun getAllBooks() =
        Repository.getAllBook()

    suspend fun getAllBooksById(id: Long) =
        Repository.getAllBookById(id)

    suspend fun addBook(book: BookResponse.Book) = Repository.addBook(book)

    suspend fun deleteBook(book: BookResponse.Book) = Repository.deleteBook(book)

    suspend fun updateBook(book: BookResponse.Book) = Repository.updateBook(book)

    suspend fun searchBook(title: String) = Repository.searchBook(title)

}