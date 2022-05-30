package com.techun.memorygame.domain.usecases.auth.signup

import com.techun.memorygame.domain.model.UserModel
import com.techun.memorygame.domain.repository.AuthRepository
import com.techun.memorygame.utils.DataState
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class SignUpUseCase @Inject constructor(
    private val loginRepository: AuthRepository
) {

    suspend operator fun invoke(user: UserModel, password: String): Flow<DataState<UserModel>> =
        loginRepository.signUp(user, password)
}