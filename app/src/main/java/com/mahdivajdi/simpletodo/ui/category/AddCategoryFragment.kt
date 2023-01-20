package com.mahdivajdi.simpletodo.ui.category

import android.app.AlertDialog
import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.activityViewModels
import com.mahdivajdi.simpletodo.App
import com.mahdivajdi.simpletodo.data.repository.CategoryRepository
import com.mahdivajdi.simpletodo.data.repository.TaskRepository
import com.mahdivajdi.simpletodo.databinding.AddCategoryDialogBinding
import com.mahdivajdi.simpletodo.domain.model.Category
import com.mahdivajdi.simpletodo.ui.MainViewModel
import com.mahdivajdi.simpletodo.ui.MainViewModelFactory

class AddCategoryFragment : DialogFragment() {

    private val mainViewModel: MainViewModel by activityViewModels {
        MainViewModelFactory(
            TaskRepository((activity?.application as App).database.taskDao()),
            CategoryRepository((activity?.application as App).database.categoryDao())
        )
    }

    private var _binding: AddCategoryDialogBinding? = null
    private val binding: AddCategoryDialogBinding get() = _binding!!


    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return activity?.let {
            _binding = AddCategoryDialogBinding.inflate(layoutInflater)
            val builder = AlertDialog.Builder(it)
            builder.setView(binding.root)
                .setPositiveButton("Save", DialogInterface.OnClickListener { _, _ ->
                    mainViewModel.insertCategory(
                        Category(
                            title = binding.editTextAddCategoryTitle.text.toString(),
                        )
                    )
                    dialog?.cancel()
                }).setNegativeButton("Cancel", DialogInterface.OnClickListener { _, _ ->
                    dialog?.cancel()
                })
            builder.create()
        } ?: throw IllegalStateException("Activity can't be null")
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}
