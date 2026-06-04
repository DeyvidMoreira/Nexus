package com.example.nexus.ui.ViewModels

import com.example.nexus.framework.service.remote.entity.UserModel
import com.example.nexus.framework.service.remote.repository.FirebaseAuthRepository
import com.example.nexus.testing.MainDispatcherRule
import com.example.nexus.testing.eventually
import com.example.nexus.ui.until.UserPreferences
import com.example.nexus.ui.until.WarningMessage
import com.example.pwdcripto.framework.contants.ConstantsMessages
import com.google.common.truth.Truth.assertThat
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class SignInViewModelTest {

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private lateinit var authRepository: FirebaseAuthRepository
    private lateinit var userPreferences: UserPreferences
    private lateinit var viewModel: SignInViewModel

    @Before
    fun setUp() {
        WarningMessage.setMessage(null)
        authRepository = mockk(relaxed = true)
        userPreferences = mockk(relaxed = true)
        viewModel = SignInViewModel(authRepository, userPreferences)
    }

    @Test
    fun `onEmailChange updates email state`() {
        viewModel.onEmailChange("user@domain.com")

        assertThat(viewModel.uiState.value.email).isEqualTo("user@domain.com")
    }

    @Test
    fun `onPasswordChange updates password state`() {
        viewModel.onPasswordChange("Password1!")

        assertThat(viewModel.uiState.value.password).isEqualTo("Password1!")
    }

    @Test
    fun `toggleRememberMe toggles remember me flag`() {
        viewModel.toggleRememberMe()

        assertThat(viewModel.uiState.value.isRememberMeChecked).isTrue()

        viewModel.toggleRememberMe()

        assertThat(viewModel.uiState.value.isRememberMeChecked).isFalse()
    }

    @Test
    fun `signIn with empty fields does not call repository and emits warning`() {
        viewModel.signIn(UserModel(email = "", password = ""))

        assertThat(WarningMessage.message.value).isEqualTo(ConstantsMessages.MESSAGE_EMPTY_FIELDS)
        coVerify(exactly = 0) { authRepository.signIn(any(), any()) }
    }

    @Test
    fun `signIn calls repository and emits success event`() = runTest {
        coEvery { authRepository.signIn(any(), any()) } returns Unit

        viewModel.signIn(UserModel(email = "user@domain.com", password = "Password1!"))

        coVerify(timeout = 1_000) {
            authRepository.signIn("user@domain.com", "Password1!")
        }
        eventually {
            viewModel.uiState.value.isSuccessful
        }
        assertThat(viewModel.signInIsSuccessful.first()).isTrue()
    }

    @Test
    fun `signIn saves credentials when remember me is enabled`() {
        viewModel.toggleRememberMe()

        viewModel.signIn(UserModel(email = "user@domain.com", password = "Password1!"))

        coVerify(timeout = 1_000) {
            userPreferences.savedUserCredentials("user@domain.com", "Password1!")
        }
    }

    @Test
    fun `signIn does not save credentials when remember me is disabled`() {
        viewModel.signIn(UserModel(email = "user@domain.com", password = "Password1!"))

        coVerify(timeout = 1_000) {
            authRepository.signIn("user@domain.com", "Password1!")
        }
        coVerify(exactly = 0) { userPreferences.savedUserCredentials(any(), any()) }
    }

    @Test
    fun `signIn failure updates state and warning message`() = runTest {
        coEvery { authRepository.signIn(any(), any()) } throws Exception("Credenciais inválidas")

        viewModel.signIn(UserModel(email = "user@domain.com", password = "wrong"))

        eventually {
            WarningMessage.message.value == "Credenciais inválidas"
        }
        assertThat(viewModel.uiState.value.isSuccessful).isFalse()
    }

    @Test
    fun `signInWithSavedCredentials signs in when saved credentials exist`() {
        coEvery { userPreferences.getSavedEmail() } returns "saved@domain.com"
        coEvery { userPreferences.getSavedPassword() } returns "Saved1!"

        viewModel.signInWithSavedCredentials()

        coVerify(timeout = 1_000) {
            authRepository.signIn("saved@domain.com", "Saved1!")
        }
    }

    @Test
    fun `signInWithSavedCredentials warns when credentials are missing`() = runTest {
        coEvery { userPreferences.getSavedEmail() } returns ""
        coEvery { userPreferences.getSavedPassword() } returns ""

        viewModel.signInWithSavedCredentials()

        eventually {
            WarningMessage.message.value == "Nenhuma credencial salva"
        }
        coVerify(exactly = 0) { authRepository.signIn(any(), any()) }
    }
}
