package ru.nsu.fit.ui.fragment

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.core.view.isGone
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import ru.nsu.fit.BicycleAccounterApplication
import ru.nsu.fit.R
import ru.nsu.fit.databinding.FragmentDetailedBicycleBinding
import ru.nsu.fit.presentation.viewmodel.DetailedBicycleViewModel
import javax.inject.Inject

class DetailedBicycleFragment : Fragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private lateinit var viewModel: DetailedBicycleViewModel

    private var _binding: FragmentDetailedBicycleBinding? = null
    private val binding: FragmentDetailedBicycleBinding get() = checkNotNull(_binding) { "Binding is not initialized" }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        (requireActivity().application as BicycleAccounterApplication).appComponent.inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDetailedBicycleBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(this, viewModelFactory)[DetailedBicycleViewModel::class.java]
        initListeners()
        initViews()
        loadDataFromArgs()
    }

    private fun initViews() {
        binding.sellButton.setOnClickListener {
            viewModel.onSellButton()
        }
    }

    private fun initListeners() {
        lifecycleScope.launchWhenStarted {
            viewModel.bicycle.collect { bike ->
                with(bike) {
                    binding.bikeNameText.text = name
                    binding.stateText.text = state.stateName
                    binding.wheelSizeText.text = wheelSize.diameter.toString()
                    binding.colorText.text = color.colorName
                    binding.priceMinText.text = purchasePrice.toString()
                    binding.descriptionText.text = description ?: ""
                    if (sellingPrice != null) {
                        binding.priceText.isGone = false
                        binding.priceText.text = sellingPrice.toString()
                    } else {
                        binding.priceText.isGone = true
                    }
                    picture?.let { binding.image.setImageBitmap(it) }
                }
            }
        }
        lifecycleScope.launchWhenStarted {
            viewModel.error.collect {
                showError()
                findNavController().popBackStack()
            }
        }
        lifecycleScope.launchWhenStarted {
            viewModel.sellCall.collect {
                navigateToSellBicycle(it.id, it.sellingPrice?.toDouble() ?: 0.0)
            }
        }
    }

    private fun loadDataFromArgs() {
        arguments?.getBoolean(OPTIONAL_IS_ITEM_SOLD)?.let {
            binding.sellButton.isGone = it
        }
        arguments?.getInt(REQUIRED_BIKE_ID)?.also {
            viewModel.loadBicycle(it)
        }
    }

    private fun navigateToSellBicycle(id: Int, price: Double) {
        bundleOf(
            SellBicycleFragment.REQUIRED_BIKE_ID to id,
            SellBicycleFragment.REQUIRED_PRICE to price.toFloat()
        ).let {
            findNavController().navigate(
                R.id.action_detailedBicycleFragment_to_sellBicycleFragment,
                it
            )
        }
    }

    private fun showError() =
        Toast.makeText(
            requireContext(),
            getString(R.string.bicycle_detailed_screen_error),
            Toast.LENGTH_SHORT
        ).show()

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        const val OPTIONAL_IS_ITEM_SOLD = "isSold"
        const val REQUIRED_BIKE_ID = "bikeId"
    }

}