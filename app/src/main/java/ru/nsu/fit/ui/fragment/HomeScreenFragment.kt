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
import ru.nsu.fit.databinding.FragmentHomeScreenBinding
import ru.nsu.fit.presentation.viewmodel.HomeScreenViewModel
import ru.nsu.fit.ui.adapter.BicycleListAdapter
import javax.inject.Inject


class HomeScreenFragment : Fragment() {

    private var _binding: FragmentHomeScreenBinding? = null
    private val binding: FragmentHomeScreenBinding get() = checkNotNull(_binding) { "Binding is not initialized" }

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private lateinit var viewModel: HomeScreenViewModel

    private val bicycleOnClickListener = { id: Int ->
        val args = bundleOf(DetailedBicycleFragment.REQUIRED_BIKE_ID to id)
        findNavController().navigate(
            R.id.action_homeScreenFragment_to_detailedBicycleFragment,
            args
        )
    }

    private val adapter: BicycleListAdapter by lazy {
        BicycleListAdapter(getString(R.string.not_ready_for_sale), bicycleOnClickListener)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        (requireActivity().application as BicycleAccounterApplication).appComponent.inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeScreenBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this, viewModelFactory)[HomeScreenViewModel::class.java]
        initViews()
    }

    private fun initViews() {
        binding.recycler.adapter = adapter
        lifecycleScope.launchWhenStarted {
            viewModel.bicycles.collect { list ->
                if (list.isEmpty()) {
                    showMessage(getString(R.string.home_screen_empty_db))
                } else {
                    adapter.data = list
                }
            }
        }
        lifecycleScope.launchWhenStarted {
            viewModel.error.collect {
                showMessage(getString(R.string.home_screen_loading_error))
            }
        }
    }

    private fun showMessage(message: String) =
        Toast.makeText(
            requireContext(),
            message,
            Toast.LENGTH_SHORT
        ).show()

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}