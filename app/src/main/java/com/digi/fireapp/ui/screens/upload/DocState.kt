package com.digi.fireapp.ui.screens.upload

enum class UploadStatus {
    IDLE, UPLOADING, SUCCESS, ERROR
}

data class DocState(
    val isLoading: Boolean = false,
    val error: String = "",
    val uploadStatus: UploadStatus = UploadStatus.IDLE,
    val progress: Int = 0,
)
