package ru.nsu.fit.presentation.viewmodel

import android.graphics.Bitmap
import android.net.Uri
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.async
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
    private val getAllStatesUseCase: GetAllStatesUseCase,
    private val getImageUseCase: GetImageUseCase
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

    private var imageDeferred: Deferred<Bitmap>? = null

    private val _image = MutableSharedFlow<Bitmap>()
    val image: SharedFlow<Bitmap> get() = _image.asSharedFlow()

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

    fun addPicture(uri: Uri?) {
        if (uri == null) return
        imageDeferred = viewModelScope.async { getImageUseCase(uri).also { _image.emit(it) } }
    }

    fun addBicycle(
        name: String,
        purchasePrice: Int,
        type: String,
        state: String,
        wheelSize: Double,
        color: String,
        description: String?
    ) {
        viewModelScope.launch {
            val bicycle =
                Bicycle(
                    name = name,
                    purchasePrice = purchasePrice,
                    type = Type(type),
                    state = State(state),
                    wheelSize = WheelSize(wheelSize),
                    color = Color(color),
                    description = description,
                    picture = imageDeferred?.await()
                )
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