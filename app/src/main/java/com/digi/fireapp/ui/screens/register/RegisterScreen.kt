package com.digi.fireapp.ui.screens.register

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun RegisterScreen(
    state: RegisterState = RegisterState(),
    onEvent: (RegisterEvent) -> Unit = {},
    onBack: () -> Unit = {},
) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Box(modifier = Modifier.fillMaxSize().background(MaterialTheme.colorScheme.secondaryContainer))
        Column(
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            IconButton(
                onClick = { onBack() },
                modifier = Modifier.background(
                    MaterialTheme.colorScheme.onTertiary,
                    MaterialTheme.shapes.small
                )
            ) {
                Icon(Icons.Default.ArrowBack, contentDescription = "Back")
            }
            Text(text = "Create free account", style = MaterialTheme.typography.headlineMedium)
            OutlinedTextField(
                value = state.email,
                onValueChange = { onEvent(RegisterEvent.SetEmail(it)) },
                placeholder = { Text("Email") },
                leadingIcon = { Icon(imageVector = Icons.Default.Email, contentDescription = null) }
            )
            OutlinedTextField(
                value = state.username,
                onValueChange = { onEvent(RegisterEvent.SetUsername(it)) },
                placeholder = { Text("Email") },
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Default.Person,
                        contentDescription = null
                    )
                }
            )
            OutlinedTextField(
                value = state.password,
                onValueChange = { onEvent(RegisterEvent.SetPassword(it)) },
                placeholder = { Text("Password") },
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Default.Lock,
                        contentDescription = null
                    )
                }
            )
            OutlinedTextField(
                value = state.confirmPassword,
                onValueChange = { onEvent(RegisterEvent.SetConfirmPassword(it)) },
                placeholder = { Text("Confirm Password") },
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Default.Lock,
                        contentDescription = null
                    )
                }
            )
            Row {
                Button(
                    onClick = { onEvent(RegisterEvent.OnSaveUser) },
                    shape = MaterialTheme.shapes.extraSmall,
                ) {
                    Text("Register")
                }
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
private fun RegisterScreenPreview() {
    RegisterScreen()
}