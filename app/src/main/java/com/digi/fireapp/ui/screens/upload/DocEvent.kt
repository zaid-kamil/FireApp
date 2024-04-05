package com.digi.fireapp.ui.screens.upload

import android.net.Uri

sealed class DocEvent {
    data class UploadImage(val uri: Uri) : DocEvent()
}