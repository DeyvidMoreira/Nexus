package com.example.nexus.framework.common.constants

import com.example.nexus.framework.service.local.until.MigrationStrategy

object ConstantsDatabase {
    const val DATABASE_VERSION = 3 // Atualizado para nova versão
    const val DATA_BASE_NAME = "app_database"
    const val TABLE_NAME = "password_table"

    // Usar lista ordenada de migrações
    val migrations = listOf(
        1 to MigrationStrategy.ADD_NEW_COLUMN,
        2 to MigrationStrategy.REMOVE_UNUSED_COLUMN,
        //3 to MigrationStrategy.CHANGE_COLUMN_TYPE // Nova migração
    )
}