package com.techun.memorygame.utils

import android.app.Activity
import android.content.Intent
import android.text.TextUtils
import android.widget.ImageView
import android.widget.Toast
import com.bumptech.glide.Glide
import com.google.android.material.textfield.TextInputEditText
import com.techun.memorygame.R
import com.techun.memorygame.utils.Constants.VALUE_REQUIRED
import de.hdodenhof.circleimageview.CircleImageView

fun ImageView.loadByResource(resource: String) =
    Glide.with(this)
        .load(resource)
        .centerCrop()
        .placeholder(R.drawable.mgame)
        .error(R.drawable.mgame)
        .fallback(R.drawable.mgame)
        .into(this)

fun ImageView.loadByResource(resource: Int) =
    Glide.with(this)
        .load(resource)
        .centerInside()
        .placeholder(R.drawable.mgame)
        .error(R.drawable.mgame)
        .fallback(R.drawable.mgame)
        .into(this)

fun CircleImageView.loadByResource(resource: String) =
    Glide.with(this)
        .load(resource)
        .centerInside()
        .placeholder(R.drawable.mgame)
        .error(R.drawable.mgame)
        .fallback(R.drawable.mgame)
        .into(this)

inline fun <reified T : Activity> Activity.goToActivity(
    noinline init: Intent.() -> Unit = {},
    finish: Boolean = false
) {
    val intent = Intent(this, T::class.java)
    intent.init()
    startActivity(intent)
    if (finish)
        finish()
}


fun Activity.toast(text: String, length: Int = Toast.LENGTH_SHORT) {
    Toast.makeText(this, text, length).show()
}

fun Activity.isInputEmpty(editText: TextInputEditText, errorMessage: Boolean = false): Boolean {
    return if (TextUtils.isEmpty(editText.text.toString().trim { it <= ' ' })) {
        if (errorMessage) {
            editText.error = VALUE_REQUIRED
        }
        true
    } else {
        false
    }
}
