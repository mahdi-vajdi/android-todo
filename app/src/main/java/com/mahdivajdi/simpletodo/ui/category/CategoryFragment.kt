package com.mahdivajdi.simpletodo.ui.category

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.LiveData
import androidx.navigation.fragment.navArgs
import com.mahdivajdi.simpletodo.App
import com.mahdivajdi.simpletodo.R
import com.mahdivajdi.simpletodo.data.repository.CategoryRepository
import com.mahdivajdi.simpletodo.data.repository.TaskRepository
import com.mahdivajdi.simpletodo.databinding.FragmentCategoryBinding
import com.mahdivajdi.simpletodo.domain.model.Category
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

    private val args: CategoryFragmentArgs by navArgs()
    private lateinit var category: LiveData<Category>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentCategoryBinding.inflate(layoutInflater, container, false)
        category = taskViewModel.getCategory(args.categoryId)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        category.observe(viewLifecycleOwner) { category ->
            binding.apply {
                textViewCategoryId.text = category.categoryId.toString()
                textViewCategoryTitle.text = category.name
                textViewCategoryDescription.text = category.description
            }
            binding.buttonCategoryDelete.setOnClickListener {
                taskViewModel.deleteCategory(category.categoryId)
            }
            binding.buttonCategoryEdit.setOnClickListener {
                // TODO: navigate to category edit fragment
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}