package com.mahdivajdi.simpletodo.ui.task

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.mahdivajdi.simpletodo.App
import com.mahdivajdi.simpletodo.data.repository.CategoryRepository
import com.mahdivajdi.simpletodo.data.repository.TaskRepository
import com.mahdivajdi.simpletodo.databinding.FragmentTodayTasksBinding
import com.mahdivajdi.simpletodo.ui.MainViewModel
import com.mahdivajdi.simpletodo.ui.TaskViewModelFactory
import com.mahdivajdi.simpletodo.ui.adapter.TaskListAdapter
import java.time.LocalDate


class TodayTasksFragment : Fragment() {

    private val mainViewModel: MainViewModel by activityViewModels {
        TaskViewModelFactory(
            TaskRepository((activity?.application as App).database.taskDao()),
            CategoryRepository((activity?.application as App).database.categoryDao())
        )
    }

    private var _binding: FragmentTodayTasksBinding? = null
    private val binding: FragmentTodayTasksBinding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentTodayTasksBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.lifecycleOwner = viewLifecycleOwner
        binding.executePendingBindings()

        // Initiate recyclerview
        val tasksListAdapter = TaskListAdapter(
            onItemClicked = {
                val action = TodayTasksFragmentDirections.actionTodayTasksFragmentToTaskFragment(it)
                view.findNavController().navigate(action)
            },
            toggleTaskState = {
                mainViewModel.toggleTaskState(it)
            },
            toggleTaskPriority = {
                mainViewModel.toggleTaskPriority(it)
            }
        )
        binding.recyclerViewTodayTasks.adapter = tasksListAdapter
        binding.recyclerViewTodayTasks.layoutManager = LinearLayoutManager(requireContext())

        val today = LocalDate.now().toEpochDay()
        mainViewModel.getTasksWithDate(today).observe(viewLifecycleOwner) {
            tasksListAdapter.submitList(it)
        }
    }


    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }


}