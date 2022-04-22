package ru.nsu.fit.ui.fragment

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import ru.nsu.fit.BicycleAccounterApplication
import ru.nsu.fit.R
import ru.nsu.fit.databinding.FragmentDetailedCustomerBinding
import ru.nsu.fit.presentation.viewmodel.DetailedCustomerViewModel
import ru.nsu.fit.ui.adapter.BicycleListAdapter
import javax.inject.Inject

class DetailedCustomerFragment : Fragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private lateinit var viewModel: DetailedCustomerViewModel

    private var _binding: FragmentDetailedCustomerBinding? = null
    private val binding: FragmentDetailedCustomerBinding get() = checkNotNull(_binding) { "Binding is not initialized" }

    private val bicycleOnClickListener = { id: Int ->
        val args = bundleOf(DetailedBicycleFragment.REQUIRED_BIKE_ID to id)
        findNavController().navigate(
            R.id.action_detailedCustomerFragment_to_detailedBicycleFragment,
            args
        )
    }

    //lazy because context is not ready when fragment constructor is invoked (and getString requires context)
    private val adapter: BicycleListAdapter by lazy {
        BicycleListAdapter(
            getString(R.string.not_ready_for_sale),
            bicycleOnClickListener
        )
    }


    override fun onAttach(context: Context) {
        super.onAttach(context)
        (requireActivity().application as BicycleAccounterApplication).appComponent.inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDetailedCustomerBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this, viewModelFactory)[DetailedCustomerViewModel::class.java]
        binding.recycler.adapter = adapter
        arguments?.getInt(REQUIRED_CUSTOMER_ID)?.let {
            viewModel.loadCustomer(it)
        }

        initObservers()
    }

    private fun initObservers() {
        lifecycleScope.launchWhenStarted {
            viewModel.customer.collect {
                binding.name.text = it.name
                binding.phone.text = it.phone
                adapter.data = it.bicycles
            }
        }
        lifecycleScope.launchWhenStarted {
            viewModel.messages.collect {
                showToastShort(getString(R.string.failed_to_load_detailed_info))
                findNavController().popBackStack()
            }
        }
    }

    private fun showToastShort(hint: String) {
        Toast.makeText(
            requireContext(),
            hint,
            Toast.LENGTH_SHORT
        ).show()
    }

    companion object {
        const val REQUIRED_CUSTOMER_ID = "customerId"
    }
}