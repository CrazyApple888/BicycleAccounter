package ru.nsu.fit.ui.fragment

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import ru.nsu.fit.BicycleAccounterApplication
import ru.nsu.fit.R
import ru.nsu.fit.databinding.FragmentSalesListBinding
import ru.nsu.fit.presentation.viewmodel.SalesViewModel
import ru.nsu.fit.ui.adapter.SalesListAdapter
import javax.inject.Inject

class SalesListFragment : Fragment() {
    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private lateinit var viewModel: SalesViewModel

    private var _binding: FragmentSalesListBinding? = null
    private val binding: FragmentSalesListBinding get() = checkNotNull(_binding) { "Binding is not initialized" }

    private val adapterOnClick = { bikeId: Int ->
        bundleOf(DetailedSaleFragment.REQUIRED_BIKE_ID to bikeId).let {
            findNavController().navigate(R.id.action_salesListFragment_to_detailedSaleFragment, it)
        }
    }
    private val adapter: SalesListAdapter by lazy { SalesListAdapter(adapterOnClick) }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        (requireActivity().application as BicycleAccounterApplication).appComponent.inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSalesListBinding.inflate(inflater, container, false)
        viewModel = ViewModelProvider(this, viewModelFactory)[SalesViewModel::class.java]

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
        initListeners()
    }

    private fun initListeners() {
        lifecycleScope.launchWhenStarted {
            viewModel.sales.collect {
                adapter.data = it
            }
        }
    }

    private fun initViews() {
        binding.recycler.adapter = adapter
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }
}