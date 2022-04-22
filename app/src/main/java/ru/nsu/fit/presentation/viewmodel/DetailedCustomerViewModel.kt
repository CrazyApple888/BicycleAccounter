package ru.nsu.fit.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import ru.nsu.fit.domain.model.DetailedCustomer
import ru.nsu.fit.domain.model.Result
import ru.nsu.fit.domain.usecase.GetDetailedCustomerUseCase
import javax.inject.Inject

class DetailedCustomerViewModel @Inject constructor(
    private val getDetailedCustomerUseCase: GetDetailedCustomerUseCase
) : ViewModel() {
    private val _messages: MutableSharedFlow<Result<String>> = MutableSharedFlow()
    val messages: SharedFlow<Result<String>> = _messages.asSharedFlow()

    private val _customer: MutableSharedFlow<DetailedCustomer> = MutableSharedFlow(replay = 1)
    val customer: SharedFlow<DetailedCustomer> = _customer.asSharedFlow()


    fun loadCustomer(id: Int) {
        viewModelScope.launch {
            getDetailedCustomerUseCase(id).collect { result ->
                when (result) {
                    is Result.Failure -> _messages.emit(Result.Failure())
                    is Result.Success<DetailedCustomer> -> _customer.emit(result.result!!)
                }
            }
        }
    }
}