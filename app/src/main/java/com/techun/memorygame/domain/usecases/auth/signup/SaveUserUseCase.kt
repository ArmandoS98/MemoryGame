package com.techun.memorygame.domain.usecases.auth.signup

import com.techun.memorygame.domain.model.UserModel
import com.techun.memorygame.domain.repository.AuthRepository
import com.techun.memorygame.utils.DataState
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class SaveUserUseCase @Inject constructor(
    private val loginRepository: AuthRepository
) {

    suspend operator fun invoke(user: UserModel): Flow<DataState<Boolean>> =
        loginRepository.saveUser(user)
}