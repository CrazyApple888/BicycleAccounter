package ru.nsu.fit.ui.dialogs

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import ru.nsu.fit.R
import ru.nsu.fit.domain.model.State

class ChangeStateDialog(
    private val states: List<State>,
    private val onChange: (State) -> Unit
) : DialogFragment() {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        super.onCreateDialog(savedInstanceState)
        val builder = AlertDialog.Builder(requireContext())
        builder.setTitle(getString(R.string.change_state_dialog_tag))
            .setItems(states.map { it.stateName }.toTypedArray())
            { _, idx -> onChange(states[idx]) }
            .setNegativeButton(getString(R.string.cancel))
            { _, _ -> }
        return builder.create()
    }

    companion object {
        const val TAG = "change_state_dialog"
    }
}