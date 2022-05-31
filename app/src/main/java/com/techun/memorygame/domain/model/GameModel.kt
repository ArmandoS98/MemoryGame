package com.techun.memorygame.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import java.util.*

@Parcelize
data class GameModel(
    var id: String? = null,
    val title: String? = null,
    val cover: String? = null,
    val about: String? = null,
    val cards: MutableList<CardModel>? = null
) : Parcelable