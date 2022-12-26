package com.mahdivajdi.simpletodo.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.mahdivajdi.simpletodo.data.TaskRepository
import com.mahdivajdi.simpletodo.data.local.AppDatabase
import com.mahdivajdi.simpletodo.databinding.FragmentMainBinding
import com.mahdivajdi.simpletodo.ui.adapter.TaskListAdapter


class MainFragment : Fragment() {

    private val taskViewModel: TaskViewModel by viewModels {
       TaskViewModelFactory(
           TaskRepository(
               AppDatabase.getDatabase(requireContext()).taskDao()
           )
       )
    }

    private var _binding: FragmentMainBinding? = null
    private val binding: FragmentMainBinding get() = _binding!!

    private lateinit var taskListAdapter: TaskListAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentMainBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Initiate recyclerview
        taskListAdapter = TaskListAdapter()
        binding.RecyclerViewMain.adapter = taskListAdapter
        taskViewModel.getTasks().observe(viewLifecycleOwner) {
            taskListAdapter.submitList(it)
        }

        binding.fabMainAdd.setOnClickListener {
            // TODO: implement adding task dialog
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}