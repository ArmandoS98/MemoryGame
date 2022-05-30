package com.techun.memorygame.domain.usecases.auth.google

import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.techun.memorygame.domain.model.UserModel
import com.techun.memorygame.domain.repository.AuthRepository
import com.techun.memorygame.utils.DataState
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class LoginWithGoogleUseCase @Inject constructor(private val loginRepository: AuthRepository) {
    suspend operator fun invoke(acct: GoogleSignInAccount): Flow<DataState<UserModel>> =
        loginRepository.loginWithGoogle(acct)
}