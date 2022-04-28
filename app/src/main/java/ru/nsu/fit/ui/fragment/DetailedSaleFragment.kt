package ru.nsu.fit.ui.fragment

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isGone
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
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
        viewModel = ViewModelProvider(this, viewModelFactory)[DetailedSaleViewModel::class.java]
        initListeners()
        processArgs()
    }

    private fun processArgs() {
        arguments?.getInt(REQUIRED_BIKE_ID)?.let {
            viewModel.loadSale(it)
        } ?: run {
            onErrorOccurred()
        }
    }

    private fun initListeners() {
        lifecycleScope.launchWhenStarted {
            viewModel.saleLoadedEvent.collect { sale ->
                with(binding) {
                    customerText.text = sale.customerName
                    bikeText.text = sale.bicycleName
                    additionalInfoTitle.isGone = sale.additionalInfo.isBlank()
                    additionalInfoText.isGone = sale.additionalInfo.isBlank()
                    additionalInfoText.text = sale.additionalInfo
                    sellPriceText.text = sale.sellPrice.toString()
                    purchasePriceText.text = sale.buyPrice.toString()
                    costText.text = sale.cost.toString()
                    totalCost.text = getString(R.string.total_cost_pattern, sale.totalCost)
                }

            }
        }
        lifecycleScope.launchWhenStarted {
            viewModel.errorEvent.collect {
                onErrorOccurred()
            }
        }
    }

    private fun onErrorOccurred() {
        Toast.makeText(
            requireContext(),
            getString(R.string.detailed_sale_empty_id),
            Toast.LENGTH_SHORT
        ).show()
        findNavController().popBackStack()
    }

    companion object {
        const val REQUIRED_BIKE_ID = "bikeId"
    }
}