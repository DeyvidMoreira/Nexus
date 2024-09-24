package com.example.nexus.core.service.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class UserModel(
    val email: String,
    val password: String
)
