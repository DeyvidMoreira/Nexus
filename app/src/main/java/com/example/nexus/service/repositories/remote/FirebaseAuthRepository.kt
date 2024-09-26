package com.example.nexus.service.repositories.remote

import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.tasks.await

class FirebaseAuthRepository (
    private val firebaseAuth: FirebaseAuth
) {

    suspend fun signUp(email: String, password: String) {
        firebaseAuth.createUserWithEmailAndPassword(email, password)
            .await()

    }

}