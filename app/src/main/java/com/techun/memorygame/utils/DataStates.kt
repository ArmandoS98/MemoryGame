package com.techun.memorygame.utils

import java.lang.Exception

sealed class DataStates<out R> {
    data class Success<out T>(val data: T) : DataStates<T>()
    data class Error(val exception: Exception) : DataStates<Nothing>()
    object Loading : DataStates<Nothing>()
    object Finished : DataStates<Nothing>()
}