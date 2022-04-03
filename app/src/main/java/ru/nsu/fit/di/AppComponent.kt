package ru.nsu.fit.di

import android.content.Context
import dagger.BindsInstance
import dagger.Component
import ru.nsu.fit.ui.fragment.DetailedBicycleFragment
import ru.nsu.fit.ui.fragment.HomeScreenFragment
import ru.nsu.fit.ui.fragment.SellBicycleFragment
import javax.inject.Singleton

@Singleton
@Component(modules = [DataModule::class, RepositoryModule::class, MapperModule::class, ViewModelModule::class])
interface AppComponent {

    fun inject(fragment: HomeScreenFragment)

    fun inject(fragment: DetailedBicycleFragment)

    fun inject(fragment: SellBicycleFragment)

    @Component.Builder
    interface AppComponentBuilder {
        fun context(@BindsInstance context: Context): AppComponentBuilder

        fun dataModule(module: DataModule): AppComponentBuilder

        fun build(): AppComponent
    }

}