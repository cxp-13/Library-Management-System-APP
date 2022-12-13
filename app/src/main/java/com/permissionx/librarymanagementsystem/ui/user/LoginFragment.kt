package com.permissionx.librarymanagementsystem.ui.user

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.LifecycleCoroutineScope
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.get
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import com.permissionx.librarymanagementsystem.R
import com.permissionx.librarymanagementsystem.databinding.FragmentLoginBinding
import com.permissionx.librarymanagementsystem.logic.model.UserResponse
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import java.time.Duration

class LoginFragment : Fragment() {

    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!

    private val userModel by activityViewModels<UserModel>()

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
        userModel.userLiveData.observe(viewLifecycleOwner) {
            val user = it.getOrNull()
            if (user is UserResponse.User) {
                Snackbar.make(view, "Log in successfully", Snackbar.LENGTH_SHORT).show()
            } else {
                Snackbar.make(
                    view,
                    "$user",
                    Snackbar.LENGTH_SHORT
                )
                    .show()
            }
        }
//        用户注册后的观察者
        userModel.tokenLiveData.observe(viewLifecycleOwner) {
            val token = it.getOrNull()
            if (token != null) {
                Snackbar.make(
                    view,
                    "Registration succeeded, token is $token",
                    Snackbar.LENGTH_SHORT
                ).show()
            } else {
                val throwable = it.exceptionOrNull()
                Snackbar.make(
                    view,
                    "Registration failed, The reason is ${throwable?.message}",
                    Snackbar.LENGTH_SHORT
                ).show()
            }
        }
//登录按钮
        binding.loginBtn.setOnClickListener {
            val userName = binding.loginUsername.editText?.text.toString()
            val password = binding.loginPwd.editText?.text.toString()
            Log.d("test", "onViewCreated: $userName $password")

            userModel.login(userName, password)
        }

//        用户注册
        binding.registerBtn.setOnClickListener {
            val userName = binding.loginUsername.editText?.text.toString()
            val pwd = binding.loginPwd.editText?.text.toString()
            userModel.registered(name = userName, pwd = pwd)
        }
    }

    //注意：Fragment 的存在时间比其视图长。请务必在 Fragment 的 onDestroyView() 方法中清除对绑定类实例的所有引用。
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}