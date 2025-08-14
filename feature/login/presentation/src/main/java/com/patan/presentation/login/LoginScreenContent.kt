package com.patan.feature.login.presentation.login

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.patan.presentation.login.LoginScreenContract


@Composable
fun LoginScreenContent(
    state: LoginScreenContract.LoginState,
    onEvent: (LoginScreenContract.LoginEvent) -> Unit
) {
    val context = LocalContext.current

    Box(Modifier.fillMaxSize()) {
        Column(
            Modifier
                .fillMaxWidth()
                .padding(24.dp)
                .align(Alignment.Center)
        ) {
            Text("Ravelry ile Giriş", style = MaterialTheme.typography.headlineMedium)

            Spacer(Modifier.height(16.dp))

            Button(
                modifier = Modifier.fillMaxWidth(),
                onClick = { onEvent(LoginScreenContract.LoginEvent.ClickRavelry) },
                enabled = !state.loading
            ) { Text("Ravelry ile Devam Et") }

            Spacer(Modifier.height(12.dp))

            Text(
                "Hesabın yok mu? Ravelry’de oluştur",
                modifier = Modifier.clickable {
                    val uri = Uri.parse("https://www.ravelry.com/account/login?show_sign_up=true")
                    context.startActivity(Intent(Intent.ACTION_VIEW, uri))
                    onEvent(LoginScreenContract.LoginEvent.ClickSignup)
                },
                textAlign = TextAlign.Center
            )

            if (state.loading) {
                Spacer(Modifier.height(16.dp))
                LinearProgressIndicator(Modifier.fillMaxWidth())
            }

            state.error?.let {
                Spacer(Modifier.height(8.dp))
                Text(it, color = MaterialTheme.colorScheme.error)
            }
        }
    }
}