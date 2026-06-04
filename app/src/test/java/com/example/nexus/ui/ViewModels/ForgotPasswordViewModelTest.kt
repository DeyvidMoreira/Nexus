package com.example.nexus.ui.ViewModels

import com.example.nexus.core.service.repository.local.ValidationError
import com.example.nexus.framework.service.remote.repository.FirebaseAuthRepository
import com.example.nexus.testing.MainDispatcherRule
import com.example.nexus.ui.until.WarningMessage
import com.google.common.truth.Truth.assertThat
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class ForgotPasswordViewModelTest {

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private lateinit var authRepository: FirebaseAuthRepository
    private lateinit var viewModel: ForgotPasswordViewModel

    @Before
    fun setUp() {
        WarningMessage.setMessage(null)
        authRepository = mockk(relaxed = true)
        viewModel = ForgotPasswordViewModel(authRepository)
    }

    @Test
    fun `invalid email sets failure state and does not call repository`() {
        viewModel.onSendResetPasswordClick("invalid-email")

        assertThat(viewModel.resetState.value.isSuccess).isFalse()
        assertThat(WarningMessage.message.value).isEqualTo("Formato de e-mail inválido")
        coVerify(exactly = 0) { authRepository.resetPassword(any()) }
    }

    @Test
    fun `valid email sends reset password and sets success state`() = runTest {
        coEvery { authRepository.resetPassword(any()) } returns Unit

        viewModel.onSendResetPasswordClick("user@domain.com")

        coVerify(timeout = 1_000) { authRepository.resetPassword("user@domain.com") }
        assertThat(viewModel.resetState.value.isSuccess).isTrue()
        assertThat(WarningMessage.message.value).isEqualTo("E-mail enviado com sucesso!")
    }

    @Test
    fun `repository failure sets failure state and warning message`() = runTest {
        coEvery { authRepository.resetPassword(any()) } throws Exception("Erro de rede")

        viewModel.onSendResetPasswordClick("user@domain.com")

        coVerify(timeout = 1_000) { authRepository.resetPassword("user@domain.com") }
        assertThat(viewModel.resetState.value.isSuccess).isFalse()
        assertThat(WarningMessage.message.value).isEqualTo("Erro de rede")
    }

    @Test
    fun `clearState resets reset password state`() {
        viewModel.onSendResetPasswordClick("invalid-email")

        viewModel.clearState()

        assertThat(viewModel.resetState.value.isSuccess).isFalse()
        assertThat(viewModel.resetState.value.isLoading).isFalse()
        assertThat(viewModel.resetState.value.warningMessage).isNull()
    }

    @Test
    fun `mapValidationErrorToMessage maps known errors`() {
        assertThat(viewModel.mapValidationErrorToMessage(ValidationError.INVALID_EMAIL))
            .isEqualTo("Formato de e-mail inválido")
        assertThat(viewModel.mapValidationErrorToMessage(ValidationError.EMPTY_FIELDS))
            .isEqualTo("Digite seu e-mail para continuar")
        assertThat(viewModel.mapValidationErrorToMessage(ValidationError.PASSWORD_EMPTY))
            .isEqualTo("Erro de validação")
    }
}
