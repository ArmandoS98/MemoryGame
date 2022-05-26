package com.techun.memorygame.data

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class CardItem(
    var title: String? = null,
    var urlImagen: String? = null,
    var id: Int? = null
) : Parcelable