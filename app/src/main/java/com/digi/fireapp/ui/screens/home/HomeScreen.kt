@file:OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterial3Api::class)

package com.digi.fireapp.ui.screens.home

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import kotlinx.coroutines.launch

@Composable
fun HomeScreen(
    state: HomeState = HomeState(),
    onEvent: (HomeEvent) -> Unit = {},
    onLogout: () -> Unit = {},
    onNavigateToNotes: () -> Unit = {},
    onNavigateToDocuments: () -> Unit = {},
) {
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()
    ModalNavigationDrawer(
        drawerContent = {
            ModalDrawerSheet {
                Text("FireApp Menu", modifier = Modifier.padding(16.dp))
                Text(text = "Welcome ${state.username}", modifier = Modifier.padding(16.dp))
                Divider()
                NavigationDrawerItem(label = { Text(text = "Notes") },
                    selected = true,
                    onClick = { onNavigateToNotes() })
                NavigationDrawerItem(label = { Text(text = "Uploads") },
                    selected = false,
                    onClick = { onNavigateToDocuments() })
                Spacer(modifier = Modifier.weight(1f))
                NavigationDrawerItem(label = { Text(text = "Logout") },
                    selected = false,
                    onClick = { onLogout() })
            }
        },
        modifier = Modifier.fillMaxSize(),
        drawerState = drawerState,
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            Column {
                Row(
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    IconButton(onClick = {
                        scope.launch { if (drawerState.isClosed) drawerState.open() else drawerState.close() }
                    }) {
                        Icon(imageVector = Icons.Default.Menu, contentDescription = "Menu")
                    }
                    Spacer(modifier = Modifier.width(16.dp))
                    Text("FireApp", style = MaterialTheme.typography.titleLarge)
                }

                Spacer(modifier = Modifier.height(24.dp))
                NoteDisplayArea(
                    state = state, onEvent = onEvent, onNavigateToNotes = onNavigateToNotes
                )
                Spacer(modifier = Modifier.height(24.dp))
                DocumentDisplayArea(
                    state = state, onNavigateToDocuments = onNavigateToDocuments
                )
            }
        }
    }
}

@Composable
fun NoteDisplayArea(
    state: HomeState,
    onEvent: (HomeEvent) -> Unit,
    onNavigateToNotes: () -> Unit,
) {
    if (state.noteListState == NoteListState.LOADING) {
        Text("Loading notes...")
        CircularProgressIndicator()
    } else {
        LazyRow(
            modifier = Modifier.height(200.dp), horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            items(state.notes) { note ->
                Card(onClick = { /*TODO*/ }, modifier = Modifier.height(200.dp)) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text(note.title, style = MaterialTheme.typography.headlineMedium)
                        Text(note.content)
                    }
                }
            }
            item {
                Card(
                    onClick = {
                        onNavigateToNotes()
                    }, modifier = Modifier.size(200.dp)
                ) {
                    Box(
                        contentAlignment = Alignment.Center, modifier = Modifier.fillMaxSize()
                    ) {
                        Icon(
                            imageVector = Icons.Default.AddCircle,
                            contentDescription = "add",
                            modifier = Modifier.size(72.dp)
                        )
                    }
                }
            }
        }
    }

}


@Composable
fun DocumentDisplayArea(
    state: HomeState,
    onNavigateToDocuments: () -> Unit,
) {
    if (state.docListState == DocumentListState.LOADING) {
        Text("Loading documents...")
    } else {
        LazyRow(
            modifier = Modifier.height(200.dp), horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            items(state.documentList) { doc ->
                Card(
                    border = BorderStroke(1.dp, MaterialTheme.colorScheme.primary),
                ) {
                    AsyncImage(
                        model = doc.url,
                        contentDescription = doc.name,
                        modifier = Modifier
                            .fillMaxHeight()
                            .width(150.dp)
                            .clip(MaterialTheme.shapes.medium),
                        contentScale = ContentScale.Crop,
                    )
                }
            }
            item {
                Card(
                    onClick = { onNavigateToDocuments() }, modifier = Modifier.size(200.dp)
                ) {
                    Box(
                        contentAlignment = Alignment.Center, modifier = Modifier.fillMaxSize()
                    ) {
                        Icon(
                            imageVector = Icons.Default.AddCircle,
                            contentDescription = "add",
                            modifier = Modifier.size(72.dp)
                        )
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun HomeScreenPreview() {
    HomeScreen()
}