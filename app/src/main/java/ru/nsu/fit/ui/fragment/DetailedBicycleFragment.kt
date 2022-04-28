package ru.nsu.fit.ui.fragment

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.core.view.isGone
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import ru.nsu.fit.BicycleAccounterApplication
import ru.nsu.fit.R
import ru.nsu.fit.databinding.FragmentDetailedBicycleBinding
import ru.nsu.fit.databinding.ItemIssueBinding
import ru.nsu.fit.presentation.viewmodel.DetailedBicycleViewModel
import ru.nsu.fit.ui.dialogs.AddIssueDialog
import ru.nsu.fit.ui.dialogs.ChangePriceDialog
import ru.nsu.fit.ui.dialogs.ChangeStateDialog
import javax.inject.Inject

class DetailedBicycleFragment : Fragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private lateinit var viewModel: DetailedBicycleViewModel

    private var _binding: FragmentDetailedBicycleBinding? = null
    private val binding: FragmentDetailedBicycleBinding get() = checkNotNull(_binding) { "Binding is not initialized" }

    private var changeStateDialog: ChangeStateDialog? = null
    private val changePriceDialog: ChangePriceDialog by lazy { ChangePriceDialog(viewModel::updatePrice) }
    private val addIssueDialog: AddIssueDialog by lazy { AddIssueDialog(viewModel::addIssue) }

    private var editingEnabled = false

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
        initListeners()
        initViews()
        loadDataFromArgs()
    }

    private fun initViews() {
        binding.sellButton.setOnClickListener {
            viewModel.onSellButton()
        }
    }

    private fun initListeners() {
        lifecycleScope.launchWhenStarted {
            viewModel.bicycle.collect { bike ->
                with(bike) {
                    binding.addIssueButton.isGone = !editingEnabled
                    binding.editStateButton.isGone = !editingEnabled
                    binding.editPriceButton.isGone = !editingEnabled
                    binding.bikeNameText.text = name
                    binding.stateText.text = state.stateName
                    binding.wheelSizeText.text = wheelSize.diameter.toString()
                    binding.colorText.text = color.colorName
                    binding.priceMinText.text = purchasePrice.toString()
                    binding.priceValue.text =
                        sellingPrice?.toString() ?: getString(R.string.no_selling_price_set)
                    if (!description.isNullOrBlank()) {
                        binding.descriptionHeader.isGone = false
                        binding.descriptionText.isGone = false
                        binding.descriptionText.text = description

                    }
                    if (sellingPrice != null) {
                        binding.sellingPriceText.isGone = false
                        binding.sellingPriceText.text = sellingPrice.toString()
                    } else {
                        binding.sellingPriceText.isGone = true
                    }
                    picture?.let { binding.image.setImageBitmap(it) }
                    binding.sellButton.isGone = state.isNotSelling
                    if (issues.isNotEmpty()) {
                        binding.issuesList.removeAllViews()
                        binding.issuesList.isGone = false

                        issues.forEach { issue ->
                            val itemView = layoutInflater.inflate(R.layout.item_issue, null)
                            val itemBinding = ItemIssueBinding.bind(itemView)
                            itemBinding.description.text = issue.description
                            itemBinding.price.text =
                                String.format(itemBinding.price.text.toString(), issue.cost)
                            binding.issuesList.addView(itemBinding.root)
                        }
                    }
                }
            }
        }
        lifecycleScope.launchWhenStarted {
            viewModel.error.collect {
                toastOnError(it)
                if (it == DetailedBicycleViewModel.Errors.LOAD_BICYCLE_FAILED) {
                    findNavController().popBackStack()
                }
            }
        }
        lifecycleScope.launchWhenStarted {
            viewModel.sellCall.collect {
                navigateToSellBicycle(it.id, it.sellingPrice?.toDouble() ?: 0.0)
            }
        }

        lifecycleScope.launchWhenStarted {
            viewModel.states.collect { states ->
                changeStateDialog = ChangeStateDialog(states, viewModel::updateState)
            }
        }
        binding.editPriceButton.setOnClickListener {
            changePriceDialog.show(parentFragmentManager, ChangePriceDialog.TAG)
        }
        binding.editStateButton.setOnClickListener {
            changeStateDialog?.show(parentFragmentManager, ChangeStateDialog.TAG)
        }
        binding.addIssueButton.setOnClickListener {
            addIssueDialog.show(parentFragmentManager, AddIssueDialog.TAG)
        }

    }

    private fun loadDataFromArgs() {
        arguments?.getInt(REQUIRED_BIKE_ID)?.also {
            viewModel.loadBicycle(it)
        }
        editingEnabled = arguments?.getBoolean(OPTIONAL_EDITING_ENABLED) ?: false
    }

    private fun navigateToSellBicycle(id: Int, price: Double) {
        bundleOf(
            SellBicycleFragment.REQUIRED_BIKE_ID to id,
            SellBicycleFragment.REQUIRED_PRICE to price.toFloat()
        ).let {
            findNavController().navigate(
                R.id.action_detailedBicycleFragment_to_sellBicycleFragment,
                it
            )
        }
    }

    private fun toastOnError(code: DetailedBicycleViewModel.Errors) =
        when (code) {
            DetailedBicycleViewModel.Errors.LOAD_BICYCLE_FAILED -> showToastShort(getString(R.string.bicycle_load_failed_text))
            DetailedBicycleViewModel.Errors.UPDATE_PRICE_FAILED -> showToastShort(getString(R.string.update_price_failed_text))
            DetailedBicycleViewModel.Errors.ADD_ISSUE_FAILED -> showToastShort(getString(R.string.update_issue_failed_text))
            DetailedBicycleViewModel.Errors.UPDATE_STATE_FAILED -> showToastShort(getString(R.string.update_state_failed_text))
        }


    private fun showToastShort(hint: String) {
        Toast.makeText(
            requireContext(),
            hint,
            Toast.LENGTH_SHORT
        ).show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        const val REQUIRED_BIKE_ID = "bikeId"
        const val OPTIONAL_EDITING_ENABLED = "editingEnabled"
    }

}