package ru.nsu.fit.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ru.nsu.fit.domain.model.Bicycle
import ru.nsu.fit.domain.model.Result
import ru.nsu.fit.domain.usecase.GetBicycleUseCase
import javax.inject.Inject

class DetailedBicycleViewModel @Inject constructor(
    private val getBicycleUseCase: GetBicycleUseCase
) : ViewModel() {

    private val _bicycle = MutableLiveData<Bicycle>()
    val bicycle: LiveData<Bicycle> get() = _bicycle

    fun loadBicycle(id: Int) {
        viewModelScope.launch {
            getBicycleUseCase(id).collect { result ->
                when (result) {
                    is Result.Success -> result.result?.let { _bicycle.value = it }
                    //todo сделать вывод сообщений об ошибках
                    else -> {}
                }
            }
        }
    }
}