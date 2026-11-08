package com.example.nexus.framework.service.remote.repository

import com.google.android.gms.tasks.Tasks
import com.google.common.truth.Truth.assertThat
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthException
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthInvalidUserException
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import com.google.firebase.auth.FirebaseAuthWeakPasswordException
import com.google.firebase.auth.FirebaseUser
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

class FirebaseAuthRepositoryTest {

    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var repository: FirebaseAuthRepository

    @Before
    fun setUp() {
        firebaseAuth = mockk(relaxed = true)
        repository = FirebaseAuthRepository(firebaseAuth)
    }

    @Test
    fun `signUp completes when Firebase creates user`() = runTest {
        every {
            firebaseAuth.createUserWithEmailAndPassword("user@domain.com", "Password1!")
        } returns Tasks.forResult(mockk<AuthResult>())

        repository.signUp("user@domain.com", "Password1!")

        verify {
            firebaseAuth.createUserWithEmailAndPassword("user@domain.com", "Password1!")
        }
    }

    @Test
    fun `signUp maps email collision error`() = runTest {
        every {
            firebaseAuth.createUserWithEmailAndPassword(any(), any())
        } returns Tasks.forException(
            FirebaseAuthUserCollisionException("ERROR_EMAIL_ALREADY_IN_USE", "Email in use")
        )

        val result = runCatching { repository.signUp("user@domain.com", "Password1!") }

        assertThat(result.exceptionOrNull()).hasMessageThat().isEqualTo("O email já está em uso.")
    }

    @Test
    fun `signUp maps weak password error`() = runTest {
        every {
            firebaseAuth.createUserWithEmailAndPassword(any(), any())
        } returns Tasks.forException(
            FirebaseAuthWeakPasswordException("ERROR_WEAK_PASSWORD", "Weak password", "Reason")
        )

        val result = runCatching { repository.signUp("user@domain.com", "weak") }

        assertThat(result.exceptionOrNull()).hasMessageThat().isEqualTo("A senha fornecida é muito fraca.")
    }

    @Test
    fun `signIn completes when Firebase authenticates user`() = runTest {
        every {
            firebaseAuth.signInWithEmailAndPassword("user@domain.com", "Password1!")
        } returns Tasks.forResult(mockk<AuthResult>())

        repository.signIn("user@domain.com", "Password1!")

        verify {
            firebaseAuth.signInWithEmailAndPassword("user@domain.com", "Password1!")
        }
    }

    @Test
    fun `signIn maps invalid user error`() = runTest {
        every {
            firebaseAuth.signInWithEmailAndPassword(any(), any())
        } returns Tasks.forException(
            FirebaseAuthInvalidUserException("ERROR_USER_NOT_FOUND", "User not found")
        )

        val result = runCatching { repository.signIn("missing@domain.com", "Password1!") }

        assertThat(result.exceptionOrNull()).hasMessageThat()
            .isEqualTo("O usuário não foi encontrado ou está desativado.")
    }

    @Test
    fun `signIn maps invalid credentials error`() = runTest {
        every {
            firebaseAuth.signInWithEmailAndPassword(any(), any())
        } returns Tasks.forException(
            FirebaseAuthInvalidCredentialsException("ERROR_INVALID_CREDENTIAL", "Invalid")
        )

        val result = runCatching { repository.signIn("user@domain.com", "wrong") }

        assertThat(result.exceptionOrNull()).hasMessageThat()
            .isEqualTo("Credenciais inválidas. Verifique seu email e senha.")
    }

    @Test
    fun `signIn maps network error`() = runTest {
        every {
            firebaseAuth.signInWithEmailAndPassword(any(), any())
        } returns Tasks.forException(
            FirebaseAuthException("ERROR_NETWORK_REQUEST_FAILED", "Network")
        )

        val result = runCatching { repository.signIn("user@domain.com", "Password1!") }

        assertThat(result.exceptionOrNull()).hasMessageThat()
            .isEqualTo("Erro de rede. Verifique sua conexão com a internet.")
    }

    @Test
    fun `resetPassword sends reset email`() = runTest {
        every {
            firebaseAuth.sendPasswordResetEmail("user@domain.com")
        } returns Tasks.forResult<Void>(null)

        repository.resetPassword("user@domain.com")

        verify { firebaseAuth.sendPasswordResetEmail("user@domain.com") }
    }

    @Test
    fun `resetPassword maps invalid user error`() = runTest {
        every {
            firebaseAuth.sendPasswordResetEmail(any())
        } returns Tasks.forException(
            FirebaseAuthInvalidUserException("ERROR_USER_NOT_FOUND", "User not found")
        )

        val result = runCatching { repository.resetPassword("missing@domain.com") }

        assertThat(result.exceptionOrNull()).hasMessageThat()
            .isEqualTo("Nenhuma conta encontrada com este email.")
    }

    @Test
    fun `deleteAccount deletes current user when available`() = runTest {
        val user = mockk<FirebaseUser>()
        every { firebaseAuth.currentUser } returns user
        every { user.delete() } returns Tasks.forResult<Void>(null)

        repository.deleteAccount()

        verify { user.delete() }
    }

    @Test
    fun `logout signs out Firebase auth`() = runTest {
        repository.logout()

        verify { firebaseAuth.signOut() }
    }
}
