package ru.nsu.fit.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import ru.nsu.fit.domain.model.Result
import ru.nsu.fit.domain.usecase.GetCustomerUseCase
import javax.inject.Inject

class DetailedCustomerViewModel @Inject constructor(
    private val getCustomerUseCase: GetCustomerUseCase
) : ViewModel() {

    private val _messages: MutableSharedFlow<Result<String>> = MutableSharedFlow()
    val messages: SharedFlow<Result<String>> = _messages.asSharedFlow()

    fun loadCustomer(id: Int) {
        viewModelScope.launch {
            getCustomerUseCase(id)
        }
    }
}