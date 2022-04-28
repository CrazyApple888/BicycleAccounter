package ru.nsu.fit.ui.dialogs

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.view.View
import androidx.fragment.app.DialogFragment
import ru.nsu.fit.R
import ru.nsu.fit.databinding.AddIssueDialogBinding
import ru.nsu.fit.domain.model.Issue

class AddIssueDialog(private val onAddIssue: (Issue) -> Unit) : DialogFragment() {

    private var _binding: AddIssueDialogBinding? = null
    private val binding get() = checkNotNull(_binding) { "Binding is not initialized" }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        super.onCreateDialog(savedInstanceState)
        val builder =
            AlertDialog.Builder(requireContext())
                .setTitle(getString(R.string.change_price_dialog_title))
                .setView(getViewAndBinding())
                .setPositiveButton(getString(R.string.add))
                { _, _ ->
                    val price = binding.price.text.toString().toIntOrNull()
                    val text = binding.description.text?.toString() ?: ""

                    if (price != null && text != "") {
                        onAddIssue(Issue(description = text, cost = price))
                    }
                }
                .setNegativeButton(getString(R.string.cancel))
                { _, _ -> }
        return builder.create()
    }

    private fun getViewAndBinding(): View {
        val view = layoutInflater.inflate(R.layout.add_issue_dialog, null)
        _binding = AddIssueDialogBinding.bind(view)
        return view
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        const val TAG = "add_issue_dialog"
    }
}