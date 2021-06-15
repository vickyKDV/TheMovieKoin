package com.vickykdv.themoviekoin

import android.app.Application
import com.vickykdv.themoviekoin.di.*
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.Koin
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

class MyApps:Application() {
    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@MyApps)
            androidLogger(Level.DEBUG)
            printLogger(Level.DEBUG)
            modules(listOf(networkModule, DataSourceModule, FactoryModule, appModule, repoModule,
                ViewModelModule))
        }
    }
}