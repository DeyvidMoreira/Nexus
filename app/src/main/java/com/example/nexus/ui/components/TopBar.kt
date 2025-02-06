package com.example.nexus.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.example.nexus.ui.theme.NeonGreen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBar(onOpenDrawer: () -> Unit = {}) {
    TopAppBar(
        navigationIcon = {
            Icon(
                imageVector = Icons.Default.Menu,
                contentDescription = "Menu",
                modifier = Modifier
                    .clickable {
                        onOpenDrawer()
                    },
                tint = NeonGreen
            )
        },
        title = {
            Text(text = "")
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = Color.Transparent
        )
    )
}