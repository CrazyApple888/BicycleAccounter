package ru.nsu.fit.ui.dialogs

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.view.View
import androidx.fragment.app.DialogFragment
import ru.nsu.fit.R
import ru.nsu.fit.databinding.ChangePriceDialogBinding

class ChangePriceDialog(private val onChangePrice: (Int) -> Unit) : DialogFragment() {

    private var _binding: ChangePriceDialogBinding? = null
    private val binding get() = checkNotNull(_binding) { "Binding is not initialized" }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        super.onCreateDialog(savedInstanceState)
        val builder =
            AlertDialog.Builder(requireContext())
                .setTitle(getString(R.string.change_price_dialog_title))
                .setView(getViewAndBinding())
                .setPositiveButton(getString(R.string.change))
                { _, _ ->
                    binding.priceInput.text.toString().toIntOrNull()?.let { onChangePrice(it) }
                }
                .setNegativeButton(getString(R.string.cancel))
                { _, _ -> }
        return builder.create()
    }

    private fun getViewAndBinding(): View {
        val view = layoutInflater.inflate(R.layout.change_price_dialog, null)
        _binding = ChangePriceDialogBinding.bind(view)
        return view
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        const val TAG = "change_price_dialog"
    }
}