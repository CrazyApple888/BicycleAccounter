package ru.nsu.fit.presentation.viewmodel

import androidx.lifecycle.ViewModel
import ru.nsu.fit.domain.usecase.GetSimpleCustomerAllUseCase
import javax.inject.Inject

class DetailedCustomerViewModel @Inject constructor(
    getCustomerUseCase: GetSimpleCustomerAllUseCase
) : ViewModel() {
}