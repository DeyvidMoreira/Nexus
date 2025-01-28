package com.example.nexus.framework.service.local.until

import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.pwdcripto.framework.contants.ConstantsDatabase
import com.example.pwdcripto.framework.contants.ConstantsDatabase.NEXT_VERSION_DATABASE
import com.example.pwdcripto.framework.contants.ConstantsDatabase.VERSION_DATABASE

fun getDatabaseMigrations(): Array<Migration> {
    return arrayOf(
        // Migração de versão
        Migration(VERSION_DATABASE, NEXT_VERSION_DATABASE) { database ->
            performMigration(database, 1) // Chama a função performMigration passando o número da migração
        }
    )
}

// Função para obter a estratégia de migração com base no número
fun getMigrationStrategy(migrationNumber: Int): MigrationStrategy {
    return when (migrationNumber) {
        1 -> MigrationStrategy.ADD_NEW_COLUMN
        2 -> MigrationStrategy.REMOVE_UNUSED_COLUMN
        3 -> MigrationStrategy.CHANGE_COLUMN_TYPE
        4 -> MigrationStrategy.RESET_TABLE
        else -> throw IllegalArgumentException("Estratégia de migração inválida.")
    }
}

// Função para aplicar a estratégia de migração de acordo com o número
fun performMigration(database: SupportSQLiteDatabase, migrationNumber: Int) {
    val strategy = getMigrationStrategy(migrationNumber) // Obtém a estratégia de migração

    when (strategy) {
        MigrationStrategy.ADD_NEW_COLUMN -> {
            // Exemplo de adicionar uma nova coluna
            database.execSQL(
                "ALTER TABLE ${ConstantsDatabase.TABLE_NAME} ADD COLUMN new_column TEXT"
            )
        }
        MigrationStrategy.REMOVE_UNUSED_COLUMN -> {
            // Exemplo de lógica para remover uma coluna.
            // Remover colunas no SQLite não é direto, então geralmente é necessário recriar a tabela.
            // Aqui você teria que reestruturar a tabela para excluir a coluna indesejada.
            // Isso pode ser feito com a recriação da tabela.
            database.execSQL("DROP TABLE IF EXISTS ${ConstantsDatabase.TABLE_NAME}")
            database.execSQL(
                "CREATE TABLE ${ConstantsDatabase.TABLE_NAME} (" +
                        "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        "tag TEXT NOT NULL, " +
                        "password TEXT NOT NULL, " +
                        "createdAt INTEGER NOT NULL)"
            )
        }
        MigrationStrategy.CHANGE_COLUMN_TYPE -> {
            // Exemplo de mudança de tipo de coluna
            // O SQLite não oferece um comando direto para alterar o tipo de uma coluna
            // Então, você precisaria recriar a tabela para suportar isso
            database.execSQL("DROP TABLE IF EXISTS ${ConstantsDatabase.TABLE_NAME}")
            database.execSQL(
                "CREATE TABLE ${ConstantsDatabase.TABLE_NAME} (" +
                        "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        "tag TEXT NOT NULL, " +
                        "password TEXT NOT NULL, " +
                        "createdAt INTEGER NOT NULL, " +
                        "new_column TEXT)"
            )
        }
        MigrationStrategy.RESET_TABLE -> {
            // Exemplo de resetar a tabela completamente (deletando e recriando)
            database.execSQL("DROP TABLE IF EXISTS ${ConstantsDatabase.TABLE_NAME}")
            database.execSQL(
                "CREATE TABLE ${ConstantsDatabase.TABLE_NAME} (" +
                        "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        "tag TEXT NOT NULL, " +
                        "password TEXT NOT NULL, " +
                        "createdAt INTEGER NOT NULL)"
            )
        }
    }
}