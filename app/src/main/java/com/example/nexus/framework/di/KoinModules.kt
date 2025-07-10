package com.example.nexus.core.di


import android.os.Build
import androidx.annotation.RequiresApi
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.nexus.framework.service.local.AppDatabase
import com.example.nexus.framework.service.local.repository.PasswordRepository
import com.example.nexus.framework.service.local.until.getDatabaseMigrations
import com.example.nexus.framework.service.remote.repository.FirebaseAuthRepository
import com.example.nexus.ui.ViewModels.ForgotPasswordViewModel
import com.example.nexus.ui.ViewModels.PwdGeneratorViewModel
import com.example.nexus.ui.ViewModels.SignInViewModel
import com.example.nexus.ui.ViewModels.SignUpViewModel
import com.example.nexus.ui.until.CryptoHelper
import com.example.nexus.ui.until.UserPreferences
import com.example.pwdcripto.framework.contants.ConstantsDatabase
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module


@RequiresApi(Build.VERSION_CODES.R)
// Módulo de ViewModels
val viewModelModule = module {
    viewModelOf(::SignUpViewModel)
    viewModelOf(::SignInViewModel)
    viewModelOf(::PwdGeneratorViewModel)
    viewModel { ForgotPasswordViewModel(get()) }
}

// Módulo de dependências de segurança
val securityModule = module {
    single { CryptoHelper(androidContext()) }
    single { UserPreferences(androidContext()) }
}

// Módulo de armazenamento de dados
val storangeModule = module {
    singleOf(::FirebaseAuthRepository)
}

// Módulo de banco de dados local
val localStorageModule = module {
    single {
        Room.databaseBuilder(
            androidContext(),
            AppDatabase::class.java,
            ConstantsDatabase.DATA_BASE_NAME
        )
            .addMigrations(*getDatabaseMigrations())
            .setJournalMode(RoomDatabase.JournalMode.TRUNCATE)
            .build()
    }
    single { get<AppDatabase>().passwordDao() }
    single { PasswordRepository(
        passwordDao = get(),
        cryptoHelper = get()
    ) }
}

// Módulo de Firebase
val firebaseModule = module {
    single {
        Firebase
            .auth
    }
}

