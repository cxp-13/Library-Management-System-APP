package com.permissionx.librarymanagementsystem.ui.book

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.permissionx.librarymanagementsystem.logic.Repository
import com.permissionx.librarymanagementsystem.logic.model.BookResponse

class BookViewModel : ViewModel() {


    //    用户token
    private var token: String? = null

    fun setToken(token: String) {
        this.token = token
    }

    //图书详情
    private val _bookTitle = MutableLiveData<String>()

    val bookInfo = Transformations.switchMap(_bookTitle) {
        Repository.searchBookInfo(this.token!!, it)
    }

    //展示的全部图书


    private val _all = MutableLiveData<String>()

    private val _showBooks = Transformations.switchMap(_all) {
        Repository.searchBook(this.token!!, it)
    }

    val showBooks: LiveData<Result<List<BookResponse.Book>>>
        get() = _showBooks

    //展示的个人借阅的全部图书
    private val _userId = MutableLiveData<String>()

    val showMyBooks = Transformations.switchMap(_userId) {
        Repository.searchUserBook(this.token!!, it)
    }

    //搜索的图书
    private val _queryTitle = MutableLiveData<String>()

    val books = ArrayList<BookResponse.Book>()

    val queryBooks: LiveData<Result<List<BookResponse.Book>>> =
        Transformations.switchMap(_queryTitle) {
            Repository.searchBook(this.token!!, it)
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
        book: BookResponse.Book
    ) = Repository.addBook(this.token!!, book)

    suspend fun deleteBook(
        title: String
    ) = Repository.deleteBook(this.token!!, title)

    suspend fun updateBook(
        book: BookResponse.Book
    ) = Repository.updateBook(this.token!!, book)

    fun searchBook(
        title: String
    ) {
        _queryTitle.value = title
    }

    fun searchBookInfo(
        title: String
    ) {
        _bookTitle.value = title
    }

    fun searchUserBook(
        userId: String
    ) {
        _userId.value = userId
    }

    suspend fun borrowBook(
        title: String,
        userId: String,
        returnTime: String
    ) = Repository.borrowBook(this.token!!, title, userId, returnTime)
}