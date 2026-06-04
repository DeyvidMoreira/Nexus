package com.example.nexus.framework.di

import android.app.Application
import com.example.nexus.BuildConfig
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

class NexusAplication: Application()  {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            if (BuildConfig.DEBUG) {
                androidLogger(Level.DEBUG)
            }
            androidContext(this@NexusAplication)
            modules(
                viewModelModule,
                securityModule,
                storageModule,
                localStorageModule,
                firebaseModule,

            )
        }
    }



}