package com.permissionx.librarymanagementsystem.ui.book

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.permissionx.librarymanagementsystem.R
import com.permissionx.librarymanagementsystem.databinding.FragmentBookBinding
import com.permissionx.librarymanagementsystem.databinding.FragmentMyBookBinding
import com.permissionx.librarymanagementsystem.logic.model.BookResponse
import com.permissionx.librarymanagementsystem.ui.user.UserModel
import kotlinx.coroutines.launch


class MyBookFragment : Fragment() {


    companion object {
        fun newInstance() = BookFragment()
    }

    private lateinit var viewModel: BookViewModel
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
        viewModel = ViewModelProvider(this)[BookViewModel::class.java]


        var books = ArrayList<BookResponse.Book>()

//        图书列表展示
        lifecycleScope.launch() {
            val user = userModel.getUser()
            if (user == null) {
                books.addAll(viewModel.getAllBooks()!!)
            } else {
                books.addAll(viewModel.getAllBooksById(user.id)!!)
            }
            val bookAdapter = BookAdapter(books)
            val gridLayoutManager = GridLayoutManager(newInstance().context, 4)
            val recyclerView = binding?.recyclerView
            recyclerView?.layoutManager = gridLayoutManager
            recyclerView?.adapter = bookAdapter

        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}