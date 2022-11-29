package com.permissionx.librarymanagementsystem.ui.user

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.get
import androidx.navigation.fragment.findNavController
import com.permissionx.librarymanagementsystem.R
import com.permissionx.librarymanagementsystem.databinding.FragmentLoginBinding
import com.permissionx.librarymanagementsystem.databinding.FragmentUserBinding
import com.permissionx.librarymanagementsystem.logic.model.UserResponse

class LoginFragment : Fragment() {

    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!

    private val userModel by activityViewModels<UserModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentLoginBinding.inflate(inflater, container, false)

        binding.loginBtn.setOnClickListener {
            val userName = binding.name.text.toString()
            val pwd = binding.pwd.text.toString().toLong()
            userModel.saveUser(UserResponse.User(name = userName, password = pwd))
        }




        return binding.root
    }
//注意：Fragment 的存在时间比其视图长。请务必在 Fragment 的 onDestroyView() 方法中清除对绑定类实例的所有引用。
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}