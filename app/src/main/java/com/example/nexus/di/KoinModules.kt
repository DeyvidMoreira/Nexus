package com.example.nexus.core.di


import com.example.nexus.service.repositories.remote.FirebaseAuthRepository
import com.example.nexus.ui.ViewModels.SignUpViewModel
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module


val appModule = module {
    viewModelOf(::SignUpViewModel)
}

val storangeModule = module {
    singleOf(::FirebaseAuthRepository)
}


val firebaseModule = module {
    single{
        Firebase
            .auth
    }
}
