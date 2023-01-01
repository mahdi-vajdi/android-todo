package com.mahdivajdi.simpletodo.ui.category

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.mahdivajdi.simpletodo.App
import com.mahdivajdi.simpletodo.R
import com.mahdivajdi.simpletodo.data.repository.CategoryRepository
import com.mahdivajdi.simpletodo.data.repository.TaskRepository
import com.mahdivajdi.simpletodo.databinding.FragmentCategoriesBinding
import com.mahdivajdi.simpletodo.ui.adapter.CategoryListAdapter
import com.mahdivajdi.simpletodo.ui.adapter.TaskListAdapter
import com.mahdivajdi.simpletodo.ui.task.AddTaskFragment
import com.mahdivajdi.simpletodo.ui.task.TaskViewModel
import com.mahdivajdi.simpletodo.ui.task.TaskViewModelFactory


class CategoriesFragment : Fragment() {

    private val taskViewModel: TaskViewModel by activityViewModels {
        TaskViewModelFactory(
            TaskRepository((activity?.application as App).database.taskDao()),
            CategoryRepository((activity?.application as App).database.categoryDao())
        )
    }

    private var _binding: FragmentCategoriesBinding? = null
    private val binding: FragmentCategoriesBinding get() = _binding!!

    private lateinit var categoryListAdapter: CategoryListAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentCategoriesBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // Initiate categories list
        categoryListAdapter = CategoryListAdapter { categoryId ->
            val action = CategoriesFragmentDirections.actionCategoriesFragmentToCategoryFragment(categoryId)
            view.findNavController().navigate(action)
        }
        binding.recyclerViewCategories.adapter = categoryListAdapter
        binding.recyclerViewCategories.layoutManager = LinearLayoutManager(requireContext())
        taskViewModel.getCategories().observe(viewLifecycleOwner) {
            categoryListAdapter.submitList(it)
        }

        // Add new category
        binding.fabCategoriesAdd.setOnClickListener {
            val addCategoryFragment = AddCategoryFragment { category ->
                Log.i("task", "onViewCreated: newTask= $it")
                taskViewModel.insertCategory(category)
            }
            addCategoryFragment.show(parentFragmentManager, "add_task")
        }
    }
}