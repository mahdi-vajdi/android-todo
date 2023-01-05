package com.mahdivajdi.simpletodo.ui.category

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.mahdivajdi.simpletodo.App
import com.mahdivajdi.simpletodo.data.repository.CategoryRepository
import com.mahdivajdi.simpletodo.data.repository.TaskRepository
import com.mahdivajdi.simpletodo.databinding.FragmentCategoriesBinding
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

    //    private lateinit var categoryListAdapter: CategoryListAdapter
//    private lateinit var categoryViewPagerAdapter: CategoryViewPagerAdapter


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
        binding.lifecycleOwner = viewLifecycleOwner
        binding.executePendingBindings()

        taskViewModel.getCategories().observe(viewLifecycleOwner) { categoryList ->
            val categoryIds: List<Long> = categoryList.map {
                it.categoryId
            }
            Log.d("viewpager", "onViewCreated: ctegory id list= $categoryIds")
            val viewPager = binding.viewPagerCategories
            val viewPagerAdapter = CategoryViewPagerAdapter(requireActivity().supportFragmentManager, lifecycle, categoryIds)
            viewPager.adapter = viewPagerAdapter
        }

        // Add new category
        /* binding.fabCategoriesAdd.setOnClickListener {
             val addCategoryFragment = AddCategoryFragment { category ->
                 Log.i("task", "onViewCreated: newTask= $it")
                 taskViewModel.insertCategory(category)
             }
             addCategoryFragment.show(parentFragmentManager, "add_task")
         }*/
    }
}

class CategoryViewPagerAdapter(
    fragmentManager: FragmentManager,
    lifecycle: Lifecycle,
    private val categoryIds: List<Long>,
) : FragmentStateAdapter(fragmentManager, lifecycle) {

    override fun getItemCount(): Int {
        Log.d("viewpager", "getItemCount: ${categoryIds.size}")
        return categoryIds.size
    }

    override fun createFragment(position: Int): Fragment =
        CategoryFragment.newInstance(categoryIds[position])
}