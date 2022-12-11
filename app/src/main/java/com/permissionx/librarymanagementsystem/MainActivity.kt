package com.permissionx.librarymanagementsystem


import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.findViewTreeLifecycleOwner
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.*
import com.google.android.material.navigation.NavigationView
import com.permissionx.librarymanagementsystem.databinding.ActivityMainBinding
import com.permissionx.librarymanagementsystem.logic.model.UserResponse
import com.permissionx.librarymanagementsystem.ui.user.UserModel

class MainActivity : AppCompatActivity() {


    private lateinit var binding: ActivityMainBinding

    private lateinit var appBarConfiguration: AppBarConfiguration


    private val userModel by lazy {
        ViewModelProvider(this).get(UserModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)
        window.statusBarColor = Color.TRANSPARENT

//        supportActionBar.let {
//            it?.setDisplayHomeAsUpEnabled(true)
//            it?.setHomeAsUpIndicator(R.drawable.user)
//        }

        userModel.supportFragmentManager = supportFragmentManager
//将菜单和导航绑定
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        val navController = navHostFragment.navController

        appBarConfiguration = AppBarConfiguration(navController.graph, binding.drawerLayout)

        setupActionBarWithNavController(navController, appBarConfiguration)
        val navigationView = findViewById<NavigationView>(R.id.nav_view)
        navigationView.setupWithNavController(navController)



//抽屉导航栏
        binding.drawerLayout.addDrawerListener(object : DrawerLayout.DrawerListener {
            override fun onDrawerSlide(drawerView: View, slideOffset: Float) {
            }
            //             打开前监测是否有用户登录，有则显示其信息
            override fun onDrawerOpened(drawerView: View) {
                val navigationView = drawerView.findViewById<NavigationView>(R.id.nav_view)
                val headView = navigationView.getHeaderView(0)
                val username = headView?.findViewById<TextView>(R.id.username)
                val userId = headView?.findViewById<TextView>(R.id.user_id)

                userModel.userLiveData.observe(drawerView.findViewTreeLifecycleOwner()!!){
                    val user = it.getOrNull()
                    username!!.text = user?.name
                    userId!!.text = user?.id

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
        val navController = findNavController(R.id.nav_host_fragment)
        Log.d("test", "onOptionsItemSelected: ${item.title}")
        return item.onNavDestinationSelected(navController) || super.onOptionsItemSelected(item)
    }


    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }
}

