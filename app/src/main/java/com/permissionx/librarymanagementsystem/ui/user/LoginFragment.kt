package com.permissionx.librarymanagementsystem.ui.user

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import com.google.android.material.snackbar.Snackbar
import com.permissionx.librarymanagementsystem.LibraryManagementSystemApplication
import com.permissionx.librarymanagementsystem.databinding.FragmentLoginBinding
import com.permissionx.librarymanagementsystem.logic.model.TokenResponse

class LoginFragment : Fragment() {

    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!

    private val userViewModel by activityViewModels<UserModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
//        用户登陆后的观察者
        userViewModel.loginDataLiveData.observe(viewLifecycleOwner) {
            if (it.isSuccess) {
                val loginData = it.getOrNull() as TokenResponse.Data
//                bookViewModel.setToken(token)
//token做全局变量
                LibraryManagementSystemApplication.token = loginData.token
                // 给用户的id赋值
                if (userViewModel.userLiveData.value!!.id == "") {
                    userViewModel.setUserId(loginData.id)
                }
                Snackbar.make(
                    view,
                    "token is ${loginData.token} id:${loginData.id}",
                    Snackbar.LENGTH_SHORT
                ).show()
            } else {
                val exception = it.exceptionOrNull()
                Snackbar.make(
                    view,
                    "${exception?.message}",
                    Snackbar.LENGTH_SHORT
                ).show()
            }
        }
//        用户注册后的观察者
        userViewModel.registeredStatusLiveData.observe(viewLifecycleOwner) {
            if (it.isSuccess) {
                val message = it.getOrNull()
                Snackbar.make(
                    view,
                    "$message",
                    Snackbar.LENGTH_SHORT
                ).show()
            } else {
                val exception = it.exceptionOrNull()
                Snackbar.make(
                    view,
                    "${exception?.message}",
                    Snackbar.LENGTH_SHORT
                ).show()
            }
        }
//登录按钮
        binding.loginBtn.setOnClickListener {
            val userName = binding.loginUsername.editText?.text.toString()
            val password = binding.loginPwd.editText?.text.toString()
            Log.d("test", "onViewCreated: $userName $password")
            userViewModel.login(userName, password)
        }
//        用户注册
        binding.registerBtn.setOnClickListener {
            val userName = binding.loginUsername.editText?.text.toString()
            val pwd = binding.loginPwd.editText?.text.toString()
            userViewModel.registered(userName, pwd)
        }
    }

    //注意：Fragment 的存在时间比其视图长。请务必在 Fragment 的 onDestroyView() 方法中清除对绑定类实例的所有引用。
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}