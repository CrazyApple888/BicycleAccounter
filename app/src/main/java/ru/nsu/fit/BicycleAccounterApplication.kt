package ru.nsu.fit

import android.app.Application
import ru.nsu.fit.di.AppComponent
import ru.nsu.fit.di.DaggerAppComponent
import ru.nsu.fit.di.DataModule

class BicycleAccounterApplication : Application() {

    lateinit var appComponent: AppComponent
        private set

    override fun onCreate() {
        super.onCreate()
        appComponent = DaggerAppComponent.builder()
            .context(applicationContext)
            .dataModule(DataModule(applicationContext))
            .build()
    }

}