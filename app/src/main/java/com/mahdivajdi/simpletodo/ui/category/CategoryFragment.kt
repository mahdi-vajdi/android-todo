package com.mahdivajdi.simpletodo.ui.category

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.LiveData
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.mahdivajdi.simpletodo.App
import com.mahdivajdi.simpletodo.data.repository.CategoryRepository
import com.mahdivajdi.simpletodo.data.repository.TaskRepository
import com.mahdivajdi.simpletodo.databinding.FragmentCategoryBinding
import com.mahdivajdi.simpletodo.domain.model.Category
import com.mahdivajdi.simpletodo.ui.adapter.TaskListAdapter
import com.mahdivajdi.simpletodo.ui.task.TaskViewModel
import com.mahdivajdi.simpletodo.ui.task.TaskViewModelFactory

class CategoryFragment : Fragment() {

    private val taskViewModel: TaskViewModel by activityViewModels {
        TaskViewModelFactory(
            TaskRepository((activity?.application as App).database.taskDao()),
            CategoryRepository((activity?.application as App).database.categoryDao())
        )
    }

    private var _binding: FragmentCategoryBinding? = null
    private val binding: FragmentCategoryBinding get() = _binding!!

    private lateinit var category: LiveData<Category>

    private lateinit var taskListAdapter: TaskListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let { arguments ->
            val categoryId = arguments.getLong(CATEGORY_ID_ARG)
            category = taskViewModel.getCategory(categoryId)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentCategoryBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.lifecycleOwner = viewLifecycleOwner
        binding.executePendingBindings()

        // Initiate recyclerview
        taskListAdapter = TaskListAdapter { taskId ->
            val action = CategoriesFragmentDirections.actionCategoriesFragmentToTaskFragment(taskId)
            view.findNavController().navigate(action)
        }
        binding.recyclerViewCategoryTaskList.adapter = taskListAdapter
        binding.recyclerViewCategoryTaskList.layoutManager = LinearLayoutManager(requireContext())

        category.observe(viewLifecycleOwner) { category ->
            Log.d("viewpager", "onViewCreated: $category")
            binding.apply {
                textViewCategoryTitle.text = category.title
            }
            binding.buttonCategoryDelete.setOnClickListener {
                taskViewModel.deleteCategory(category.categoryId)
            }
            binding.buttonCategoryEdit.setOnClickListener {
                val action =
                    CategoriesFragmentDirections.actionCategoriesFragmentToEditCategoryFragment(category.categoryId)
                view.findNavController().navigate(action)
            }
            taskViewModel.getTaskByCategoryId(category.categoryId).observe(viewLifecycleOwner) {
                taskListAdapter.submitList(it)
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