package com.example.presentation.login

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import com.example.components.GoogleButton

@Composable
internal fun LoginScreenContent(
    state: LoginScreenContract.State,
    onEvent: (LoginScreenContract.Event) -> Unit
) {
    Column(
        modifier = Modifier.fillMaxSize().padding(16.dp),
        verticalArrangement = Arrangement.Center
    ) {
        TextField(
            value = state.email,
            onValueChange = { onEvent(LoginScreenContract.Event.OnEmailChanged(it)) },
            label = { Text("Email") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(8.dp))

        TextField(
            value = state.password,
            onValueChange = { onEvent(LoginScreenContract.Event.OnPasswordChanged(it)) },
            label = { Text("Password") },
            visualTransformation = PasswordVisualTransformation(),
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(text = "Forgot Password", textDecoration = TextDecoration.Underline, modifier = Modifier.clickable(
            onClick = {

            }
        ).align(Alignment.End))

        Spacer(modifier = Modifier.height(8.dp))


        if (state.isLoading) {
            CircularProgressIndicator(modifier = Modifier.align(Alignment.CenterHorizontally))
        } else {
            Button(
                onClick = { onEvent(LoginScreenContract.Event.OnLoginClicked) },
                modifier = Modifier.fillMaxWidth().height(36.dp),
                shape = RoundedCornerShape(50)
            ) {
                Text("Login")
            }

            Spacer(modifier = Modifier.height(8.dp))

            Button(
                onClick = { onEvent(LoginScreenContract.Event.OnRegisterClicked) },
                modifier = Modifier.fillMaxWidth().height(36.dp),
                shape = RoundedCornerShape(50)
            ) {
                Text("Sign Up")
            }

            Spacer(modifier = Modifier.height(8.dp))

            val ctx = LocalContext.current
            GoogleButton(
                modifier = Modifier.align(Alignment.CenterHorizontally)
            ) {
                onEvent(LoginScreenContract.Event.OnLoginWithGmailClicked(ctx))
            }

        }
        state.errorMessage?.let {
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = it, color = Color.Red)
        }
    }
}
