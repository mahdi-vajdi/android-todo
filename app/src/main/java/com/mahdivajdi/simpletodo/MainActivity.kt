package com.mahdivajdi.simpletodo

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.mahdivajdi.simpletodo.databinding.ActivityMainBinding
import com.mahdivajdi.simpletodo.ui.task.AddTaskFragment

class MainActivity : AppCompatActivity() {

    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        navController = navHostFragment.navController

        // Set up toolbar
        setSupportActionBar(binding.toolbar)
        /*val appBarConfiguration =
            AppBarConfiguration(setOf(R.id.categoriesFragment, )*/
        setupActionBarWithNavController(navController)

        // Set up bottom nav
        binding.bottomNavigationBar.background = null
        binding.bottomNavigationBar.menu.getItem(2).isEnabled = false
        binding.bottomNavigationBar.setupWithNavController(navController)

        // Add new task using fab button
        binding.fabMainAddTask.setOnClickListener {
            AddTaskFragment.newInstance()
                .show(supportFragmentManager, AddTaskFragment::class.java.canonicalName)
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp() || super.onSupportNavigateUp()
    }
}