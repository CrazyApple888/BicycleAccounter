package ru.nsu.fit.ui.fragment

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
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

        binding.submitButton.setOnClickListener { onSubmit() }
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
                        strings)
                    binding.wheelSizeInput.setAdapter(adapter)
                }
            }
        }
    }

    private fun onSubmit() {
        var validatingSuccessful = true

        val name = binding.nameInput.text?.toString()?.also {
            validatingSuccessful = matchesBicycleName(it)
        } ?: run {
            //todo добавить подсвечивание полей
            validatingSuccessful = false
            ""
        }

        val type = binding.typeInput.text?.toString()?.also {
            validatingSuccessful = matchesTypeName(it)
        } ?: run {
            //todo добавить подсвечивание полей
            validatingSuccessful = false
            ""
        }

        val state = binding.stateInput.text?.toString()?.also {
            validatingSuccessful = matchesBicycleState(it)
        } ?: run {
            //todo добавить подсвечивание полей
            validatingSuccessful = false
            ""

        }

        val wheelSize = binding.wheelSizeInput.text?.toString()?.toDoubleOrNull()?.also {
            validatingSuccessful = matchesWheelSize(it)
        } ?: run {
            //todo добавить подсвечивание полей
            validatingSuccessful = false
            0.0
        }

        val color = binding.colorInput.text?.toString()?.also {
            validatingSuccessful = matchesColorName(it)
        } ?: run {
            //todo добавить подсвечивание полей
            validatingSuccessful = false
            ""
        }

        val additional = binding.additionalInfoInput.text?.toString() ?: run {
            //todo добавить подсвечивание полей
            validatingSuccessful = false
        }

        if (validatingSuccessful) {
            viewModel.addBicycle(
                Bicycle(
                    name = name,
                    purchasePrice = 100,
                    type = Type(type),
                    state = State(state),
                    wheelSize = WheelSize(wheelSize),
                    color = Color(color)
                )
            )
        }
    }

    private fun matchesBicycleState(state: String) = state.all { it.isLetter() }

    private fun matchesBicycleName(name: String) = name.all { true }

    private fun matchesColorName(name: String) = name.all { it.isLetter() || '-' == it }

    private fun matchesTypeName(name: String) = name.all { it.isLetter() }

    private fun matchesWheelSize(size: Double) = size in 0.0..50.0

    private fun showInputHint(hint: String) {
        Toast.makeText(
            requireContext(),
            hint,
            Toast.LENGTH_SHORT
        ).show()
    }
}