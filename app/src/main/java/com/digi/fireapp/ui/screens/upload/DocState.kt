package com.digi.fireapp.ui.screens.upload

import android.net.Uri
import com.digi.fireapp.data.CDoc

enum class UploadStatus {
    IDLE, UPLOADING, SUCCESS, ERROR
}

enum class DocumentStatus {
    LOADING, ERROR, SUCCESS
}

data class DocState(
    val isLoading: Boolean = false,
    val error: String = "",
    val uploadStatus: UploadStatus = UploadStatus.IDLE,
    val progress: Int = 0,
    val documents: List<CDoc> = emptyList(),
    val documentStatus: DocumentStatus = DocumentStatus.LOADING,
    val selectedImage : Uri? = null
)
