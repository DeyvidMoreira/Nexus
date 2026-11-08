package com.example.nexus.framework.service.local.repository

import com.example.nexus.framework.service.local.dao.PasswordDao
import com.example.nexus.framework.service.local.entity.PasswordEntity
import com.example.nexus.ui.until.CryptoHelper
import com.google.common.truth.Truth.assertThat
import io.mockk.every
import io.mockk.mockk
import javax.crypto.SecretKey
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

class PasswordRepositoryTest {

    private val secretKey = mockk<SecretKey>()
    private lateinit var dao: FakePasswordDao
    private lateinit var cryptoHelper: CryptoHelper
    private lateinit var repository: PasswordRepository

    @Before
    fun setUp() {
        dao = FakePasswordDao()
        cryptoHelper = mockk()

        every { cryptoHelper.getOrCreateSecretKey() } returns secretKey
        every { cryptoHelper.encryptLocalData(any(), secretKey) } answers {
            "encrypted:${firstArg<String>()}"
        }
        every { cryptoHelper.decryptLocalData(any(), secretKey) } answers {
            val value = firstArg<String>()
            if (value.startsWith("encrypted:")) {
                value.removePrefix("encrypted:")
            } else {
                throw IllegalArgumentException("Value is not encrypted")
            }
        }

        repository = PasswordRepository(dao, cryptoHelper)
    }

    @Test
    fun `savePassword encrypts password before storing`() = runTest {
        repository.savePassword(password(id = 1, tag = "Email", value = "plain"))

        assertThat(dao.currentPasswords()).containsExactly(
            password(id = 1, tag = "Email", value = "encrypted:plain")
        )
    }

    @Test
    fun `updatePassword encrypts password before storing`() = runTest {
        dao.seed(password(id = 1, tag = "Email", value = "encrypted:old"))

        repository.updatePassword(password(id = 1, tag = "Email", value = "new"))

        assertThat(dao.currentPasswords()).containsExactly(
            password(id = 1, tag = "Email", value = "encrypted:new")
        )
    }

    @Test
    fun `allPasswords decrypts current single encrypted records`() = runTest {
        dao.seed(password(id = 1, tag = "Email", value = "encrypted:plain"))

        val result = repository.allPasswords.first()

        assertThat(result).containsExactly(
            password(id = 1, tag = "Email", value = "plain")
        )
    }

    @Test
    fun `allPasswords decrypts legacy double encrypted records`() = runTest {
        dao.seed(password(id = 1, tag = "Email", value = "encrypted:encrypted:plain"))

        val result = repository.allPasswords.first()

        assertThat(result).containsExactly(
            password(id = 1, tag = "Email", value = "plain")
        )
    }

    @Test
    fun `getPasswordsByTag returns decrypted matching passwords`() = runTest {
        dao.seed(
            password(id = 1, tag = "Email pessoal", value = "encrypted:p1"),
            password(id = 2, tag = "Banco", value = "encrypted:p2")
        )

        val result = repository.getPasswordsByTag("email").first()

        assertThat(result).containsExactly(
            password(id = 1, tag = "Email pessoal", value = "p1")
        )
    }

    @Test
    fun `deletePassword removes only selected password`() = runTest {
        val email = password(id = 1, tag = "Email", value = "encrypted:p1")
        val bank = password(id = 2, tag = "Banco", value = "encrypted:p2")
        dao.seed(email, bank)

        repository.deletePassword(email)

        assertThat(dao.currentPasswords()).containsExactly(bank)
    }

    @Test
    fun `deleteAllPasswords clears local storage`() = runTest {
        dao.seed(
            password(id = 1, tag = "Email", value = "encrypted:p1"),
            password(id = 2, tag = "Banco", value = "encrypted:p2")
        )

        repository.deleteAllPasswords()

        assertThat(dao.currentPasswords()).isEmpty()
    }

    private class FakePasswordDao : PasswordDao {
        private val passwords = MutableStateFlow<List<PasswordEntity>>(emptyList())

        override suspend fun savePassword(password: PasswordEntity) {
            passwords.value = passwords.value + password
        }

        override suspend fun updatePassword(password: PasswordEntity) {
            passwords.value = passwords.value.map { current ->
                if (current.id == password.id) password else current
            }
        }

        override suspend fun deletePassword(password: PasswordEntity) {
            passwords.value = passwords.value.filterNot { it.id == password.id }
        }

        override fun getAllPasswords(): Flow<List<PasswordEntity>> = passwords

        override fun getPasswordsByTag(tag: String): Flow<List<PasswordEntity>> {
            return passwords.map { list ->
                list.filter { it.tag.contains(tag, ignoreCase = true) }
            }
        }

        override suspend fun deleteAllPasswords() {
            passwords.value = emptyList()
        }

        fun seed(vararg password: PasswordEntity) {
            passwords.value = password.toList()
        }

        fun currentPasswords(): List<PasswordEntity> = passwords.value
    }

    private fun password(
        id: Int,
        tag: String,
        value: String
    ): PasswordEntity = PasswordEntity(
        id = id,
        tag = tag,
        password = value,
        createdAt = id.toLong()
    )
}
