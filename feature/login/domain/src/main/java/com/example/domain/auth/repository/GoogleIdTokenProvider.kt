package com.example.domain.auth.repository

import android.content.Context

interface GoogleIdTokenProvider {
    suspend fun getIdToken(context: Context): String?
}