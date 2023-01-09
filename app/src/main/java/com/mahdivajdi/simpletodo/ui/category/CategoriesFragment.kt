package com.mahdivajdi.simpletodo.ui.category

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.mahdivajdi.simpletodo.App
import com.mahdivajdi.simpletodo.R
import com.mahdivajdi.simpletodo.data.repository.CategoryRepository
import com.mahdivajdi.simpletodo.data.repository.TaskRepository
import com.mahdivajdi.simpletodo.databinding.FragmentCategoriesBinding
import com.mahdivajdi.simpletodo.ui.MainViewModel
import com.mahdivajdi.simpletodo.ui.TaskViewModelFactory


class CategoriesFragment : Fragment() {


    private val mainViewModel: MainViewModel by activityViewModels {
        TaskViewModelFactory(TaskRepository((activity?.application as App).database.taskDao()),
            CategoryRepository((activity?.application as App).database.categoryDao()))
    }

    private var _binding: FragmentCategoriesBinding? = null
    private val binding: FragmentCategoriesBinding get() = _binding!!


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

        mainViewModel.getCategories().observe(viewLifecycleOwner) { categoryList ->
            if (categoryList.isEmpty()) return@observe
            val categoryIds: List<Long> = categoryList.map {
                it.categoryId
            }
            Log.d("viewpager", "onViewCreated: ctegory id list= $categoryIds")
            val viewPager = binding.viewPagerCategories
            val viewPagerAdapter =
                CategoryViewPagerAdapter(requireActivity().supportFragmentManager,
                    lifecycle,
                    categoryIds)
            viewPager.adapter = viewPagerAdapter

            val tabLayout = binding.tabLayoutCategories
            TabLayoutMediator(tabLayout, viewPager) { tab, position ->
                tab.text = categoryList[position].title
            }.attach()
            // Add category tab
            tabLayout.addTab(tabLayout.newTab().setText("+ New Category"))
            tabLayout.addOnTabSelectedListener(object :
                TabLayout.OnTabSelectedListener {
                override fun onTabSelected(tab: TabLayout.Tab?) {
                    if (tab?.position == categoryIds.size) {
                        AddCategoryFragment().show(childFragmentManager, "add_category")
                        tabLayout.setSelectedTabIndicatorColor(Color.TRANSPARENT)
                    }
                }

                override fun onTabUnselected(tab: TabLayout.Tab?) {
                    tabLayout.setSelectedTabIndicatorColor(resources.getColor(R.color.purple_500))
                }

                override fun onTabReselected(tab: TabLayout.Tab?) {
                    if (tab?.position == categoryIds.size) {
                        AddCategoryFragment().show(childFragmentManager, "add_category")
                        tab.view.isSelected = false
                        tabLayout.setSelectedTabIndicatorColor(Color.TRANSPARENT)
                    }
                }

            })
        }
    }
}

class CategoryViewPagerAdapter(
    fragmentManager: FragmentManager,
    lifecycle: Lifecycle,
    private val categoryIds: List<Long>,
) : FragmentStateAdapter(fragmentManager, lifecycle) {

    private val itemCount = categoryIds.size + 1

    override fun getItemCount(): Int {
        Log.d("viewpager", "getItemCount: $itemCount")
        return categoryIds.size
    }

    override fun createFragment(position: Int): Fragment {
        Log.d("viewpager", "position: $position")
        return CategoryFragment.newInstance(categoryIds[position])
    }
}