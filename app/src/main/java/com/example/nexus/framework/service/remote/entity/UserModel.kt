package com.example.nexus.framework.service.remote.entity

import androidx.room.Entity

@Entity
data class UserModel(
    val email: String,
    val password: String
)
