package ru.nsu.fit.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import ru.nsu.fit.domain.model.Bicycle
import ru.nsu.fit.domain.model.Result
import ru.nsu.fit.domain.usecase.GetBicycleUseCase
import javax.inject.Inject

class DetailedBicycleViewModel @Inject constructor(
    private val getBicycleUseCase: GetBicycleUseCase
) : ViewModel() {

    private val _bicycle = MutableSharedFlow<Bicycle>()
    val bicycle = _bicycle.asSharedFlow()

    private val _isSuccess = MutableStateFlow(true)
    val isSuccess = _isSuccess.asStateFlow()

    fun loadBicycle(id: Int) {
        viewModelScope.launch {
            getBicycleUseCase(id).collect { result ->
                when (result) {
                    is Result.Success -> result.result?.let { _bicycle.emit(it) }
                    else -> _isSuccess.emit(false)
                }
            }
        }
    }
}