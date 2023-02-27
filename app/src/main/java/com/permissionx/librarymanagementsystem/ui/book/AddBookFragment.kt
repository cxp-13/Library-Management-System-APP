package com.permissionx.librarymanagementsystem.ui.book

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.google.android.material.textfield.MaterialAutoCompleteTextView
import com.permissionx.librarymanagementsystem.R
import com.permissionx.librarymanagementsystem.databinding.FragmentAddBookBinding
import com.permissionx.librarymanagementsystem.logic.model.Book
import com.permissionx.librarymanagementsystem.util.showSnackbar
import kotlinx.coroutines.launch

class AddBookFragment : Fragment() {
    private val viewModel by activityViewModels<BookViewModel>()
    private var _binding: FragmentAddBookBinding? = null

    private val binding get() = _binding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentAddBookBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
//图书的类别
        val types = arrayOf(
            "technology", "life", "finance", "Children", "Literary"
        )
        val adapter = ArrayAdapter(
            this.requireContext(),
            android.R.layout.simple_dropdown_item_1line,
            types
        )
        val typeEditText = binding?.type?.editText as MaterialAutoCompleteTextView
        typeEditText.setAdapter(adapter)
//判断是否是更新图书
        var title: String? = null
        var body: String? = null
        var bookId: String = ""
        viewModel.bookInfo.observe(viewLifecycleOwner) {
            Log.d("test", "onActivityCreated bookinfo:${it.getOrNull()} ")
            it.getOrNull()?.apply {
                title = this.title
                body = this.body
                bookId = this.id
            }
            binding!!.title.editText?.setText(title)
            binding!!.body.editText?.setText(body)
        }

        //确认更新或者提交图书
        binding!!.confirmBtn.setOnClickListener {
            val newTitle = binding?.title?.editText?.text.toString()
            val count = binding?.count?.editText?.text.toString()
            val location = binding?.location?.editText?.text.toString()
            val body = binding?.body?.editText?.text.toString()
            var type = "default"
            //获取图书类别菜单的选定项
            binding!!.typeTextView.setOnItemClickListener { parent, view, position, id ->
                val textView = view as TextView
                type = textView.text as String
            }
            lifecycleScope.launch {
                val result = if (title != null) {
                    viewModel.updateBook(
                        Book(
                            id = bookId,
//                            title = "$newTitle&$title",
                            title = newTitle,
                            count = count.toLongOrNull() ?: 1,
                            bookshelfLocation = location,
                            body = body,
                            category = type
                        )
                    ).getOrNull()
                } else {
                    viewModel.addBook(
                        Book(
                            title = newTitle,
                            count = count.toLong(),
                            bookshelfLocation = location,
                            body = body,
                            category = type
                        )
                    ).getOrNull()
                }
                view?.showSnackbar("$result", "examine") {
                    val navController = findNavController()
                    navController.navigate(R.id.action_addBookFragment_to_bookFragment)
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}