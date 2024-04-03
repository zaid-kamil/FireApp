package com.digi.fireapp.data

// Firebase models must have default values for all properties
data class CNote(
    val uid: String = "",
    val content: String = "",
    val title: String = "",
    val importance: Int = 0,
)
