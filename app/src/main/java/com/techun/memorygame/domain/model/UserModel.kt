package com.techun.memorygame.domain.model

import com.techun.memorygame.utils.Constants.IMAGE_USER_DEFAULT
import com.techun.memorygame.utils.Constants.INFO_NOT_SET
import com.techun.memorygame.utils.Constants.USER_NAME

data class UserModel(
    val id: String = INFO_NOT_SET,
    val email: String = INFO_NOT_SET,
    val imageUrl: String = IMAGE_USER_DEFAULT,
    val userName: String = USER_NAME
)
