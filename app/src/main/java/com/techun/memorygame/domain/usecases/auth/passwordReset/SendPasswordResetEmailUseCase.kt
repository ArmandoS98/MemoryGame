package com.techun.memorygame.domain.usecases.auth.passwordReset

import com.techun.memorygame.domain.repository.AuthRepository
import com.techun.memorygame.utils.DataState
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class SendPasswordResetEmailUseCase @Inject constructor(private val repository: AuthRepository) {
    suspend operator fun invoke(email: String): Flow<DataState<Boolean>> =
        repository.verifyPasswordReset(email)
}