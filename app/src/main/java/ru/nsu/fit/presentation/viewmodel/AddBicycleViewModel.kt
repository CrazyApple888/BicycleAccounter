package ru.nsu.fit.presentation.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import ru.nsu.fit.domain.model.*
import ru.nsu.fit.domain.usecase.AddBicycleUseCase
import javax.inject.Inject

class AddBicycleViewModel @Inject constructor(
    private val addBicycleUseCase: AddBicycleUseCase
) : ViewModel() {

    private var _states: MutableStateFlow<List<State>> = MutableStateFlow(emptyList())
    val states: StateFlow<List<State>> = _states.asStateFlow()

    private var _types: MutableStateFlow<List<Type>> = MutableStateFlow(emptyList())
    val types: StateFlow<List<Type>> = _types.asStateFlow()

    private var _wheelSizes: MutableStateFlow<List<WheelSize>> = MutableStateFlow(emptyList())
    val wheelSizes: StateFlow<List<WheelSize>> = _wheelSizes.asStateFlow()

    private var _colors: MutableStateFlow<List<Color>> = MutableStateFlow(emptyList())
    val colors: StateFlow<List<Color>> = _colors.asStateFlow()

    fun loadAvailableParams() {
        viewModelScope.launch {
            addBicycleUseCase.getColors().collect { result ->
                result.successOrNull { message, _ ->
                    Log.w(LoggingTags.VIEWMODEL, message ?: "No error message")
                }?.let { _colors.value = it }
            }
        }
        viewModelScope.launch {
            addBicycleUseCase.getStates().collect { result ->
                result.successOrNull { message, _ ->
                    Log.w(LoggingTags.VIEWMODEL, message ?: "No error message")
                }?.let { _states.value = it }
            }
        }
        viewModelScope.launch {
            addBicycleUseCase.getTypes().collect { result ->
                result.successOrNull { message, _ ->
                    Log.w(LoggingTags.VIEWMODEL, message ?: "No error message")
                }?.let { _types.value = it }
            }
        }
        viewModelScope.launch {
            addBicycleUseCase.getWheelSizes().collect { result ->
                result.successOrNull { message, _ ->
                    Log.w(LoggingTags.VIEWMODEL, message ?: "No error message")
                }?.let { _wheelSizes.value = it }
            }
        }
    }

    fun addBicycle(bicycle: Bicycle) {
        viewModelScope.launch {
            addBicycleUseCase.addBicycle(bicycle).successOrNull { message, _ ->
                Log.w(LoggingTags.VIEWMODEL, message ?: "No error message")
            }
        }
    }
}