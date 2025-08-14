package com.patan.presentation.login

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun HomeContent(
    state: HomeContract.HomeState,
    onEvent: (HomeContract.HomeEvent) -> Unit
) {
    Column(Modifier.fillMaxSize().padding(24.dp)) {

        when {
            state.loading -> {
                LinearProgressIndicator(Modifier.fillMaxWidth())
                Spacer(Modifier.height(16.dp))
                Text("Yükleniyor...")
            }

            state.error != null -> {
                Text(state.error, color = MaterialTheme.colorScheme.error)
                Spacer(Modifier.height(12.dp))
                Button(onClick = { onEvent(HomeContract.HomeEvent.Refresh) }) { Text("Tekrar Dene") }
            }

            else -> {
                Text(
                    text = "Hoş geldin, ${state.username ?: "kullanıcı"}",
                    style = MaterialTheme.typography.headlineSmall
                )
                Spacer(Modifier.height(24.dp))
                Button(onClick = { onEvent(HomeContract.HomeEvent.Refresh) }) { Text("Yenile") }
            }
        }

        Spacer(Modifier.weight(1f))
        Button(
            onClick = { onEvent(HomeContract.HomeEvent.ClickLogout) },
            modifier = Modifier.fillMaxWidth()
        ) { Text("Çıkış Yap") }
    }
}