package ru.nsu.fit.ui.fragment

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.activity.result.ActivityResultCaller
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.core.view.isGone
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


class AddBicycleFragment : Fragment(), ActivityResultCaller {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private lateinit var viewModel: AddBicycleViewModel

    private var _binding: FragmentAddBicycleBinding? = null
    private val binding: FragmentAddBicycleBinding get() = checkNotNull(_binding) { "Binding is not initialized" }

    private val isPermissionGranted: Boolean
        get() =
            ActivityCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.READ_EXTERNAL_STORAGE
            ) == PackageManager.PERMISSION_GRANTED

    private val requestPermissionLauncher =
        registerForActivityResult(
            ActivityResultContracts.RequestPermission()
        ) { granted: Boolean ->
            if (!granted) {
                showToastShort(getString(R.string.add_bicycle_needs_permission))
            }
        }

    private val picker = registerForActivityResult(ActivityResultContracts.GetContent(), ::foo)

    // Callback in registerForActivityResult should be initialized at the moment above
    // and we must call registerForActivityResult before fragment created so we have to use this shitty hack
    // because viewModel is not initialized at this moment
    private fun foo(uri: Uri?) {
        viewModel.addPicture(uri)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        (requireActivity().application as BicycleAccounterApplication).appComponent.inject(this)
        requestPermissionLauncher.launch(Manifest.permission.READ_EXTERNAL_STORAGE)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAddBicycleBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this, viewModelFactory)[AddBicycleViewModel::class.java]
        loadParams()
        setUpValidators()
        initListeners()
    }

    private fun initListeners() {
        lifecycleScope.launchWhenCreated {
            viewModel.messages.collect {
                when (it) {
                    is Result.Success -> showToastShort(getString(R.string.bicycle_added_successfully))
                    is Result.Failure -> showToastShort(getString(R.string.bicycle_adding_failed))
                }
            }
        }
        lifecycleScope.launchWhenStarted {
            binding.bikeImage.isGone = false
            viewModel.image.collect { binding.bikeImage.setImageBitmap(it) }
        }
        binding.addPhoto.setOnClickListener {
            if (isPermissionGranted) {
                picker.launch(MIME_TYPE_IMAGE)
            } else {
                requestPermissionLauncher.launch(Manifest.permission.READ_EXTERNAL_STORAGE)
            }
        }
        binding.submitButton.setOnClickListener { onSubmit() }
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
                name = name,
                purchasePrice = purchasePrice,
                type = type,
                state = state,
                wheelSize = wheelSize,
                color = color,
                description = additional
            )
        }
    }

    private fun matchesBicycleState(state: String) = state.all { it.isLetter() || it == ' ' }

    private fun matchesColorName(name: String) =
        name.all { (it.isLetter() || it == '-') && it != ' ' }

    private fun matchesTypeName(name: String) = name.all { !it.isDigit() }

    private fun matchesPurchasePrice(price: Int) = price >= 0

    private fun matchesWheelSize(size: Double) = size in 0.0..50.0

    private fun showToastShort(hint: String) {
        Toast.makeText(
            requireContext(),
            hint,
            Toast.LENGTH_SHORT
        ).show()
    }

    private companion object {
        const val MIME_TYPE_IMAGE = "image/*"
    }
}