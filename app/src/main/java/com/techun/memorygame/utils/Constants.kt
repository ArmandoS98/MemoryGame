package com.techun.memorygame.utils

object Constants {
    const val GAMES_COLLECTIONS = "Games"
    const val USERS_COLLECTIONS = "Users"
    const val GOOGLE_SIGN_IN = 1998


    const val INFO_NOT_SET = "info_not_set"
    const val USER_NOT_LOGGED = "user_not_logged"
    var USER_LOGGED_IN_ID = USER_NOT_LOGGED

    const val USER_NOT_EXISTS : String = "There is no user record corresponding to this identifier. The user may have been deleted."
    const val WRONG_PASSWORD : String = "The password is invalid or the user does not have a password."

    const val VALUE_REQUIRED = "Campo necesario"

    const val EMAIL_ALREADY_EXISTS : String = "com.google.firebase.auth.FirebaseAuthUserCollisionException: The email address is already in use by another account."

}