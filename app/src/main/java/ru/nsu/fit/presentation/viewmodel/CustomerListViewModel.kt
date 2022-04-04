package ru.nsu.fit.presentation.viewmodel

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject

class CustomerListViewModel @Inject constructor() : ViewModel() {

    private val _client: MutableStateFlow<> = MutableStateFlow(emptyList())
}