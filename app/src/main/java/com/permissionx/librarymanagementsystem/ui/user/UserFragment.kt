package com.permissionx.librarymanagementsystem.ui.user

import android.os.Bundle
import android.view.Gravity
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.permissionx.librarymanagementsystem.R
import com.permissionx.librarymanagementsystem.databinding.ActivityMainBinding
import com.permissionx.librarymanagementsystem.databinding.FragmentUserBinding
import com.permissionx.librarymanagementsystem.logic.model.UserResponse


class UserFragment : Fragment() {


    private var _binding: FragmentUserBinding? = null
    private val binding get() = _binding!!

    private val userModel by activityViewModels<UserModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentUserBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)





    }

    override fun onStart() {
        super.onStart()
        val drawerLayout = activity?.findViewById<DrawerLayout>(R.id.drawerLayout)

        binding.navUser.setNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.showAll -> {
                    drawerLayout?.closeDrawers()
                    true
                }
                else -> {
                    true
                }
            }
        }

    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}