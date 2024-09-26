package com.example.nexus

import android.app.Application
import com.example.nexus.core.di.appModule
import com.example.nexus.core.di.firebaseModule
import com.example.nexus.core.di.storangeModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

class NexusAplication: Application()  {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger(Level.DEBUG)
            androidContext(this@NexusAplication)
            modules(
                appModule,
                storangeModule,
                firebaseModule
            )
        }
    }



}