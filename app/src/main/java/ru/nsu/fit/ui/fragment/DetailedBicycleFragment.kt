package ru.nsu.fit.ui.fragment

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.core.view.isGone
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import kotlinx.coroutines.flow.last
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
        loadDataFromArgs()

        //todo delete placeholder
        binding.priceText.text = "1000"

        initViews()
    }

    private fun initViews() {
        lifecycleScope.launchWhenStarted {
            viewModel.bicycle.collect { bike ->
                with(bike) {
                    binding.bikeNameText.text = name
                    binding.stateText.text = state.state
                    binding.wheelSizeText.text = wheelSize.toString()
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

        lifecycleScope.launchWhenStarted {
            viewModel.isSuccess.collect {
                if (!it) {
                    Toast.makeText(requireContext(), "Непредвиденная ошибка :(", Toast.LENGTH_SHORT)
                        .show()
                    //findNavController().popBackStack()
                }
            }
        }

        binding.sellButton.setOnClickListener {
            val args = bundleOf(
                SellBicycleFragment.REQUIRED_BIKE_ID to requireArguments().getInt(REQUIRED_BIKE_ID),
                SellBicycleFragment.REQUIRED_PRICE to binding.priceText.text.toString().toFloat()
            )
            findNavController().navigate(R.id.action_detailedBicycleFragment_to_sellBicycleFragment, args)
        }
    }

    private fun loadDataFromArgs() {
        arguments?.getBoolean("isSold")?.let {
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