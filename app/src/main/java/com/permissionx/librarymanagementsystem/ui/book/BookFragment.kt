package com.permissionx.librarymanagementsystem.ui.book

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.permissionx.librarymanagementsystem.R
import com.permissionx.librarymanagementsystem.databinding.FragmentBookBinding
import com.permissionx.librarymanagementsystem.logic.model.BookResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class BookFragment : Fragment() {

    companion object {
        fun newInstance() = BookFragment()
    }

    private lateinit var viewModel: BookViewModel

    private var _binding: FragmentBookBinding? = null
    private val binding get() = _binding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentBookBinding.inflate(inflater, container, false)


        return binding?.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(BookViewModel::class.java)
        var books = ArrayList<BookResponse.Book>()

//        图书列表展示
        lifecycleScope.launch() {
            val allBooks =
                viewModel.getAllBooks()!!
            books.addAll(allBooks)

            val bookAdapter = BookAdapter(books)
            val gridLayoutManager = GridLayoutManager(newInstance().context, 4)
            val recyclerView = binding?.recyclerView
            recyclerView?.layoutManager = gridLayoutManager
            recyclerView?.adapter = bookAdapter

        }
//图书测试数据
//        val books = mutableListOf(
//            BookResponse.Book(id = 0, title = "dsfsdg"),
//            BookResponse.Book(id = 1, title = "dsfsdg"),
//            BookResponse.Book(id = 2, title = "dsfsdg"),
//            BookResponse.Book(id = 3, title = "dsfsdg"),
//            BookResponse.Book(id = 4, title = "dsfsdg"),
//            BookResponse.Book(id = 5, title = "dsfsdg")
//        )


//        添加图书悬浮按钮
        binding?.fab?.setOnClickListener {
            val navController = findNavController()
            navController.navigate(R.id.action_bookFragment_to_addBookFragment)
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}