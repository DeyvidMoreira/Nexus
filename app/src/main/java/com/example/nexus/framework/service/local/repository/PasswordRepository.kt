package com.example.nexus.framework.service.local.repository

import com.example.nexus.framework.service.local.dao.PasswordDao
import com.example.nexus.framework.service.local.entity.PasswordEntity
import com.example.nexus.ui.until.CryptoHelper
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class PasswordRepository(
    private val passwordDao: PasswordDao,
    private val cryptoHelper: CryptoHelper
) {

    private val secreKey = cryptoHelper.getOrCreateSecretKey()

    // Função para obter todas as senhas
    val allPasswords: Flow<List<PasswordEntity>> = passwordDao.getAllPasswords()
        .map { list ->
            list.map { pwd ->
                // Decriptografa a senha antes de retornar
                pwd.copy(
                    password = cryptoHelper.decryptLocalData(pwd.password,secreKey)
                )
            }
        }

    // Função para obter senhas por tag
    fun getPasswordsByTag(query: String): Flow<List<PasswordEntity>> {
        return passwordDao.getPasswordsByTag(query)
            .map { list ->
                list.map { pwd ->
                    pwd.copy(
                        password = cryptoHelper.decryptLocalData(pwd.password,secreKey)
                    )
                }
            }
    }

    // Função para salvar uma senha
    suspend fun savePassword(passwordEntity: PasswordEntity) {
        // Criptografa a senha antes de salvar
        val encryptedPassword = passwordEntity.copy(
            password = cryptoHelper.encryptLocalData(passwordEntity.password,secreKey)
        )
        passwordDao.savePassword(encryptedPassword)
    }

    // Função para editar uma senha
    suspend fun updatePassword(passwordEntity: PasswordEntity) {
        val encryptedPassword = passwordEntity.copy(
            password = cryptoHelper.encryptLocalData(passwordEntity.password,secreKey)
        )
        passwordDao.updatePassword(encryptedPassword)
    }

    // Função para deletar uma senha
    suspend fun deletePassword(passwordEntity: PasswordEntity) {
        passwordDao.deletePassword(passwordEntity)
    }
}
