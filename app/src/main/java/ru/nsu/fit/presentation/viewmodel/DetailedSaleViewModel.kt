package ru.nsu.fit.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import ru.nsu.fit.domain.model.Result
import ru.nsu.fit.domain.model.SaleDetailed
import ru.nsu.fit.domain.usecase.GetSaleDetailedUseCase
import javax.inject.Inject

class DetailedSaleViewModel @Inject constructor(
    private val getSaleDetailedUseCase: GetSaleDetailedUseCase
) : ViewModel() {

    private val _saleLoadedEvent = MutableSharedFlow<SaleDetailed>(replay = 1)
    val saleLoadedEvent: SharedFlow<SaleDetailed> get() = _saleLoadedEvent.asSharedFlow()

    private val _errorEvent = MutableSharedFlow<Unit>()
    val errorEvent: SharedFlow<Unit> get() = _errorEvent.asSharedFlow()

    fun loadSale(id: Int) {
        viewModelScope.launch {
            getSaleDetailedUseCase(id).collect { result ->
                when (result) {
                    is Result.Success -> _saleLoadedEvent.emit(result.result!!)
                    is Result.Failure -> _errorEvent.emit(Unit)
                }
            }
        }
    }
}
