package com.mahdivajdi.simpletodo.ui.category

import android.app.AlertDialog
import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import com.mahdivajdi.simpletodo.databinding.AddCategoryDialogBinding
import com.mahdivajdi.simpletodo.domain.model.Category

class AddCategoryFragment(private val newCategory: (category: Category) -> Unit) : DialogFragment() {

    private var _binding: AddCategoryDialogBinding? = null
    private val binding: AddCategoryDialogBinding get() = _binding!!


    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return activity?.let {
            _binding = AddCategoryDialogBinding.inflate(layoutInflater)
            val builder = AlertDialog.Builder(it)
            builder.setView(binding.root)
                .setPositiveButton("Save", DialogInterface.OnClickListener { _, _ ->
                    val category = Category(
                        title = binding.editTextAddCategoryTitle.text.toString(),
                    )
                    newCategory(category)
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
