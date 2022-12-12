package com.permissionx.librarymanagementsystem.ui.book

import android.opengl.Visibility
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.os.bundleOf
import androidx.navigation.NavController
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton
import com.permissionx.librarymanagementsystem.R
import com.permissionx.librarymanagementsystem.logic.model.BookResponse
import com.permissionx.librarymanagementsystem.ui.user.UserModel
import com.permissionx.librarymanagementsystem.util.showSnackbar
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Date
import kotlin.concurrent.timerTask

class BookAdapter(
    val books: List<BookResponse.Book>?,
    val viewModel: BookViewModel,
    val userModel: UserModel,
    val navController: NavController,
    val showReturnDate: Boolean,
    val showBtn: Boolean = true
) :
    RecyclerView.Adapter<BookAdapter.ViewHolder>() {

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val bookId = view.findViewById<TextView>(R.id.book_id)
        val bookName = view.findViewById<TextView>(R.id.book_name)
        val bookType = view.findViewById<TextView>(R.id.book_type)
        val bookReturnDate = view.findViewById<TextView>(R.id.book_return_date)
        val deleteBookFab = view.findViewById<ExtendedFloatingActionButton>(R.id.delete_book_fab)
        val updateBookFab = view.findViewById<ExtendedFloatingActionButton>(R.id.update_book_fab)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflate = LayoutInflater.from(parent.context).inflate(R.layout.book_item, parent, false)
        val holder = ViewHolder(inflate)
//        搜索界面不显示
        if (!showBtn) {
            holder.deleteBookFab.visibility = View.GONE
            holder.updateBookFab.visibility = View.GONE
        }
        holder.itemView.setOnClickListener {
            val position = holder.bindingAdapterPosition
            val book = books?.get(position)
//            存储选中的图书，方便后续的借阅和删除
            viewModel.bookLiveData.value = book
//            图书详情页是否显示借阅按钮
            val bundle = bundleOf("showBorrowingBtn" to showReturnDate)
            navController.navigate(R.id.bookInfoFragment, bundle)
        }
        return holder
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val book = books?.get(position)
        holder.bookId.text = book?.id.toString()
        holder.bookName.text = book?.title
        holder.bookType.text = book?.category
        if (showReturnDate) {
            val date = Date(book!!.returnDate)
            val simpleDateFormat = SimpleDateFormat("yyyy-MM-dd")
            holder.bookReturnDate.text = simpleDateFormat.format(date)
        } else {
            holder.bookReturnDate.visibility = View.GONE
        }
//删除图书
        holder.deleteBookFab.setOnClickListener {
            val user = userModel.userLiveData.value?.getOrNull()
            if (user != null) {
                CoroutineScope(Dispatchers.IO).launch {
                    if (book != null) {
                        viewModel.deleteBook(book)
                    }
                }
                holder.bookId.showSnackbar("The deletion was successful")
            } else {
                holder.bookId.showSnackbar("The user is not logged in")
            }
        }
        //更新图书
        holder.updateBookFab.setOnClickListener {
            val user = userModel.userLiveData.value?.getOrNull()
            if (true || user != null) {
                navController.navigate(
                    R.id.action_bookFragment_to_addBookFragment,
                    bundleOf("title" to book?.title, "body" to book?.body)
                )
            } else {
                holder.bookId.showSnackbar("The user is not logged in")
            }
        }
    }

    override fun getItemCount(): Int {
        return books?.size!!
    }
}