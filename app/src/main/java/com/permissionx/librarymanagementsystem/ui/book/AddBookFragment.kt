package com.permissionx.librarymanagementsystem.ui.book

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.google.android.material.textfield.MaterialAutoCompleteTextView
import com.permissionx.librarymanagementsystem.R
import com.permissionx.librarymanagementsystem.databinding.FragmentAddBookBinding
import com.permissionx.librarymanagementsystem.logic.model.BookResponse
import kotlinx.coroutines.launch

class AddBookFragment : Fragment() {

    companion object {
        fun newInstance() = AddBookFragment()
    }

    private lateinit var viewModel: BookViewModel

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
        viewModel = ViewModelProvider(this).get(BookViewModel::class.java)

//菜单项
        val types = arrayOf(
            "technology", "life", "finance", "Children", "Literary"
        )
        val adapter = ArrayAdapter<String>(
            this.requireContext(),
            android.R.layout.simple_dropdown_item_1line,
            types
        )
        val typeEditText = binding?.type?.editText as MaterialAutoCompleteTextView
        typeEditText.setAdapter(adapter)

//        提交图书
        binding!!.addBookBtn.setOnClickListener {
            val title = binding?.title?.editText?.text.toString()
            val count = binding?.count?.editText?.text.toString()
            val location = binding?.location?.editText?.text.toString()
            val body = binding?.body?.editText?.text.toString()
            var type: String = "default"

            binding!!.typeTextView.setOnItemClickListener { parent, view, position, id ->
                val textView = view as TextView
                type = textView.text as String
            }
            lifecycleScope.launch {
                viewModel.addBook(
                    BookResponse.Book(
                        title = title,
                        count = count.toLong(),
                        bookshelfLocation = location,
                        body = body,
                        category = type
                    )
                )
            }


        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}