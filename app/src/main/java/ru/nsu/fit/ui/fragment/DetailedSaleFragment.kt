package ru.nsu.fit.ui.fragment

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.StringRes
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import ru.nsu.fit.BicycleAccounterApplication
import ru.nsu.fit.R
import ru.nsu.fit.databinding.FragmentDetailedSaleBinding
import ru.nsu.fit.presentation.viewmodel.DetailedSaleViewModel
import javax.inject.Inject

class DetailedSaleFragment : Fragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private lateinit var viewModel: DetailedSaleViewModel

    private var _binding: FragmentDetailedSaleBinding? = null
    private val binding: FragmentDetailedSaleBinding get() = checkNotNull(_binding) { "Binding is not initialized" }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        (requireActivity().application as BicycleAccounterApplication).appComponent.inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDetailedSaleBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initListeners()
        processArgs()
    }

    private fun processArgs() {
        arguments?.getInt(REQUIRED_BIKE_ID)?.let {
            viewModel.loadSale(it)
        } ?: run {
            showToast(R.string.detailed_sale_empty_id)
            navigateBack()
        }
    }

    private fun initListeners() {

    }

    private fun navigateBack() {
        findNavController().popBackStack()
    }

    private fun showToast(@StringRes id: Int) =
        Toast.makeText(requireContext(), getString(id), Toast.LENGTH_SHORT).show()

    companion object {
        const val REQUIRED_BIKE_ID = "bikeId"
    }
}