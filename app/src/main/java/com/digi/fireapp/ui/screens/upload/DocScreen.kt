package com.digi.fireapp.ui.screens.upload

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Done
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.FilledIconButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest

@Composable
fun DocScreen(
    state: DocState = DocState(),
    onEvent: (DocEvent) -> Unit,
    onBack: () -> Unit,
) {
    val result = remember { mutableStateOf<Uri?>(null) }
    Scaffold(
        bottomBar = { UploadBar(state, onEvent, result) }
    ) { paddingValues ->
        Column(
            modifier = Modifier.padding(paddingValues),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            result.value?.let { image ->
                //Use Coil to display the selected image
                val painter = rememberAsyncImagePainter(
                    ImageRequest
                        .Builder(LocalContext.current)
                        .data(data = image)
                        .build()
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
                    } else if (state.uploadStatus == UploadStatus.SUCCESS) {
                        Text(
                            text = "Upload Successful",
                            modifier = Modifier.align(Alignment.Center)
                        )
                    } else if (state.uploadStatus == UploadStatus.ERROR) {
                        Text(
                            text = "Upload Failed",
                            modifier = Modifier.align(Alignment.Center)
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun UploadBar(state: DocState, onEvent: (DocEvent) -> Unit, result: MutableState<Uri?>) {
    val launcher = rememberLauncherForActivityResult(ActivityResultContracts.PickVisualMedia()) {
        result.value = it
    }
    BottomAppBar {
        ExtendedFloatingActionButton(onClick = {
            launcher.launch(
                PickVisualMediaRequest(mediaType = ActivityResultContracts.PickVisualMedia.ImageOnly)
            )
        }) {
            Icon(imageVector = Icons.Default.KeyboardArrowRight, contentDescription = "Browse")
            Text(text = "Select Image")
        }
    }
}
