package com.example.domain.auth.usecase

import android.content.Context
import com.example.domain.auth.repository.GoogleIdTokenProvider
import com.patan.core.common.model.BaseError
import com.patan.core.common.model.RestResult
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class RequestGoogleIdTokenUseCase @Inject constructor(
        private val provider: GoogleIdTokenProvider
    ) {
        operator fun invoke(ctx: Context): Flow<RestResult<String>> = flow {
            emit(RestResult.Loading)
            try {
                val token = provider.getIdToken(ctx)
                if (token.isNullOrBlank()) {
                    emit(RestResult.Error(BaseError("Google girişi iptal edildi veya token alınamadı")))
                } else {
                    emit(RestResult.Success(token))
                }
            } catch (t: Throwable) {
                emit(RestResult.Error(BaseError(t.message ?: "Bilinmeyen hata")))
            }
        }
    }
