package ru.nsu.fit.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import ru.nsu.fit.data.dao.CustomerDao
import ru.nsu.fit.domain.model.Result
import ru.nsu.fit.domain.model.SimpleCustomer
import ru.nsu.fit.domain.usecase.GetSimpleCustomerAllUseCase
import javax.inject.Inject

class CustomerListViewModel @Inject constructor(
    private val getSimpleCustomerAllUseCase: GetSimpleCustomerAllUseCase,
    private val customerDao: CustomerDao
) : ViewModel() {

    init {
        loadCustomers()
    }

    private val _messages = MutableSharedFlow<Result<String>>(replay = 1)
    val messages = _messages.asSharedFlow()

    private val _customers = MutableSharedFlow<List<SimpleCustomer>>(replay = 1)
    val customers = _customers.asSharedFlow()

    fun loadBuffer() {
        viewModelScope.launch {
            _customers.emit(_customers.replayCache.lastOrNull() ?: emptyList())
        }
    }

    private fun loadCustomers() {
        viewModelScope.launch {
            getSimpleCustomerAllUseCase().collect { result ->
                when (result) {
                    is Result.Success -> {
                        _customers.emit(result.result!!)
                    }
                    is Result.Failure -> {
                        _messages.emit(Result.Failure())
                    }
                }
            }
        }
    }
}