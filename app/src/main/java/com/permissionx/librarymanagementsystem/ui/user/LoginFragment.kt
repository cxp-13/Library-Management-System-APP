package com.permissionx.librarymanagementsystem.ui.user

import android.os.Bundle
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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentLoginBinding.inflate(inflater, container, false)



        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.loginBtn.setOnClickListener {
            val userName = binding.loginUsername.editText?.text.toString()
            val password = binding.loginUsername.editText?.text.toString()

            lifecycleScope.launch {
//                network
//                userModel.login(userName, password)
//                database
                val user = userModel.getUserDataBase(userName)
                if (user != null) {
                    userModel.setUser(user)
                    Snackbar.make(view, "Log in successfully", Snackbar.LENGTH_SHORT).show()
                } else {
                    Snackbar.make(
                        view,
                        "The user name or password is incorrect",
                        Snackbar.LENGTH_SHORT
                    )
                        .show()
                }
            }
        }
        binding.registerBtn.setOnClickListener {
            val userName = binding.loginUsername.editText?.text.toString()
            val pwd = binding.loginPwd.editText?.text.toString()
//            if (userModel.isUserSaved(userName, pwd)) {
//                Snackbar.make(view, "The user is registered", Snackbar.LENGTH_SHORT).show()
//            } else {
//                userModel.saveUser(UserResponse.User(name = userName, password = pwd))
//                binding.name.setText("")
//                binding.pwd.setText("")
//                Snackbar.make(view, "Registration succeeded", Snackbar.LENGTH_SHORT).show()
//            }
            lifecycleScope.launch {
                val user = userModel.getUserDataBase(userName)
                if (user != null) {
                    Snackbar.make(view, "The user is registered", Snackbar.LENGTH_SHORT).show()
                } else {
                    userModel.saveUserDataBase(UserResponse.User(name = userName, password = pwd))
                    Snackbar.make(view, "Registration succeeded", Snackbar.LENGTH_SHORT).show()
                }
            }
        }
    }

    //注意：Fragment 的存在时间比其视图长。请务必在 Fragment 的 onDestroyView() 方法中清除对绑定类实例的所有引用。
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}