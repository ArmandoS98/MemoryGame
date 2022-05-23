package com.techun.memorygame.data.utils.extensions

import android.widget.ImageView
import com.bumptech.glide.Glide
import com.techun.memorygame.R

fun ImageView.loadByResource(resource: String) =
    Glide.with(this)
        .load(resource)
        .centerCrop()
        .placeholder(R.drawable.ic_launcher_foreground)
        .error(R.drawable.ic_launcher_foreground)
        .fallback(R.drawable.ic_launcher_foreground)
        .into(this)
