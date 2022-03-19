package ru.nsu.fit.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import ru.nsu.fit.R
import ru.nsu.fit.databinding.FragmentHomeScreenBinding
import ru.nsu.fit.domain.model.*
import ru.nsu.fit.presentation.HomeScreenViewModel
import ru.nsu.fit.ui.adapter.BicycleListAdapter


class HomeScreenFragment : Fragment() {

    private var _binding: FragmentHomeScreenBinding? = null
    private val binding: FragmentHomeScreenBinding get() = checkNotNull(_binding) { "Binding is not initialized" }
    //todo create viewmodel factory
    //private val viewModel: HomeScreenViewModel by viewModels()//lazy { ViewModelProvider(this)[HomeScreenViewModel::class.java] }
    private val adapter: BicycleListAdapter by lazy {
        BicycleListAdapter(getString(R.string.not_ready_for_sale), ::bicycleOnClickListener)
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
        val data = SimpleBicycle(
            1,
            "Очень очень очень длинное название",
            12345,
            null,
            27.5
        )
        adapter.data = listOf(
            data
        )
//        viewModel.bicycles.observe(viewLifecycleOwner) { adapter.data = it }
    }

    private fun bicycleOnClickListener(id: Int) {
//        val args = Bundle()
//        args.putInt("bikeId", id)
//        findNavController().navigate(
//            R.id.action_homeScreenFragment_to_detailedBicycleFragment,
//            args
//        )
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}