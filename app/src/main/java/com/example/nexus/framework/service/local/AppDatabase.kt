package com.example.nexus.framework.service.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.nexus.framework.service.local.dao.PasswordDao
import com.example.nexus.framework.service.local.entity.PasswordEntity
import com.example.pwdcripto.framework.contants.ConstantsDatabase

@Database(entities = [PasswordEntity::class], version = ConstantsDatabase.DATABASE_VERSION)
abstract class AppDatabase : RoomDatabase() {
    abstract fun passwordDao(): PasswordDao
}