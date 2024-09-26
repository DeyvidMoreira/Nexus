package com.example.nexus

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.example.nexus.ui.navigation.MainApp
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase


private const val TAG = "Tag"

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val auth = Firebase.auth
        Log.i(TAG, "auth: $auth")
        /*  auth.createUserWithEmailAndPassword(
                "deyvidtec@gmail.com",
                "12345678"
            ).addOnCompleteListener{task ->
                if (task.isSuccessful){
                    Log.i(TAG, "createUser: Sucesso")
                }else{
                    Log.i(TAG, "createUser: Falha -> ${task.exception}")
                }

            }
        */

        auth.signInWithEmailAndPassword(
            "deyvidtec@gmail.com",
            "12345678"
        ).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                Log.i(TAG, "createUser: Sucesso")
            } else {
                Log.i(TAG, "createUser: Falha -> ${task.exception}")
            }

            enableEdgeToEdge()
            setContent {
                MainApp()
            }
        }


    }
}
