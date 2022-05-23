package com.techun.memorygame.view

data class MemoryItem(
    var status: Boolean,
    val name: String,
    val urlImagen: String,
    val id: Int? = 0
)