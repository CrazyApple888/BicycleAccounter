package ru.nsu.fit.di

import android.content.Context
import dagger.BindsInstance
import dagger.Component
import ru.nsu.fit.ui.fragment.HomeScreenFragment
import javax.inject.Singleton

@Singleton
@Component(modules = [DataModule::class])
interface AppComponent {

    fun inject(fragment: HomeScreenFragment)

    @Component.Builder
    interface AppComponentBuilder {
        fun context(@BindsInstance context: Context): AppComponentBuilder

        fun dataModule(module: DataModule): AppComponentBuilder

        fun build(): AppComponent
    }

}