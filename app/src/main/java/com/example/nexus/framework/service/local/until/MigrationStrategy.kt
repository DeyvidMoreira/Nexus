package com.example.nexus.framework.service.local.until

enum class MigrationStrategy (val description: String) {
    ADD_NEW_COLUMN("Adicionar nova coluna"),
    REMOVE_UNUSED_COLUMN("Remover coluna não utilizada"),
    CHANGE_COLUMN_TYPE("Alterar tipo de coluna"),
    RESET_TABLE("Resetar tabela e recriar"),
}