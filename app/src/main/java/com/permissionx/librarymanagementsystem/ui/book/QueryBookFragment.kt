package com.permissionx.librarymanagementsystem.ui.book

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.permissionx.librarymanagementsystem.databinding.FragmentQueryBookBinding
import com.permissionx.librarymanagementsystem.ui.user.UserModel
import com.permissionx.librarymanagementsystem.util.showSnackbar


class QueryBookFragment : Fragment() {

    private val viewModel by activityViewModels<BookViewModel>()
    private val userModel by activityViewModels<UserModel>()

    private var _binding: FragmentQueryBookBinding? = null
    private val binding get() = _binding


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentQueryBookBinding.inflate(inflater, container, false)


        val linearLayoutManager =
            LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)

        val bookAdapter = BookAdapter(
            viewModel.books,
            viewModel,
            userModel,
            findNavController(),
            showReturnTime = false,
            showBtn = false
        )

        binding?.recyclerView?.apply {
            layoutManager = linearLayoutManager
            adapter = bookAdapter
        }
//根据搜索框实时变化展示的数据
        binding?.search?.editText?.addTextChangedListener {
            val queryText = it.toString()
            if (queryText.isNotEmpty()) {
                viewModel.searchBook(queryText)
            } else {
                viewModel.books.clear()
                bookAdapter.notifyDataSetChanged()
            }
        }
//观察查询图书列表
        viewModel.queryBooks.observe(viewLifecycleOwner) {
            val books = it.getOrNull()
            if (books != null) {
                viewModel.books.apply {
                    clear()
                    addAll(books)
                }
                bookAdapter.notifyDataSetChanged()
            } else {
                viewModel.books.apply {
                    clear()
                }
                bookAdapter.notifyDataSetChanged()
                val errorMsg = it.exceptionOrNull()?.message
                binding?.search?.showSnackbar("$errorMsg")
            }
        }
        return binding?.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}