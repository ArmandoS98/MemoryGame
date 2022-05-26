package com.techun.memorygame.data

data class GameItem(
    var title: String? = null,
    var cover: String? = null,
    var about: String? = null,
    var cards: MutableList<CardItem>? = null
)