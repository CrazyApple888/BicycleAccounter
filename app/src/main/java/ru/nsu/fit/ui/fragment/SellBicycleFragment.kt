package ru.nsu.fit.ui.fragment

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.collectLatest
import ru.nsu.fit.BicycleAccounterApplication
import ru.nsu.fit.R
import ru.nsu.fit.databinding.FragmentSellBicycleBinding
import ru.nsu.fit.presentation.viewmodel.SellBicycleViewModel
import javax.inject.Inject


class SellBicycleFragment : Fragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private lateinit var viewModel: SellBicycleViewModel
    private var _binding: FragmentSellBicycleBinding? = null
    private val binding: FragmentSellBicycleBinding get() = checkNotNull(_binding) { "Binding is not initialized" }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        (requireActivity().application as BicycleAccounterApplication).appComponent.inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSellBicycleBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this, viewModelFactory)[SellBicycleViewModel::class.java]
        initViews()
        initListeners()
    }

    private fun initViews() {
        arguments?.getFloat(REQUIRED_PRICE)?.let {
            binding.priceEt.setText(it.toString())
        }
        binding.sellButton.setOnClickListener { sell() }
    }

    private fun initListeners() {
        lifecycleScope.launchWhenStarted {
            viewModel.isSuccessfullySold.collectLatest {
                if (it == null) {
                    return@collectLatest
                }
                if (it) {
                    Toast.makeText(
                        requireContext(),
                        getString(R.string.sell_bicycle_success),
                        Toast.LENGTH_SHORT
                    )
                        .show()
                } else {
                    Toast.makeText(requireContext(), getString(R.string.sell_bicycle_failure), Toast.LENGTH_SHORT).show()
                }
                navigateToHomeScreen()
            }
        }
    }

    private fun sell() {
        if (!validateFields()) {
            Toast.makeText(requireContext(), getString(R.string.sell_bicycle_fill_all_fields), Toast.LENGTH_SHORT).show()
            return
        }


        val bikeId = arguments?.getInt(REQUIRED_BIKE_ID)
        if (bikeId == null) {
            Toast.makeText(
                requireContext(),
                getString(R.string.sell_bicycle_failure),
                Toast.LENGTH_SHORT
            ).show()
            navigateToHomeScreen()
        }
        viewModel.sell(
            bikeId!!,
            binding.priceEt.text.toString().toDouble(),
            binding.nameEt.text.toString(),
            binding.phoneEt.text.toString()
        )
    }

    private fun navigateToHomeScreen() {
        findNavController().navigate(R.id.action_sellBicycleFragment_to_homeScreenFragment)
    }

    private fun validateFields() =
        !binding.nameEt.text.isNullOrBlank()
                && !binding.phoneEt.text.isNullOrBlank()
                && !binding.priceEt.text.isNullOrBlank()


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        const val REQUIRED_PRICE = "price"
        const val REQUIRED_BIKE_ID = "bikeId"
    }

}