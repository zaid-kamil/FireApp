package com.digi.fireapp.data

data class CDoc(
    val uid: String = "",
    val url: String = "",
    val name: String = "",
    val mimetype: String = "",
    val timestamp: Long = System.currentTimeMillis(),
)
