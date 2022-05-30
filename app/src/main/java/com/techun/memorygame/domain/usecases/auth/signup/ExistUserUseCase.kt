package com.techun.memorygame.domain.usecases.auth.signup

import com.techun.memorygame.domain.repository.AuthRepository
import com.techun.memorygame.utils.DataState
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ExistUserUseCase @Inject constructor(
    private val loginRepository: AuthRepository
) {
    suspend operator fun invoke(id: String): Flow<DataState<Boolean>> =
        loginRepository.userExist(id)
}