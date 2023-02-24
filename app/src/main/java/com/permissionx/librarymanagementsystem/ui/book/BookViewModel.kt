package com.permissionx.librarymanagementsystem.ui.book

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.permissionx.librarymanagementsystem.LibraryManagementSystemApplication
import com.permissionx.librarymanagementsystem.logic.Repository
import com.permissionx.librarymanagementsystem.logic.model.Book

class BookViewModel : ViewModel() {






    //图书详情
    private val _bookId = MutableLiveData<String>()

    val bookInfo = Transformations.switchMap(_bookId) {
        Repository.searchBookInfo(LibraryManagementSystemApplication.token, it)
    }

    //展示的全部图书
    private val _all = MutableLiveData<String>()

    private val _showBooks = Transformations.switchMap(_all) {
        Repository.searchBook(LibraryManagementSystemApplication.token, it)
    }

    val showBooks: LiveData<Result<List<Book>>>
        get() = _showBooks

    //展示的个人借阅的全部图书
    private val _userId = MutableLiveData<String>()

    val showMyBooks = Transformations.switchMap(_userId) {
        Repository.searchUserBook(LibraryManagementSystemApplication.token, it)
    }

    //搜索的图书
    private val _queryTitle = MutableLiveData<String>()

    val books = ArrayList<Book>()

    val queryBooks: LiveData<Result<List<Book>>> =
        Transformations.switchMap(_queryTitle) {
            Repository.searchBook(LibraryManagementSystemApplication.token, it)
        }


//    数据库
//    fun addBookDataBase(book: BookResponse.Book) = Repository.addBookDataBase(book)
//
//    fun deleteBookDataBase(book: BookResponse.Book) = Repository.deleteBookDataBase(book)
//
//    fun updateBookDataBase(book: BookResponse.Book) = Repository.updateBookDataBase(book)
//
//    fun searchBookDataBase(title: String) {
//        _queryTitle.value = title
//    }

    fun refreshBooks() {
        _all.value = "all"
    }

    suspend fun addBook(
        book: Book
    ) = Repository.addBook(LibraryManagementSystemApplication.token, book)

    suspend fun deleteBook(
        id: String
    ) = Repository.deleteBook(LibraryManagementSystemApplication.token, id)

    suspend fun updateBook(
        book: Book
    ) = Repository.updateBook(LibraryManagementSystemApplication.token, book)

    fun searchBook(
        title: String
    ) {
        _queryTitle.value = title
    }

    fun searchBookInfo(
        id: String
    ) {
        _bookId.value = id
    }

    fun searchUserBook(
        userId: String
    ) {
        _userId.value = userId
    }

    suspend fun borrowBook(
        bookId: String,
        userId: String,
        returnDate: String
    ) = Repository.borrowBook(LibraryManagementSystemApplication.token, bookId, userId, returnDate)
}