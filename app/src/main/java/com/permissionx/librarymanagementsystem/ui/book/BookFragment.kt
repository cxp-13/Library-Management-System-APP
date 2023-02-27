package com.permissionx.librarymanagementsystem.ui.book

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView.VERTICAL
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.permissionx.librarymanagementsystem.R
import com.permissionx.librarymanagementsystem.databinding.FragmentBookBinding
import com.permissionx.librarymanagementsystem.ui.user.UserModel
import com.permissionx.librarymanagementsystem.util.showSnackbar

class BookFragment : Fragment() {

    companion object {
        fun newInstance() = BookFragment()
    }

    private val viewModel by activityViewModels<BookViewModel>()

    private val userModel by activityViewModels<UserModel>()


    private var _binding: FragmentBookBinding? = null
    private val binding get() = _binding


    lateinit var navController: NavController

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentBookBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        navController = findNavController()
        //初始化图书展示列表
        viewModel.showBooks.observe(viewLifecycleOwner) {
            val bookList = it.getOrNull()
            if (bookList != null) {
                val bookAdapter =
                    BookAdapter(it.getOrNull(), viewModel, userModel, navController!!, false)
                val staggeredGridLayoutManager = StaggeredGridLayoutManager(2, VERTICAL)
                val recyclerView = binding?.recyclerView
                recyclerView?.layoutManager = staggeredGridLayoutManager
                recyclerView?.adapter = bookAdapter
            } else {
                val errorMsg = it.exceptionOrNull()?.message
                Log.d("test", "onActivityCreated: $errorMsg")
                binding?.swipeRefresh?.showSnackbar(errorMsg ?: "错误")

            }
        }
        // 添加图书悬浮按钮
        binding?.fab?.setOnClickListener {
            navController!!.navigate(R.id.action_bookFragment_to_addBookFragment)
        }
        binding?.swipeRefresh?.setOnRefreshListener {
            //图书列表展示
            refreshBooks()
        }
    }

    private fun refreshBooks() {
        viewModel.refreshBooks()
        binding?.swipeRefresh?.isRefreshing = false
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}