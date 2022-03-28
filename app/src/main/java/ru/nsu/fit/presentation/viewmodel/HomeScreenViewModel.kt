package ru.nsu.fit.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ru.nsu.fit.domain.model.Result
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
            getAllBicyclesUseCase().collect { result ->
                _bicycles.value = when (result) {
                    is Result.Success -> result.result
                    //todo сделать вывод сообщений об ошибках
                    is Result.Failure -> emptyList()
                }
            }
        }
    }

}