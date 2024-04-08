package com.digi.fireapp.ui.screens.upload

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyHorizontalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Done
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.FilledIconButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest

@Composable
fun DocScreen(
    state: DocState = DocState(),
    onEvent: (DocEvent) -> Unit,
    onBack: () -> Unit,
) {
    Scaffold(bottomBar = { UploadBar(state, onEvent) }) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = "Upload Documents", style = MaterialTheme.typography.titleLarge)
            DocumentCollection(state = state)
            Divider()
            UploadViewSection(state = state, onEvent = onEvent)
        }

    }
}

@Composable
fun UploadViewSection(
    state: DocState, onEvent: (DocEvent) -> Unit,
) {
    state.selectedImage?.let { image ->
        //Use Coil to display the selected image
        val painter = rememberAsyncImagePainter(
            ImageRequest.Builder(LocalContext.current).data(data = image).build()
        )

        Box {
            Image(
                painter = painter,
                contentDescription = null,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(400.dp)
                    .padding(16.dp)
                    .clip(shape = MaterialTheme.shapes.extraLarge),
                contentScale = ContentScale.Crop
            )
            FilledIconButton(
                onClick = {
                    onEvent(DocEvent.UploadImage(image))
                },
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .padding(24.dp),
                enabled = state.uploadStatus == UploadStatus.IDLE
            ) {
                Icon(
                    imageVector = Icons.Default.Done,
                    contentDescription = "confirm upload"
                )
            }
            if (state.uploadStatus == UploadStatus.UPLOADING) {
                CircularProgressIndicator(
                    modifier = Modifier
                        .align(Alignment.Center)
                        .size(100.dp),
                    strokeWidth = 10.dp,
                    trackColor = MaterialTheme.colorScheme.secondaryContainer
                )
                Text(
                    text = if (state.progress == 0) "Uploading" else "${state.progress}%",
                    style = if (state.progress == 0) MaterialTheme.typography.bodyLarge else MaterialTheme.typography.titleLarge,
                    modifier = Modifier.align(Alignment.Center)
                )
            } else if (state.uploadStatus == UploadStatus.SUCCESS) {
                Text(
                    text = "Upload Successful", modifier = Modifier.align(Alignment.Center)
                )
            } else if (state.uploadStatus == UploadStatus.ERROR) {
                Text(
                    text = "Upload Failed", modifier = Modifier.align(Alignment.Center)
                )
            }
        }
    }
}

@Composable
fun DocumentCollection(
    state: DocState,
) {
    if (state.documentStatus == DocumentStatus.LOADING) {
        Box(modifier = Modifier.fillMaxWidth()) {
            CircularProgressIndicator(
                modifier = Modifier.align(Alignment.Center),
                strokeWidth = 10.dp,
                trackColor = MaterialTheme.colorScheme.secondaryContainer
            )
        }
    } else {
        LazyHorizontalGrid(
            rows = GridCells.Fixed(3),
            modifier = Modifier
                .fillMaxWidth()
                .height(400.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            contentPadding = PaddingValues(vertical = 16.dp),
        ) {

            items(state.documents) { doc ->
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
        }
    }
}

@Composable
fun UploadBar(state: DocState, onEvent: (DocEvent) -> Unit) {
    val launcher = rememberLauncherForActivityResult(ActivityResultContracts.PickVisualMedia()) {
        if (it != null) onEvent(DocEvent.SetImage(it))
    }
    BottomAppBar {
        Box(modifier = Modifier.weight(1f)) {
            ExtendedFloatingActionButton(
                onClick = { launcher.launch(PickVisualMediaRequest()) },
                icon = {
                    Icon(
                        imageVector = Icons.Default.KeyboardArrowRight,
                        contentDescription = "upload image"
                    )
                },
                text = { Text("Upload Image") },
                modifier = Modifier.align(Alignment.CenterEnd)
            )
        }
    }
}
