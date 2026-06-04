package com.example.nexus.ui.ViewModels

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.example.nexus.framework.service.local.entity.PasswordEntity
import com.example.nexus.framework.service.local.repository.PasswordRepository
import com.example.nexus.framework.service.remote.repository.FirebaseAuthRepository
import com.example.nexus.testing.MainDispatcherRule
import com.example.nexus.ui.until.WarningMessage
import com.example.pwdcripto.framework.contants.ConstantsCharacters
import com.example.pwdcripto.framework.contants.ConstantsMessages
import com.google.common.truth.Truth.assertThat
import io.mockk.coVerify
import io.mockk.every
import io.mockk.verify
import io.mockk.mockk
import io.mockk.slot
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class PwdGeneratorViewModelTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private lateinit var passwordRepository: PasswordRepository
    private lateinit var firebaseAuthRepository: FirebaseAuthRepository
    private lateinit var viewModel: PwdGeneratorViewModel

    @Before
    fun setUp() {
        WarningMessage.setMessage(null)
        passwordRepository = mockk(relaxed = true)
        firebaseAuthRepository = mockk(relaxed = true)

        every { passwordRepository.allPasswords } returns flowOf(emptyList())
        every { passwordRepository.getPasswordsByTag(any()) } returns flowOf(emptyList())

        viewModel = PwdGeneratorViewModel(passwordRepository, firebaseAuthRepository)
    }

    @Test
    fun `updateOption toggles selected character groups`() {
        viewModel.updateOption("upper", true)
        viewModel.updateOption("lower", true)
        viewModel.updateOption("number", true)
        viewModel.updateOption("special", true)

        val state = viewModel.state.value

        assertThat(state.upperChecked).isTrue()
        assertThat(state.lowChecked).isTrue()
        assertThat(state.numChecked).isTrue()
        assertThat(state.especialChecked).isTrue()
    }

    @Test
    fun `updatePasswordLength updates length and slider value`() {
        viewModel.updatePasswordLength(16)

        assertThat(viewModel.state.value.passwordLength).isEqualTo(16)
        assertThat(viewModel.state.value.sliderValue).isEqualTo(16f)
    }

    @Test
    fun `generatePassword warns when no character group is selected`() {
        viewModel.updatePasswordLength(12)

        viewModel.generatePassword()

        assertThat(viewModel.state.value.warningMessage)
            .isEqualTo(ConstantsMessages.MESSAGE_NO_SELECTED_OPTION)
        assertThat(viewModel.state.value.generatedPassword).isNull()
    }

    @Test
    fun `generatePassword warns when length is invalid`() {
        viewModel.updateOption("upper", true)

        viewModel.generatePassword()

        assertThat(viewModel.state.value.warningMessage)
            .isEqualTo(ConstantsMessages.MESSAGE_NO_PASSWORD_LENGTH)
        assertThat(viewModel.state.value.generatedPassword).isNull()
    }

    @Test
    fun `generatePassword creates password with selected character set and requested length`() {
        viewModel.updateOption("upper", true)
        viewModel.updateOption("number", true)
        viewModel.updatePasswordLength(24)

        viewModel.generatePassword()

        val generatedPassword = requireNotNull(viewModel.state.value.generatedPassword)
        val allowedCharacters = ConstantsCharacters.UPPER_CASE_COMPLETED + ConstantsCharacters.NUMBERS

        assertThat(generatedPassword).hasLength(24)
        assertThat(generatedPassword.all { it in allowedCharacters }).isTrue()
        assertThat(viewModel.state.value.warningMessage).isNull()
    }

    @Test
    fun `savePassword warns when tag is empty`() {
        viewModel.savePassword("", "generated-password")

        assertThat(viewModel.state.value.warningMessage).isEqualTo(ConstantsMessages.MESSAGE_NO_TAG)
        coVerify(exactly = 0) { passwordRepository.savePassword(any()) }
    }

    @Test
    fun `savePassword warns when password was not generated`() {
        viewModel.savePassword("Email", "")

        assertThat(viewModel.state.value.warningMessage)
            .isEqualTo(ConstantsMessages.MESSAGE_NO_GENERATE_PASSWORD)
        coVerify(exactly = 0) { passwordRepository.savePassword(any()) }
    }

    @Test
    fun `savePassword sends plain password to repository`() = runTest {
        val savedPassword = slot<PasswordEntity>()

        viewModel.savePassword("Email", "plain-password")

        coVerify(timeout = 1_000) { passwordRepository.savePassword(capture(savedPassword)) }
        assertThat(savedPassword.captured.tag).isEqualTo("Email")
        assertThat(savedPassword.captured.password).isEqualTo("plain-password")
    }

    @Test
    fun `getPasswordsByTag updates filtered query source`() {
        val observer = Observer<List<PasswordEntity>> {}
        viewModel.filteredPasswords.observeForever(observer)

        viewModel.getPasswordsByTag("email")

        verify(timeout = 1_000) { passwordRepository.getPasswordsByTag("email") }
        viewModel.filteredPasswords.removeObserver(observer)
    }

    @Test
    fun `getDecryptedPassword returns repository-facing plain password`() {
        val password = PasswordEntity(tag = "Email", password = "plain-password")

        assertThat(viewModel.getDecryptedPassword(password)).isEqualTo("plain-password")
    }
}
