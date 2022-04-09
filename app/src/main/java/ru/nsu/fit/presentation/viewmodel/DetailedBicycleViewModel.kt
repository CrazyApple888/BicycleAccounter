package ru.nsu.fit.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import ru.nsu.fit.domain.model.Bicycle
import ru.nsu.fit.domain.model.Result
import ru.nsu.fit.domain.usecase.GetBicycleUseCase
import javax.inject.Inject

class DetailedBicycleViewModel @Inject constructor(
    private val getBicycleUseCase: GetBicycleUseCase
) : ViewModel() {

    private val _bicycle = MutableSharedFlow<Bicycle>()
    val bicycle: SharedFlow<Bicycle> get() = _bicycle

    private val _error = MutableStateFlow(Unit)
    val error: StateFlow<Unit> = _error.asStateFlow()

    fun loadBicycle(id: Int) {
        viewModelScope.launch {
            getBicycleUseCase(id).collect { result ->
                when (result) {
                    is Result.Success -> result.result?.let { _bicycle.emit(it) }
                    is Result.Failure -> _error.emit(Unit)
                }
            }
        }
    }
}