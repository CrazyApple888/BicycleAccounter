package ru.nsu.fit.presentation.viewmodel

import androidx.lifecycle.ViewModel
import ru.nsu.fit.domain.usecase.AddBicycleUseCase
import javax.inject.Inject

class AddBicycleViewModel @Inject constructor(
    private val addBicycleUseCase: AddBicycleUseCase
) : ViewModel() {

}