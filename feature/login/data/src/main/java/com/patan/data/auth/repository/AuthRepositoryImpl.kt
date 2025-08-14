package com.example.data.auth.repository

import android.util.Log
import com.example.domain.auth.repository.AuthRepository
import com.example.firebase.auth.FirebaseAuthProvider
import com.patan.core.common.model.BaseError
import com.patan.core.common.model.RestResult
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val authProvider: FirebaseAuthProvider
) : AuthRepository {

    override suspend fun loginWithEmail(
        email: String,
        password: String
    ): Flow<RestResult<Unit>> = flow {
        emit(RestResult.Loading)
        try {
            val authResult = authProvider.loginWithEmail(email, password).await()
            if (authResult != null) {
                emit(RestResult.Success(Unit))
            } else {
                emit(RestResult.Error(BaseError("Login succeeded but no user", statusCode = 200)))
            }
        } catch (e: Exception) {
            Log.e("AuthRepo", "Error during login", e)
            emit(RestResult.Error(BaseError(e.message.orEmpty())))
        }
    }

    override suspend fun loginWithGmail(
        idToken: String
    ): Flow<RestResult<Unit>> = flow {
        emit(RestResult.Loading)
        try {
            // 1) Firebase credential oluştur
            val authResult = authProvider.loginWithGoogle(idToken).await()

            // 2) Başarı kontrolü
            if (authResult?.user != null) {
                emit(RestResult.Success(Unit))
            } else {
                emit(RestResult.Error(BaseError("Google ile giriş başarılı fakat kullanıcı bulunamadı", statusCode = 200)))
            }
        } catch (e: Exception) {
            Log.e("AuthRepo", "Google login hata", e)
            emit(RestResult.Error(BaseError(e.message.orEmpty())))
        }
    }


    override suspend fun register(
        email: String,
        password: String
    ): Flow <RestResult<Unit>> = flow {
        emit(RestResult.Loading)
        try {
            val authResult = authProvider.register(email, password).await()
            if (authResult != null) {
                emit(RestResult.Success(Unit))
            } else {
                emit(RestResult.Error(BaseError("Registration is failed", statusCode = 200)))
            }
        } catch (e: Exception) {
            Log.e("AuthRepo", "Error during registration", e)
            emit(RestResult.Error(BaseError(e.message.orEmpty())))
        }
    }

    override suspend fun forgotPassword(email: String): Flow<RestResult<Unit>> {
        TODO("Not yet implemented")
    }

    override suspend fun logout() {
        try {
            authProvider.logout()
            // If logout needs to communicate success/failure via RestResult:
            // return RestResult.Success(Unit) // (if you change the return type)
        } catch (e: Exception) {
            Log.e("AuthRepositoryImpl", "Error during logout", e)
            // If you change logout to return RestResult<Unit>:
            // val baseError = BaseError(errorMessage = e.message ?: "Logout failed.")
            // return RestResult.Error(error = baseError)
        }
    }
}