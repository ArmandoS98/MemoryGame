package com.techun.memorygame.domain.usecases.auth.login

import com.techun.memorygame.domain.repository.AuthRepository
import com.techun.memorygame.utils.DataState
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetUserDataUseCase @Inject constructor(private val loginRepository: AuthRepository) {
    suspend operator fun invoke(): Flow<DataState<Boolean>> = loginRepository.getUserData()
}