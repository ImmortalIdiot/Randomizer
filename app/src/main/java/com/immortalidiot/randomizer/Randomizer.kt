package com.immortalidiot.randomizer

import android.app.Application
import com.immortalidiot.randomizer.data.di.databaseModule
import com.immortalidiot.randomizer.ui.di.uiModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

class Randomizer : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger(Level.DEBUG)
            androidContext(this@Randomizer)
            modules(databaseModule, uiModule)
        }
    }
}
