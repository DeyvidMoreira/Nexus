package com.example.nexus.framework.di

import android.app.Application
import android.os.Build
import androidx.annotation.RequiresApi
import com.example.nexus.core.di.firebaseModule
import com.example.nexus.core.di.localStorageModule
import com.example.nexus.core.di.securityModule
import com.example.nexus.core.di.storangeModule
import com.example.nexus.core.di.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

class NexusAplication: Application()  {

    @RequiresApi(Build.VERSION_CODES.R)
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger(Level.DEBUG)
            androidContext(this@NexusAplication)
            modules(
                viewModelModule,
                securityModule,
                storangeModule,
                localStorageModule,
                firebaseModule,

            )
        }
    }



}