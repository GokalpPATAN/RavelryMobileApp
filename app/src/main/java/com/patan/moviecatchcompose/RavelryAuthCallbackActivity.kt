package com.patan.moviecatchcompose

import android.os.Bundle
import androidx.activity.ComponentActivity
import com.patan.data.auth.ravelry.RavelryAuthManager
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class RavelryAuthCallbackActivity : ComponentActivity() {

    @Inject lateinit var authManager: RavelryAuthManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val data = intent?.data
        // Teşhis için logla:
        android.util.Log.d("RavelryAuth", "redirect = ${data?.toString()}")

        if (data != null) authManager.onAuthRedirect(data)
        finish() // geri dön
    }
}