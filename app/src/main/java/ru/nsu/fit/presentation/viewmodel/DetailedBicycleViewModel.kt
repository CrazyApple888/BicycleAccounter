package ru.nsu.fit.presentation.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import ru.nsu.fit.domain.model.*
import ru.nsu.fit.domain.usecase.*
import javax.inject.Inject

class DetailedBicycleViewModel @Inject constructor(
    private val getBicycle: GetBicycleUseCase,
    private val getStates: GetAllStatesUseCase,
    private val editPrice: EditBicycleSellingUseCase,
    private val editState: EditBicycleStateUseCase,
    private val attachIssue: AttachIssueUseCase
) : ViewModel(), Loggable {

    private var currentBicycle: Bicycle? = null
    private val _bicycle = MutableSharedFlow<Bicycle>()
    val bicycle: SharedFlow<Bicycle> get() = _bicycle

    private val _error = MutableSharedFlow<Errors>()
    val error: SharedFlow<Errors> = _error.asSharedFlow()

    private val _sellCall = MutableSharedFlow<Bicycle>()
    val sellCall: SharedFlow<Bicycle> = _sellCall.asSharedFlow()

    private val _states: MutableStateFlow<List<State>> = MutableStateFlow(emptyList())
    val states: StateFlow<List<State>> = _states.asStateFlow()

    init {
        viewModelScope.launch {
            getStates().collect { result ->
                result.successOrNull { message, _ ->
                    Log.w(loggingTag, message ?: "No error message")
                }?.let { _states.value = it }
            }
        }
    }

    fun loadBicycle(id: Int) {
        viewModelScope.launch {
            getBicycle(id).collect { result ->
                when (result) {
                    is Result.Success -> result.result?.let { bike ->
                        _bicycle.emit(bike)
                        currentBicycle = bike
                    }
                    is Result.Failure -> _error.emit(Errors.LOAD_BICYCLE_FAILED)
                }
            }
        }
    }

    fun onSellButton() {
        viewModelScope.launch {
            //Unchecked because if screen loaded then currentBicycle is not null
            _sellCall.emit(currentBicycle!!)
        }
    }

    fun updatePrice(newPrice: Int) {
        viewModelScope.launch {
            currentBicycle?.id?.let {
                when (editPrice(it, newPrice)) {
                    is Result.Success -> loadBicycle(it)
                    is Result.Failure -> _error.emit(Errors.UPDATE_PRICE_FAILED)
                }
            }
        }
    }

    fun updateState(newState: State) {
        viewModelScope.launch {
            currentBicycle?.id?.let {
                when (editState(it, newState)) {
                    is Result.Success -> loadBicycle(it)
                    is Result.Failure -> _error.emit(Errors.UPDATE_STATE_FAILED)
                }
            }
        }
    }

    fun addIssue(issue: Issue) {
        viewModelScope.launch {
            currentBicycle?.id?.let {
                when (attachIssue(it, issue)) {
                    is Result.Success -> currentBicycle?.let { bicycle ->
                        loadBicycle(bicycle.id)
                    }
                    is Result.Failure -> _error.emit(Errors.ADD_ISSUE_FAILED)
                }
            }
        }
    }

    enum class Errors {
        LOAD_BICYCLE_FAILED,
        UPDATE_PRICE_FAILED,
        ADD_ISSUE_FAILED,
        UPDATE_STATE_FAILED
    }
}
