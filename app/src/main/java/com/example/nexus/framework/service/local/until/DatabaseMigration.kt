package com.example.nexus.framework.service.local.until

import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.pwdcripto.framework.contants.ConstantsDatabase

fun getDatabaseMigrations(): Array<Migration> {
    return ConstantsDatabase.migrations.map { (targetVersion, strategy) ->
        Migration(targetVersion - 1, targetVersion) { database ->
            applyMigrationStrategy(database, strategy, targetVersion)
        }
    }.toTypedArray()
}

private fun applyMigrationStrategy(
    database: SupportSQLiteDatabase,
    strategy: MigrationStrategy,
    version: Int
) {
    when (strategy) {
        MigrationStrategy.ADD_NEW_COLUMN -> addColumn(database, version)
        MigrationStrategy.REMOVE_UNUSED_COLUMN -> removeColumn(database, version)
        MigrationStrategy.CHANGE_COLUMN_TYPE -> changeColumnType(database, version)
        MigrationStrategy.RESET_TABLE -> resetTable(database)
    }
}

private fun addColumn(database: SupportSQLiteDatabase, version: Int) {
    when (version) {
        1 -> database.execSQL("ALTER TABLE ${ConstantsDatabase.TABLE_NAME} ADD COLUMN new_column TEXT")
        3 -> database.execSQL("ALTER TABLE ${ConstantsDatabase.TABLE_NAME} ADD COLUMN another_column INTEGER")
    }
}

private fun removeColumn(database: SupportSQLiteDatabase, version: Int) {
    // Implementação segura para versões antigas
    database.execSQL("""
        CREATE TABLE temp_table (
            id INTEGER PRIMARY KEY AUTOINCREMENT,
            tag TEXT NOT NULL,
            password TEXT NOT NULL,
            createdAt INTEGER NOT NULL
        )
    """)

    database.execSQL("""
        INSERT INTO temp_table(id, tag, password, createdAt)
        SELECT id, tag, password, createdAt 
        FROM ${ConstantsDatabase.TABLE_NAME}
    """)

    database.execSQL("DROP TABLE ${ConstantsDatabase.TABLE_NAME}")
    database.execSQL("ALTER TABLE temp_table RENAME TO ${ConstantsDatabase.TABLE_NAME}")
}

private fun changeColumnType(database: SupportSQLiteDatabase, version: Int) {
    // Exemplo para versão 3
    database.execSQL("ALTER TABLE ${ConstantsDatabase.TABLE_NAME} RENAME COLUMN old_column TO tmp_column")
    database.execSQL("ALTER TABLE ${ConstantsDatabase.TABLE_NAME} ADD COLUMN old_column INTEGER")
    database.execSQL("UPDATE ${ConstantsDatabase.TABLE_NAME} SET old_column = CAST(tmp_column AS INTEGER)")
    database.execSQL("ALTER TABLE ${ConstantsDatabase.TABLE_NAME} DROP COLUMN tmp_column")
}

private fun resetTable(database: SupportSQLiteDatabase) {
    database.execSQL("DROP TABLE IF EXISTS ${ConstantsDatabase.TABLE_NAME}")
    database.execSQL("""
        CREATE TABLE ${ConstantsDatabase.TABLE_NAME} (
            id INTEGER PRIMARY KEY AUTOINCREMENT,
            tag TEXT NOT NULL,
            password TEXT NOT NULL,
            createdAt INTEGER NOT NULL
        )
    """)
}