package com.mahdivajdi.simpletodo.ui.category

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.LiveData
import androidx.navigation.findNavController
import com.mahdivajdi.simpletodo.App
import com.mahdivajdi.simpletodo.data.repository.CategoryRepository
import com.mahdivajdi.simpletodo.data.repository.TaskRepository
import com.mahdivajdi.simpletodo.databinding.FragmentCategoryBinding
import com.mahdivajdi.simpletodo.domain.model.Category
import com.mahdivajdi.simpletodo.ui.MainViewModel
import com.mahdivajdi.simpletodo.ui.TaskViewModelFactory
import com.mahdivajdi.simpletodo.ui.adapter.TaskListAdapter

class CategoryFragment : Fragment() {

    private val mainViewModel: MainViewModel by activityViewModels {
        TaskViewModelFactory(
            TaskRepository((activity?.application as App).database.taskDao()),
            CategoryRepository((activity?.application as App).database.categoryDao())
        )
    }

    private var _binding: FragmentCategoryBinding? = null
    private val binding: FragmentCategoryBinding get() = _binding!!

    private lateinit var category: LiveData<Category>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let { arguments ->
            category = mainViewModel.getCategory(arguments.getLong(CATEGORY_ID_ARG))
        }

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentCategoryBinding.inflate(layoutInflater, container, false)
        binding.lifecycleOwner = this@CategoryFragment
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val adapter = TaskListAdapter(
            onItemClicked = { taskId ->
                val action = CategoriesFragmentDirections.actionCategoriesFragmentToTaskFragment(taskId)
                view.findNavController().navigate(action)
            },
            toggleTaskState = {
                mainViewModel.toggleTaskState(it)
            },
            toggleTaskPriority = {
                mainViewModel.toggleTaskPriority(it)
            }
        )
        binding.recyclerViewCategoryTaskList.adapter = adapter

        category.observe(viewLifecycleOwner) { category ->
            Log.d("viewpager", "onViewCreated: $category")
            binding.buttonCategoryDelete.setOnClickListener {
                mainViewModel.deleteCategory(category.categoryId)
            }
            binding.buttonCategoryEdit.setOnClickListener {
                EditCategoryFragment(category.categoryId).show(
                    childFragmentManager,
                    "edit_category"
                )
            }
            mainViewModel.getTaskByCategoryId(category.categoryId).observe(viewLifecycleOwner) {
                adapter.submitList(it)
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    companion object {
        private const val CATEGORY_ID_ARG = "category_id"

        @JvmStatic
        fun newInstance(categoryId: Long) = CategoryFragment().apply {
            arguments = Bundle().apply {
                putLong(CATEGORY_ID_ARG, categoryId)
            }
        }
    }

}