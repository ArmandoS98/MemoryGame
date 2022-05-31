package com.techun.memorygame.domain.repository

import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.techun.memorygame.domain.model.UserModel
import com.techun.memorygame.utils.DataState
import kotlinx.coroutines.flow.Flow

interface AuthRepository {
    suspend fun login(email: String, password: String): Flow<DataState<Boolean>>

    suspend fun loginWithGoogle(acct: GoogleSignInAccount): Flow<DataState<UserModel>>

    suspend fun signUp(user: UserModel, password: String): Flow<DataState<UserModel>>

    suspend fun logOut(): Flow<DataState<Boolean>>

    suspend fun getUserData(): Flow<DataState<Boolean>>

    suspend fun saveUser(user: UserModel): Flow<DataState<Boolean>>

    suspend fun userExist(documentId: String): Flow<DataState<Boolean>>

    suspend fun verifyPasswordReset(email:String): Flow<DataState<Boolean>>
}