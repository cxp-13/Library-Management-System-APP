package com.permissionx.librarymanagementsystem.ui.book

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView.Orientation
import androidx.recyclerview.widget.RecyclerView.VERTICAL
import com.permissionx.librarymanagementsystem.R
import com.permissionx.librarymanagementsystem.databinding.FragmentBookBinding
import com.permissionx.librarymanagementsystem.databinding.FragmentMyBookBinding
import com.permissionx.librarymanagementsystem.logic.model.BookResponse
import com.permissionx.librarymanagementsystem.ui.user.UserModel
import com.permissionx.librarymanagementsystem.util.showSnackbar
import kotlinx.coroutines.launch


class MyBookFragment : Fragment() {
    private val viewModel by activityViewModels<BookViewModel>()
    private val userModel by activityViewModels<UserModel>()
    private var _binding: FragmentMyBookBinding? = null
    private val binding get() = _binding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentMyBookBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val linearLayoutManager =
            LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        val recyclerView = binding?.recyclerView
        recyclerView?.layoutManager = linearLayoutManager
//        图书列表展示
        val user = userModel.userLiveData.value
        if (user == null) {
            binding?.root?.showSnackbar("The user is not logged in")
        } else {
            viewModel.searchUserBook(user.id)
        }
        viewModel.showMyBooks.observe(viewLifecycleOwner) {
            val books = it.getOrNull()
            val bookAdapter = BookAdapter(books, viewModel, userModel, findNavController(), true)
            recyclerView?.adapter = bookAdapter
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}