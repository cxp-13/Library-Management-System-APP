package com.permissionx.librarymanagementsystem


import android.os.Bundle
import android.view.Gravity
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import com.google.android.material.navigation.NavigationView
import com.permissionx.librarymanagementsystem.databinding.ActivityMainBinding
import com.permissionx.librarymanagementsystem.ui.user.UserModel

class MainActivity : AppCompatActivity() {


    private lateinit var binding: ActivityMainBinding


    private val userModel by lazy {
        ViewModelProvider(this).get(UserModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)

        supportActionBar.let {
            it?.setDisplayHomeAsUpEnabled(true)
            it?.setHomeAsUpIndicator(R.drawable.user)
        }

        binding.drawerLayout.addDrawerListener(object : DrawerLayout.DrawerListener {
            override fun onDrawerSlide(drawerView: View, slideOffset: Float) {
            }

            //             打开前监测是否有用户登录，有则显示其信息
            override fun onDrawerOpened(drawerView: View) {
                val navigationView = drawerView.findViewById<NavigationView>(R.id.nav_user)

                val headView = navigationView.getHeaderView(0)

                val username = headView?.findViewById<TextView>(R.id.username)
                val email = headView?.findViewById<TextView>(R.id.email)


                val saved = userModel.user.value != null

                if (saved) {
                    val user = userModel.user.value

                    username?.text = user?.name
                    email?.text = user?.password

                }
            }

            override fun onDrawerClosed(drawerView: View) {
            }

            override fun onDrawerStateChanged(newState: Int) {
            }

        })


    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.toolbar, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                binding.drawerLayout.openDrawer(GravityCompat.START)
            }
//            R.id.login->{
//                val navController = this.findNavController(R.id.nav_host_fragment)
//                navController.navigate(R.)
//            }
        }
        return true
    }
}

