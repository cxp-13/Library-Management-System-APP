package com.permissionx.librarymanagementsystem.ui.book

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.permissionx.librarymanagementsystem.R
import com.permissionx.librarymanagementsystem.logic.model.BookResponse

class BookAdapter(val books: List<BookResponse.Book>?) :
    RecyclerView.Adapter<BookAdapter.ViewHolder>() {

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val bookId = view.findViewById<TextView>(R.id.book_id)

        val bookName = view.findViewById<TextView>(R.id.book_name)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflate = LayoutInflater.from(parent.context).inflate(R.layout.book_item, parent, false)
        return ViewHolder(inflate)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val book = books?.get(position)
        holder.bookId.text = book?.id.toString()
        holder.bookName.text = book?.title
    }

    override fun getItemCount(): Int {
        return books?.size!!
    }
}