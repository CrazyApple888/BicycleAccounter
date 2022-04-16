package ru.nsu.fit.ui.fragment

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import ru.nsu.fit.BicycleAccounterApplication
import ru.nsu.fit.R
import ru.nsu.fit.databinding.FragmentCustomerListBinding
import ru.nsu.fit.presentation.viewmodel.CustomerListViewModel
import ru.nsu.fit.ui.adapter.CustomerListAdapter
import javax.inject.Inject


class CustomerListFragment : Fragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private lateinit var viewModel: CustomerListViewModel

    private var _binding: FragmentCustomerListBinding? = null
    private val binding: FragmentCustomerListBinding get() = checkNotNull(_binding) { "Binding is not initialized" }

    private val onCustomerClickListener = { id: Int ->
        val args = bundleOf(DetailedCustomerFragment.REQUIRED_CUSTOMER_ID to id)
        findNavController().navigate(
            R.id.action_customerListFragment_to_detailedCustomerFragment,
            args
        )
    }

    private val adapter: CustomerListAdapter = CustomerListAdapter(onCustomerClickListener)

    override fun onAttach(context: Context) {
        super.onAttach(context)
        (requireActivity().application as BicycleAccounterApplication).appComponent.inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCustomerListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        viewModel = ViewModelProvider(this, viewModelFactory)[CustomerListViewModel::class.java]
        binding.recycler.adapter = adapter
        initObservers()
        super.onViewCreated(view, savedInstanceState)
    }


    //TODO add errors handling
    private fun initObservers() {
        lifecycleScope.launchWhenCreated {
            viewModel.customers.collect {
                withContext(Dispatchers.Main) {
                    adapter.data = it
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}