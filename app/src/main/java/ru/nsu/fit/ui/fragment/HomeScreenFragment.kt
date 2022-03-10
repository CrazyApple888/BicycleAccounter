package ru.nsu.fit.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import ru.nsu.fit.R
import ru.nsu.fit.databinding.FragmentHomeScreenBinding
import ru.nsu.fit.domain.model.Bicycle
import ru.nsu.fit.domain.model.Color
import ru.nsu.fit.domain.model.State
import ru.nsu.fit.domain.model.Type
import ru.nsu.fit.presentation.HomeScreenViewModel
import ru.nsu.fit.ui.adapter.BicycleListAdapter


class HomeScreenFragment : Fragment() {

    private var _binding: FragmentHomeScreenBinding? = null
    private val binding: FragmentHomeScreenBinding get() = checkNotNull(_binding) { "Binding is not initialized" }
    private val viewModel: HomeScreenViewModel by lazy { ViewModelProvider(this)[HomeScreenViewModel::class.java] }
    private val adapter: BicycleListAdapter by lazy {
        BicycleListAdapter(getString(R.string.not_ready_for_sale)) {
            val args = Bundle()
            args.putLong("bikeId", it)
            findNavController().navigate(
                R.id.action_homeScreenFragment_to_detailedBicycleFragment,
                args
            )
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeScreenBinding.inflate(inflater, container, false)

        initViews()
        return binding.root
    }

    private fun initViews() {
        binding.recycler.adapter = adapter
        adapter.data = listOf(
            Bicycle(
                1,
                "Очень очень очень длинное название",
                12345,
                null,
                null,
                null,
                Type(),
                State(),
                27.5,
                Color("Черный")
            )
        )
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}