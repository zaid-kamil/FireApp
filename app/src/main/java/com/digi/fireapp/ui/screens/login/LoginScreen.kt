package com.digi.fireapp.ui.screens.login

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun LoginScreen(
    state: LoginState = LoginState(),
    onEvent: (LoginEvent) -> Unit = {},
    onNavigateToRegister: () -> Unit = {},
) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    Brush.verticalGradient(
                        colors = listOf(
                            MaterialTheme.colorScheme.onPrimary,
                            MaterialTheme.colorScheme.primaryContainer
                        )
                    )
                )
        )
        Column(
            verticalArrangement = Arrangement.spacedBy(8.dp), modifier = Modifier.width(280.dp)
        ) {
            Text(text = "Login to FireApp", style = MaterialTheme.typography.headlineMedium)
            AnimatedVisibility(visible = state.error.isNotBlank()) {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = MaterialTheme.shapes.extraSmall
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text(
                            text = state.error,
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.error,
                            modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp),
                        )
                        Spacer(modifier = Modifier.weight(1f))
                        IconButton(
                            onClick = { onEvent(LoginEvent.ClearError) },
                            modifier = Modifier.size(18.dp)
                        ) {
                            Icon(
                                Icons.Default.Close,
                                contentDescription = "close",
                            )
                        }
                        Spacer(modifier = Modifier.width(16.dp))
                    }
                }
            }
            OutlinedTextField(
                value = state.email,
                onValueChange = { onEvent(LoginEvent.SetEmail(it)) },
                placeholder = { Text("Email") }
            )
            OutlinedTextField(
                value = state.password,
                onValueChange = { onEvent(LoginEvent.SetPassword(it)) },
                placeholder = { Text("Password") }
            )
            ElevatedButton(
                onClick = { onEvent(LoginEvent.OnLogin) },
                modifier = Modifier
                    .align(Alignment.Start)
                    .width(120.dp),
                shape = MaterialTheme.shapes.extraSmall,
                colors = ButtonDefaults.elevatedButtonColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    contentColor = MaterialTheme.colorScheme.onPrimary
                ),
                enabled = state.isLoading.not()
            ) {
                if (state.isLoading) {
                    Icon(Icons.Default.Refresh, contentDescription = "Loading")
                } else {
                    Text(text = "Login")
                }
            }

            Row {
                Text(text = "Don't have an Account?")
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "Register",
                    fontWeight = FontWeight.ExtraBold,
                    modifier = Modifier.clickable { onNavigateToRegister() }
                )
            }
        }

    }
}

@Preview(showBackground = true)
@Composable
private fun LoginScreenPreview() {
    LoginScreen()
}