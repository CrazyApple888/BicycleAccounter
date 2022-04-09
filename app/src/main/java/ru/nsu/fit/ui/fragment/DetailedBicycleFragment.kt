package ru.nsu.fit.ui.fragment

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isGone
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import ru.nsu.fit.BicycleAccounterApplication
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
        initViews()
        loadDataFromArgs()
    }

    private fun initViews() {
        viewModel.bicycle.observe(viewLifecycleOwner) { bike ->
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

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        const val OPTIONAL_IS_ITEM_SOLD = "isSold"
        const val REQUIRED_BIKE_ID = "bikeId"
    }

}