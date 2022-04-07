package ru.nsu.fit.ui.fragment

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import ru.nsu.fit.BicycleAccounterApplication
import ru.nsu.fit.R
import ru.nsu.fit.databinding.FragmentAddBicycleBinding
import ru.nsu.fit.domain.model.*
import ru.nsu.fit.presentation.viewmodel.AddBicycleViewModel
import javax.inject.Inject


class AddBicycleFragment : Fragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private lateinit var viewModel: AddBicycleViewModel

    private var _binding: FragmentAddBicycleBinding? = null
    private val binding: FragmentAddBicycleBinding get() = checkNotNull(_binding) { "Binding is not initialized" }


    override fun onAttach(context: Context) {
        super.onAttach(context)
        (requireActivity().application as BicycleAccounterApplication).appComponent.inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentAddBicycleBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this, viewModelFactory)[AddBicycleViewModel::class.java]
        loadParams()
        setUpValidators()

        binding.submitButton.setOnClickListener { onSubmit() }
        lifecycleScope.launchWhenCreated {
            viewModel.messages.collect {
                when (it) {
                    is Result.Success -> showToast(getString(R.string.bicycle_added_successfully))
                    is Result.Failure -> showToast(getString(R.string.bicycle_adding_failed))
                }
            }
        }
    }

    private fun setUpValidators() {
        binding.wheelSizeInput.doOnTextChanged { text, _, _, _ ->
            text?.let {
                if (!matchesWheelSize(it.toString().toDoubleOrNull() ?: return@let)) {
                    binding.wheelSizeInput.setError(getString(R.string.wrong_wheel_size_hint), null)
                }
            }
        }
        binding.typeInput.doOnTextChanged { text, _, _, _ ->
            text?.let {
                if (!matchesTypeName(it.toString())) {
                    binding.typeInput.setError(getString(R.string.wrong_type_name_hint), null)
                }
            }
        }
        binding.colorInput.doOnTextChanged { text, _, _, _ ->
            text?.let {
                if (!matchesColorName(it.toString())) {
                    binding.colorInput.setError(getString(R.string.wrong_color_name_hint), null)
                }
            }
        }
        binding.stateInput.doOnTextChanged { text, _, _, _ ->
            text?.let {
                if (!matchesBicycleState(it.toString())) {
                    binding.stateInput.setError(getString(R.string.wrong_state_name_hint), null)
                }
            }
        }
        binding.purchasePriceInput.doOnTextChanged { text, _, _, _ ->
            text?.let {
                if (!matchesPurchasePrice(it.toString().toIntOrNull() ?: -1)) {
                    binding.purchasePriceInput.setError(
                        getString(R.string.wrong_purchase_price_hint),
                        null
                    )
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun loadParams() {
        viewModel.loadAvailableParams()
        lifecycleScope.launchWhenCreated {
            viewModel.colors.collect { newColors ->
                withContext(Dispatchers.Main) {
                    val adapter = ArrayAdapter(
                        requireContext(),
                        R.layout.dropdown_item,
                        newColors.map { it.colorName })
                    binding.colorInput.setAdapter(adapter)
                }
            }
        }
        lifecycleScope.launchWhenCreated {
            viewModel.states.collect { newStates ->
                withContext(Dispatchers.Main) {
                    val adapter = ArrayAdapter(
                        requireContext(),
                        R.layout.dropdown_item,
                        newStates.map { it.stateName })
                    binding.stateInput.setAdapter(adapter)
                }
            }
        }
        lifecycleScope.launchWhenCreated {
            viewModel.types.collect { newTypes ->
                withContext(Dispatchers.Main) {
                    val adapter = ArrayAdapter(
                        requireContext(),
                        R.layout.dropdown_item,
                        newTypes.map { it.typeName })
                    binding.typeInput.setAdapter(adapter)
                }
            }
        }
        lifecycleScope.launchWhenCreated {
            viewModel.wheelSizes.collect { newWheelSizes ->
                withContext(Dispatchers.Main) {
                    val strings = newWheelSizes.map { it.diameter.toString() }
                    val adapter = ArrayAdapter(
                        requireContext(),
                        R.layout.dropdown_item,
                        strings
                    )
                    binding.wheelSizeInput.setAdapter(adapter)
                }
            }
        }
    }

    private fun onSubmit() {
        var validatingSuccessful = true

        val name = binding.nameInput.text?.toString() ?: run {
            validatingSuccessful = false
            ""
        }

        val type = binding.typeInput.text?.toString()?.also {
            validatingSuccessful = matchesTypeName(it)
        } ?: run {
            validatingSuccessful = false
            ""
        }

        val state = binding.stateInput.text?.toString()?.also {
            validatingSuccessful = matchesBicycleState(it)
        } ?: run {
            validatingSuccessful = false
            ""
        }

        val purchasePrice = binding.purchasePriceInput.text?.toString()?.toIntOrNull()?.also {
            validatingSuccessful = matchesPurchasePrice(it)
        } ?: run {
            validatingSuccessful = false
            0
        }

        val wheelSize = binding.wheelSizeInput.text?.toString()?.toDoubleOrNull()?.also {
            validatingSuccessful = matchesWheelSize(it)
        } ?: run {
            validatingSuccessful = false
            0.0
        }

        val color = binding.colorInput.text?.toString()?.also {
            validatingSuccessful = matchesColorName(it)
        } ?: run {
            validatingSuccessful = false
            ""
        }

        val additional = binding.additionalInfoInput.text?.toString()

        if (validatingSuccessful) {
            viewModel.addBicycle(
                Bicycle(
                    name = name,
                    purchasePrice = purchasePrice,
                    type = Type(type),
                    state = State(state),
                    wheelSize = WheelSize(wheelSize),
                    color = Color(color),
                    description = additional
                )
            )
        }
    }

    private fun matchesBicycleState(state: String) = state.all { it.isLetter() || it == ' ' }

    private fun matchesColorName(name: String) =
        name.all { (it.isLetter() || it == '-') && it != ' ' }

    private fun matchesTypeName(name: String) = name.all { !it.isDigit() }

    private fun matchesPurchasePrice(price: Int) = price >= 0

    private fun matchesWheelSize(size: Double) = size in 0.0..50.0

    private fun showToast(hint: String) {
        Toast.makeText(
            requireContext(),
            hint,
            Toast.LENGTH_SHORT
        ).show()
    }
}