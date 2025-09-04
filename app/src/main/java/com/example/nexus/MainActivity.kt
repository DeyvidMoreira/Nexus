package com.example.nexus

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import com.example.nexus.ui.navigation.MainApp
import com.example.nexus.ui.theme.NexusTheme

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            NexusTheme{
                MainApp()
            }
        }
    }


}



