package com.techun.memorygame.domain.model

import com.techun.memorygame.utils.Constants.INFO_NOT_SET

data class UserModel (
    val id: String = INFO_NOT_SET,
    val email: String = INFO_NOT_SET
)
