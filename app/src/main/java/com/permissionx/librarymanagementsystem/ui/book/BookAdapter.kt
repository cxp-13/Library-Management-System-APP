package com.permissionx.librarymanagementsystem.ui.book

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.navigation.NavController
import androidx.recyclerview.widget.RecyclerView
import com.permissionx.librarymanagementsystem.R
import com.permissionx.librarymanagementsystem.logic.model.BookResponse

class BookAdapter(
    val books: List<BookResponse.Book>?,
    val viewModel: BookViewModel,
    val navController: NavController
) :
    RecyclerView.Adapter<BookAdapter.ViewHolder>() {

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val bookId = view.findViewById<TextView>(R.id.book_id)
        val bookName = view.findViewById<TextView>(R.id.book_name)
        val bookType = view.findViewById<TextView>(R.id.book_type)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflate = LayoutInflater.from(parent.context).inflate(R.layout.book_item, parent, false)
        val holder = ViewHolder(inflate)
        holder.itemView.setOnClickListener {
            val position = holder.bindingAdapterPosition
            val book = books?.get(position)
            viewModel.bookLiveData.value = book
            navController.navigate(R.id.action_bookFragment_to_bookInfoFragment)
        }
        return holder
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val book = books?.get(position)
        holder.bookId.text = book?.id.toString()
        holder.bookName.text = book?.title
        holder.bookType.text = book?.category
    }

    override fun getItemCount(): Int {
        return books?.size!!
    }
}