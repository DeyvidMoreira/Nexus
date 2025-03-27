package com.example.nexus.framework.service.remote.repository

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthException
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthInvalidUserException
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import com.google.firebase.auth.FirebaseAuthWeakPasswordException
import kotlinx.coroutines.tasks.await

class FirebaseAuthRepository (
    private val firebaseAuth: FirebaseAuth
) {

    suspend fun signUp(email: String, password: String) {
        try {
            firebaseAuth.createUserWithEmailAndPassword(email, password).await()
        } catch (e: FirebaseAuthUserCollisionException) {
            // Email já em uso
            throw Exception("O email já está em uso.")
        } catch (e: FirebaseAuthWeakPasswordException) {
            // Senha fraca
            throw Exception("A senha fornecida é muito fraca.")
        } catch (e: FirebaseAuthInvalidCredentialsException) {
            // Credenciais inválidas
            throw Exception("Credenciais inválidas.")
        } catch (e: FirebaseAuthException) {
            // Qualquer outro erro
            throw Exception("Erro desconhecido: ${e.message}")
        }

    }

    suspend fun signIn(email: String, password: String) {
        try {
            firebaseAuth.signInWithEmailAndPassword(email, password)
                .await()
        } catch (e: FirebaseAuthInvalidUserException) {
            // O usuário não existe ou foi desativado
            throw Exception("O usuário não foi encontrado ou está desativado.")
        } catch (e: FirebaseAuthInvalidCredentialsException) {
            // Credenciais inválidas (ex: senha errada)
            throw Exception("Credenciais inválidas. Verifique seu email e senha.")
        } catch (e: FirebaseAuthException) {
            // Para erros gerais, podemos verificar o código de erro para identificar o tipo de falha
            when (e.errorCode) {
                "ERROR_NETWORK_REQUEST_FAILED" -> {
                    // Erro de rede
                    throw Exception("Erro de rede. Verifique sua conexão com a internet.")
                }

                "ERROR_TOO_MANY_REQUESTS" -> {
                    // Muitas tentativas de login
                    throw Exception("Muitas tentativas de login. Tente novamente mais tarde.")
                }

                else -> {
                    // Qualquer outro erro genérico
                    throw Exception("Erro desconhecido ao tentar fazer login: ${e.message}")
                }
            }
        }
    }

    suspend fun resetPassword(email:String) {
        try {
            firebaseAuth.sendPasswordResetEmail(email).await()
        }catch (e: FirebaseAuthInvalidUserException) {
            throw Exception("Nenhuma conta encontrada com este email.")
        }catch (e: FirebaseAuthInvalidCredentialsException) {
            throw Exception("Email inválido. Verifique se o endereço de email está correto.")
        } catch (e: FirebaseAuthException) {
            when (e.errorCode) {
                "ERROR_NETWORK_REQUEST_FAILED" -> throw Exception("Erro de rede. Verifique sua conexão com a internet.")
                "ERROR_TOO_MANY_REQUESTS" -> throw Exception("Muitas tentativas de redefinição. Aguarde um momento e tente novamente.")
                else -> throw Exception("Erro desconhecido ao tentar redefinir a senha: ${e.message}")
            }
        }
    }

    suspend fun deleteAccount() {
        try {
            firebaseAuth.currentUser?.delete()?.await()
        } catch (e: FirebaseAuthException) {
            throw Exception("Erro ao deletar conta: ${e.message}")
        }
    }

    suspend fun logout() {
        try {
            firebaseAuth.signOut()
        }catch (e: FirebaseAuthException){
            throw Exception("Erro ao fazer logout: ${e.message}")

        }
    }

}