package com.techun.memorygame.domain.usecases.auth.logout

import com.techun.memorygame.domain.repository.AuthRepository
import com.techun.memorygame.utils.DataState
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class LogOutUseCase @Inject constructor(private val loginRepository: AuthRepository) {
    suspend operator fun invoke(): Flow<DataState<Boolean>> = loginRepository.logOut()
}