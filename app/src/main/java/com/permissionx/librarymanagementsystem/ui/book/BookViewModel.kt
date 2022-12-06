package com.permissionx.librarymanagementsystem.ui.book

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.permissionx.librarymanagementsystem.logic.Repository
import com.permissionx.librarymanagementsystem.logic.model.BookResponse

class BookViewModel : ViewModel() {


    //当前选中的图书
    val bookLiveData = MutableLiveData<BookResponse.Book>()

    private val queryTitle = MutableLiveData<String>()


    val books = ArrayList<BookResponse.Book>()

    val booksLiveData: LiveData<List<BookResponse.Book>> =
        Transformations.switchMap(queryTitle) {
            Repository.searchBook(it)
        }

    suspend fun getAllBooks() =
        Repository.getAllBook()

    suspend fun getAllBooksById(id: Long) =
        Repository.getAllBookById(id)

    suspend fun addBook(book: BookResponse.Book) = Repository.addBook(book)

    suspend fun deleteBook(book: BookResponse.Book) = Repository.deleteBook(book)

    suspend fun updateBook(book: BookResponse.Book) = Repository.updateBook(book)

     fun searchBook(title: String) {
        queryTitle.value = title
    }

}