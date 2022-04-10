package ru.nsu.fit.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap
import ru.nsu.fit.presentation.viewmodel.*

@Module
interface ViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(HomeScreenViewModel::class)
    fun bindHomeScreenViewModel(viewModel: HomeScreenViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(DetailedBicycleViewModel::class)
    fun bindDetailedBicycleViewModel(viewModel: DetailedBicycleViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(AddBicycleViewModel::class)
    fun bindAddBicycleViewModel(viewModel: AddBicycleViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(SellBicycleViewModel::class)
    fun bindSellBicycleViewModel(viewModel: SellBicycleViewModel): ViewModel

    @Binds
    fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory
}