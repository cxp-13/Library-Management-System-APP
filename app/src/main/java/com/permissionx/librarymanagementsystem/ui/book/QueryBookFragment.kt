package com.permissionx.librarymanagementsystem.ui.book

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.permissionx.librarymanagementsystem.R
import com.permissionx.librarymanagementsystem.databinding.FragmentBookBinding
import com.permissionx.librarymanagementsystem.databinding.FragmentQueryBookBinding
import com.permissionx.librarymanagementsystem.ui.user.UserModel
import com.permissionx.librarymanagementsystem.util.showSnackbar
import kotlinx.coroutines.launch


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
            LinearLayoutManager(this.context, LinearLayoutManager.VERTICAL, false)

        val bookAdapter = BookAdapter(
            viewModel.books,
            viewModel,
            userModel,
            findNavController(),
            showReturnDate = false,
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
                viewModel.searchBook(it.toString())
            } else {
                viewModel.books.clear()
                bookAdapter.notifyDataSetChanged()
            }
        }
//        观察者
        viewModel.booksLiveData.observe(viewLifecycleOwner) {
            if (it.isNotEmpty()) {
                viewModel.books.apply {
                    clear()
                    addAll(it)
                }
                bookAdapter.notifyDataSetChanged()
            } else {
                viewModel.books.apply {
                    clear()
                }
                bookAdapter.notifyDataSetChanged()
                binding?.search?.showSnackbar("Failed to query the data")
            }
        }
        return binding?.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}