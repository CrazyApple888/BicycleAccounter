package ru.nsu.fit.ui.fragment

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import ru.nsu.fit.BicycleAccounterApplication
import ru.nsu.fit.R
import ru.nsu.fit.databinding.FragmentDetailedCustomerBinding
import ru.nsu.fit.presentation.viewmodel.DetailedBicycleViewModel
import javax.inject.Inject

class DetailedCustomerFragment : Fragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private lateinit var viewModel: DetailedCustomerViewModel

    private val _binding: FragmentDetailedCustomerBinding? = null
    private val binding: FragmentDetailedCustomerBinding get() = checkNotNull(_binding) { "Binding is not initialized" }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        (requireActivity().application as BicycleAccounterApplication).appComponent.inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_detailed_customer, container, false)
    }
}