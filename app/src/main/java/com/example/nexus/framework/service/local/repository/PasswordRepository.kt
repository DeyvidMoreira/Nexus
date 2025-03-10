package com.example.nexus.framework.service.local.repository

import com.example.nexus.framework.service.local.dao.PasswordDao
import com.example.nexus.framework.service.local.entity.PasswordEntity
import kotlinx.coroutines.flow.Flow

class PasswordRepository(private val passwordDao: PasswordDao) {

    // Função para obter todas as senhas
    val allPasswords: Flow<List<PasswordEntity>> = passwordDao.getAllPasswords()

    // Função para obter senhas por tag
    fun getPasswordsByTag(tag: String): Flow<List<PasswordEntity>> {
        return passwordDao.getPasswordsByTag(tag)
    }

    // Função para salvar uma senha
    suspend fun savePassword(passwordEntity: PasswordEntity) {
        passwordDao.savePassword(passwordEntity)
    }

    // Função para editar uma senha
    suspend fun updatePassword(passwordEntity: PasswordEntity) {
        passwordDao.updatePassword(passwordEntity)
    }

    // Função para deletar uma senha
    suspend fun deletePassword(passwordEntity: PasswordEntity) {
        passwordDao.deletePassword(passwordEntity)
    }
}