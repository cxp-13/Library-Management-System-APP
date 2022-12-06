package com.permissionx.librarymanagementsystem.ui.book

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import com.google.android.material.datepicker.MaterialDatePicker
import com.permissionx.librarymanagementsystem.R
import com.permissionx.librarymanagementsystem.databinding.FragmentBookBinding
import com.permissionx.librarymanagementsystem.databinding.FragmentBookInfoBinding
import com.permissionx.librarymanagementsystem.ui.user.UserModel
import com.permissionx.librarymanagementsystem.util.showSnackbar
import kotlinx.coroutines.launch

class BookInfoFragment : Fragment() {


    private val viewModel by activityViewModels<BookViewModel>()

    private val userModel by activityViewModels<UserModel>()

    private var _binding: FragmentBookInfoBinding? = null
    private val binding get() = _binding


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentBookInfoBinding.inflate(inflater, container, false)


        return binding?.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        val book = viewModel.bookLiveData.value
        if (book != null) {
            binding?.bookBody!!.text = book.body
            binding?.bookTitle!!.text = book.title
        }


        val isShowBorrowingBtn = arguments?.get("showBorrowingBtn") as Boolean

        if (isShowBorrowingBtn) {
            binding?.borrowBooksFab?.visibility = View.GONE
        }

//图书借阅按钮
        binding?.borrowBooksFab?.setOnClickListener {

            val user = userModel.getUser()

            if (user != null) {
                val datePicker =
                    MaterialDatePicker.Builder.datePicker()
                        .setTitleText("Select your return date")
                        .setSelection(MaterialDatePicker.todayInUtcMilliseconds())
                        .build()
                val supportFragmentManager = userModel.supportFragmentManager
                if (supportFragmentManager != null) {
                    datePicker.show(supportFragmentManager, "book_info")
                }

                datePicker.addOnPositiveButtonClickListener {
                    // Respond to positive button click.
                    Log.d("test", "onViewCreated: $it")
                    val book = viewModel.bookLiveData.value
                    if (book != null) {
                        book.returnDate = it
                        book.bookBorrowersId = user.id
                    }
                    lifecycleScope.launch() {
                        viewModel.updateBook(book!!)
                    }
                }
            } else {
                view.showSnackbar("Please log in first")
            }
        }
        super.onViewCreated(view, savedInstanceState)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}