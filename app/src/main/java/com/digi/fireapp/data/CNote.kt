package com.digi.fireapp.data

data class CNote(
    val uid: String = "",
    val content: String = "",
    val timestamp:Long = System.currentTimeMillis()
)
