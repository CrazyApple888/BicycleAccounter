package ru.nsu.fit.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ru.nsu.fit.domain.model.SimpleBicycle
import ru.nsu.fit.domain.usecase.GetAllBicyclesUseCase
import javax.inject.Inject

class HomeScreenViewModel @Inject constructor(
    private val getAllBicyclesUseCase: GetAllBicyclesUseCase
) : ViewModel() {

    private val _bicycles = MutableLiveData<List<SimpleBicycle>>()
    val bicycles: LiveData<List<SimpleBicycle>> get() = _bicycles

    fun loadBicycles() {
        viewModelScope.launch {
            getAllBicyclesUseCase().collect { list ->
                _bicycles.value = list
            }
        }
    }

}