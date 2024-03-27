package com.digi.fireapp.ui.screens.home

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Divider
import androidx.compose.material3.DrawerState
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun HomeScreen(
    state: HomeState = HomeState(),
    onEvent: (HomeEvent) -> Unit = {},
    onLogout: () -> Unit = {},
    onNavigateToNotes: () -> Unit = {},
    onNavigateToDocuments: () -> Unit = {},
) {
    ModalNavigationDrawer(
        drawerContent = {
            ModalDrawerSheet {
                Text("FireApp Menu", modifier = Modifier.padding(16.dp))
                Divider()
                NavigationDrawerItem(
                    label = { Text(text = "Notes") },
                    selected = true,
                    onClick = { onNavigateToNotes() }
                )
                NavigationDrawerItem(label = { Text(text = "Uploads") },
                    selected = false,
                    onClick = { onNavigateToDocuments() }
                )
                Spacer(modifier = Modifier.weight(1f))
                NavigationDrawerItem(
                    label = { Text(text = "Logout") },
                    selected = false,
                    onClick = { onLogout() }
                )
            }
        },
        modifier = Modifier.fillMaxSize(),
        drawerState = DrawerState(DrawerValue.Open),
    ) {
        Box(modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)) {
            Column {
                Text("Welcome ${state.username}")
                Text("Home Screen")
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun HomeScreenPreview() {
    HomeScreen()
}