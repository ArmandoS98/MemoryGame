package com.techun.memorygame.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class CardModel(
    var title: String? = null,
    var urlImagen: String? = null,
    var id: Int? = null
) : Parcelable