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
import ru.nsu.fit.databinding.FragmentCustomerListBinding
import ru.nsu.fit.presentation.viewmodel.CustomerListViewModel
import javax.inject.Inject


class CustomerListFragment : Fragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private lateinit var viewModel: CustomerListViewModel

    private var _binding: FragmentCustomerListBinding? = null
    private val binding: FragmentCustomerListBinding get() = checkNotNull(_binding) { "Binding is not initialized" }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        (requireActivity().application as BicycleAccounterApplication).appComponent.inject(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_customer_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }
}