package com.mahdivajdi.simpletodo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.fragment.app.activityViewModels
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.mahdivajdi.simpletodo.data.repository.CategoryRepository
import com.mahdivajdi.simpletodo.data.repository.TaskRepository
import com.mahdivajdi.simpletodo.databinding.ActivityMainBinding
import com.mahdivajdi.simpletodo.ui.task.AddTaskFragment
import com.mahdivajdi.simpletodo.ui.task.TaskViewModel
import com.mahdivajdi.simpletodo.ui.task.TaskViewModelFactory

class MainActivity : AppCompatActivity() {

    private val taskViewModel: TaskViewModel by viewModels {
        TaskViewModelFactory(
            TaskRepository((this.application as App).database.taskDao()),
            CategoryRepository((this.application as App).database.categoryDao())
        )
    }

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
            val addTaskFragment = AddTaskFragment {
                Log.i("task", "onViewCreated: newTask= $it")
                taskViewModel.insertTask(it)
            }
            addTaskFragment.show(supportFragmentManager, "add_task")
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp() || super.onSupportNavigateUp()
    }
}