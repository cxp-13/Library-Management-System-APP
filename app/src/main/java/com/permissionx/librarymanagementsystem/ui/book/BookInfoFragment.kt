package com.permissionx.librarymanagementsystem.ui.book

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.permissionx.librarymanagementsystem.R
import com.permissionx.librarymanagementsystem.databinding.FragmentBookBinding
import com.permissionx.librarymanagementsystem.databinding.FragmentBookInfoBinding

class BookInfoFragment : Fragment() {




    private val viewModel by activityViewModels<BookViewModel>()

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
        super.onViewCreated(view, savedInstanceState)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}