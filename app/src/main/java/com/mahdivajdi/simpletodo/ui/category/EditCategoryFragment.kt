package com.mahdivajdi.simpletodo.ui.category

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.LiveData
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.mahdivajdi.simpletodo.App
import com.mahdivajdi.simpletodo.data.repository.CategoryRepository
import com.mahdivajdi.simpletodo.data.repository.TaskRepository
import com.mahdivajdi.simpletodo.databinding.FragmentEditCategoryBinding
import com.mahdivajdi.simpletodo.domain.model.Category
import com.mahdivajdi.simpletodo.ui.task.TaskViewModel
import com.mahdivajdi.simpletodo.ui.task.TaskViewModelFactory

class EditCategoryFragment : Fragment() {

    private val taskViewModel: TaskViewModel by activityViewModels {
        TaskViewModelFactory(
            TaskRepository((activity?.application as App).database.taskDao()),
            CategoryRepository((activity?.application as App).database.categoryDao())
        )
    }

    private val args: EditCategoryFragmentArgs by navArgs()
    private lateinit var category: LiveData<Category>

    private var _binding: FragmentEditCategoryBinding? = null
    private val binding: FragmentEditCategoryBinding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentEditCategoryBinding.inflate(layoutInflater, container, false)
        category = taskViewModel.getCategory(args.categoryId)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.lifecycleOwner = viewLifecycleOwner
        binding.executePendingBindings()

        category.observe(viewLifecycleOwner) { category ->
            binding.apply {
                editTextEditCategoryTitle.setText(category.name)
                editTextEditCategoryDescription.setText(category.description)
                buttonEditCategorySave.setOnClickListener {
                    taskViewModel.updateCategory(
                        Category(
                            categoryId = category.categoryId,
                            name = editTextEditCategoryTitle.text.toString(),
                            description = editTextEditCategoryDescription.text.toString()
                        )
                    )
                    view.findNavController().popBackStack()
                }
                buttonEditCategoryCancel.setOnClickListener {
                    view.findNavController().popBackStack()
                }
            }

        }
    }


}