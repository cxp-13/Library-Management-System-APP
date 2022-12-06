package com.permissionx.librarymanagementsystem.ui.book

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView.Orientation
import androidx.recyclerview.widget.RecyclerView.VERTICAL
import androidx.recyclerview.widget.StaggeredGridLayoutManager
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

    private val viewModel by activityViewModels<BookViewModel>()

    private var _binding: FragmentBookBinding? = null
    private val binding get() = _binding


    var navController: NavController? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentBookBinding.inflate(inflater, container, false)


        return binding?.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        var books = ArrayList<BookResponse.Book>()

//        图书列表展示
        lifecycleScope.launch() {
            val allBooks =
                viewModel.getAllBooks()!!
            books.addAll(allBooks)
            navController = findNavController()
            val bookAdapter = BookAdapter(books, viewModel, navController!!, false)
            val staggeredGridLayoutManager = StaggeredGridLayoutManager(2, VERTICAL)
            val gridLayoutManager = GridLayoutManager(newInstance().context, 4)
            val recyclerView = binding?.recyclerView
            recyclerView?.layoutManager = staggeredGridLayoutManager
            recyclerView?.adapter = bookAdapter

        }
//图书测试数据
//        val books = mutableListOf(
//            BookResponse.Book(id = 0, title = "dsfsdg", body = "fjsdfjshdhghkjsdhgjkdshkjghdkjg"),
//            BookResponse.Book(id = 1, title = "dsfsdg"),
//            BookResponse.Book(id = 2, title = "fjsdfjshdhghkjsdhgjkdshkjghdkjg"),
//            BookResponse.Book(id = 3, title = "dsfsdg"),
//            BookResponse.Book(id = 4, title = "fjsdfjshdhghkjsdhgjkdshkjghdkjg"),
//            BookResponse.Book(id = 5, title = "dsfsdg")
//        )
//
//        navController = findNavController()
//        val bookAdapter = BookAdapter(books, viewModel, navController!!)
//        val staggeredGridLayoutManager = StaggeredGridLayoutManager(2, VERTICAL)
//        val gridLayoutManager = GridLayoutManager(newInstance().context, 4)
//        val recyclerView = binding?.recyclerView
//        recyclerView?.layoutManager = staggeredGridLayoutManager
//        recyclerView?.adapter = bookAdapter

//        添加图书悬浮按钮
        binding?.fab?.setOnClickListener {

            navController!!.navigate(R.id.action_bookFragment_to_addBookFragment)
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}