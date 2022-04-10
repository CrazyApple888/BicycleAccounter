package ru.nsu.fit.presentation.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import ru.nsu.fit.domain.model.Loggable
import ru.nsu.fit.domain.model.Result
import ru.nsu.fit.domain.usecase.SellBicycleUseCase
import javax.inject.Inject

class SellBicycleViewModel @Inject constructor(
    private val sellBicycleUseCase: SellBicycleUseCase
) : ViewModel(), Loggable {

    private val _isSuccessfullySold = MutableStateFlow<Boolean?>(null)
    val isSuccessfullySold get() = _isSuccessfullySold.asStateFlow()

    private var sellJob: Job? = null

    fun sell(bikeId: Int, price: Double, customerName: String, customerPhone: String) {
        if (sellJob != null) {
            return
        }
        sellJob = viewModelScope.launch {
            when(val result = sellBicycleUseCase(bikeId, price, customerName, customerPhone)) {
                is Result.Success<*> -> _isSuccessfullySold.emit(true)
                is Result.Failure<*> -> {
                    result.message?.let { Log.e(loggingTag, it) }
                    _isSuccessfullySold.emit(false)
                }
            }
            sellJob = null
        }
    }

}