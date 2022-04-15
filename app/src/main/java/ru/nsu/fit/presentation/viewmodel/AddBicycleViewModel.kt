package ru.nsu.fit.presentation.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import ru.nsu.fit.domain.model.*
import ru.nsu.fit.domain.usecase.*
import javax.inject.Inject

class AddBicycleViewModel @Inject constructor(
    private val addBicycleUseCase: AddBicycleUseCase,
    private val getAllColorsUseCase: GetAllColorsUseCase,
    private val getAllTypesUseCase: GetAllTypesUseCase,
    private val getAllWheelSizesUseCase: GetAllWheelSizesUseCase,
    private val getAllStatesUseCase: GetAllStatesUseCase
) : ViewModel(), Loggable {

    private val _messages: MutableSharedFlow<Result<String>> = MutableSharedFlow()
    var messages: SharedFlow<Result<String>> = _messages.asSharedFlow()

    private val _states: MutableStateFlow<List<State>> = MutableStateFlow(emptyList())
    val states: StateFlow<List<State>> = _states.asStateFlow()

    private val _types: MutableStateFlow<List<Type>> = MutableStateFlow(emptyList())
    val types: StateFlow<List<Type>> = _types.asStateFlow()

    private val _wheelSizes: MutableStateFlow<List<WheelSize>> = MutableStateFlow(emptyList())
    val wheelSizes: StateFlow<List<WheelSize>> = _wheelSizes.asStateFlow()

    private val _colors: MutableStateFlow<List<Color>> = MutableStateFlow(emptyList())
    val colors: StateFlow<List<Color>> = _colors.asStateFlow()

    fun loadAvailableParams() {
        viewModelScope.launch {
            getAllColorsUseCase().collect { result ->
                result.successOrNull { message, _ ->
                    Log.w(loggingTag, message ?: "No error message")
                }?.let { _colors.value = it }
            }
        }
        viewModelScope.launch {
            getAllStatesUseCase().collect { result ->
                result.successOrNull { message, _ ->
                    Log.w(loggingTag, message ?: "No error message")
                }?.let { _states.value = it }
            }
        }
        viewModelScope.launch {
            getAllTypesUseCase().collect { result ->
                result.successOrNull { message, _ ->
                    Log.w(loggingTag, message ?: "No error message")
                }?.let { _types.value = it }
            }
        }
        viewModelScope.launch {
            getAllWheelSizesUseCase().collect { result ->
                result.successOrNull { message, _ ->
                    Log.w(loggingTag, message ?: "No error message")
                }?.let { _wheelSizes.value = it }
            }
        }
    }

    fun addBicycle(bicycle: Bicycle) {
        viewModelScope.launch {
            addBicycleUseCase(bicycle).successOrNull { message, _ ->
                Log.w(loggingTag, message ?: "No error message")
            } ?: run {
                _messages.emit(Result.Failure())
                return@launch
            }
            _messages.emit(Result.Success())
        }
    }
}