package com.example.nexus.ui.ViewModels

import com.example.nexus.framework.service.remote.repository.FirebaseAuthRepository
import com.example.nexus.testing.MainDispatcherRule
import com.example.nexus.ui.until.WarningMessage
import com.example.nexus.framework.common.constants.ConstantsMessages
import com.google.common.truth.Truth.assertThat
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class SignUpViewModelTest {

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private lateinit var authRepository: FirebaseAuthRepository
    private lateinit var viewModel: SignUpViewModel

    @Before
    fun setUp() {
        WarningMessage.setMessage(null)
        authRepository = mockk(relaxed = true)
        viewModel = SignUpViewModel(authRepository)
    }

    @Test
    fun `state callbacks update registration fields`() {
        viewModel.iuState.value.onUserChange("Maria Silva")
        viewModel.iuState.value.onEmailChange("maria@domain.com")
        viewModel.iuState.value.onPasswordChange("Password1!")
        viewModel.iuState.value.onConfirmPasswordChange("Password1!")

        val state = viewModel.iuState.value

        assertThat(state.user).isEqualTo("Maria Silva")
        assertThat(state.email).isEqualTo("maria@domain.com")
        assertThat(state.password).isEqualTo("Password1!")
        assertThat(state.confirmPassword).isEqualTo("Password1!")
    }

    @Test
    fun `signUp with empty fields does not call repository`() = runTest {
        viewModel.signUp()

        assertThat(WarningMessage.message.value).isEqualTo(ConstantsMessages.MESSAGE_EMPTY_FIELDS)
        coVerify(exactly = 0) { authRepository.signUp(any(), any()) }
    }

    @Test
    fun `signUp with invalid email does not call repository`() = runTest {
        fillValidForm()
        viewModel.iuState.value.onEmailChange("invalid-email")

        viewModel.signUp()

        assertThat(WarningMessage.message.value).isEqualTo("Email inválido")
        coVerify(exactly = 0) { authRepository.signUp(any(), any()) }
    }

    @Test
    fun `signUp with weak password does not call repository`() = runTest {
        fillValidForm()
        viewModel.iuState.value.onPasswordChange("short")
        viewModel.iuState.value.onConfirmPasswordChange("short")

        viewModel.signUp()

        assertThat(WarningMessage.message.value).isEqualTo("A senha deve ter pelo menos 8 caracteres")
        coVerify(exactly = 0) { authRepository.signUp(any(), any()) }
    }

    @Test
    fun `signUp with mismatched passwords does not call repository`() = runTest {
        fillValidForm()
        viewModel.iuState.value.onConfirmPasswordChange("Different1!")

        viewModel.signUp()

        assertThat(WarningMessage.message.value).isEqualTo("As senhas não coincidem")
        coVerify(exactly = 0) { authRepository.signUp(any(), any()) }
    }

    @Test
    fun `signUp with valid fields calls repository`() = runTest {
        coEvery { authRepository.signUp(any(), any()) } returns Unit
        fillValidForm()

        viewModel.signUp()

        coVerify(timeout = 1_000) {
            authRepository.signUp("maria@domain.com", "Password1!")
        }
    }

    private fun fillValidForm() {
        viewModel.iuState.value.onUserChange("Maria Silva")
        viewModel.iuState.value.onEmailChange("maria@domain.com")
        viewModel.iuState.value.onPasswordChange("Password1!")
        viewModel.iuState.value.onConfirmPasswordChange("Password1!")
    }
}
