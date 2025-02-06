package com.example.nexus.core.di


import androidx.room.Room
import com.example.nexus.framework.service.local.AppDatabase
import com.example.nexus.framework.service.local.repository.PasswordRepository
import com.example.nexus.framework.service.local.until.getDatabaseMigrations
import com.example.nexus.framework.service.remote.repository.FirebaseAuthRepository
import com.example.nexus.ui.ViewModels.PwdGeneratorViewModel
import com.example.nexus.ui.ViewModels.SignUpViewModel
import com.example.pwdcripto.framework.contants.ConstantsDatabase
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module


val appModule = module {
    viewModelOf(::SignUpViewModel)
    viewModel { PwdGeneratorViewModel(get()) }
}
val storangeModule = module {
    singleOf(::FirebaseAuthRepository)
}

val localStorageModule = module {
    single {
        Room.databaseBuilder(
            androidContext(),
            AppDatabase::class.java,
            ConstantsDatabase.DATA_BASE_NAME
        )
            .addMigrations(
                *getDatabaseMigrations()
            )
            .build()
    }
    single { get<AppDatabase>().passwordDao() }
    single { PasswordRepository(get()) }
}


val firebaseModule = module {
    single{
        Firebase
            .auth
    }
}
