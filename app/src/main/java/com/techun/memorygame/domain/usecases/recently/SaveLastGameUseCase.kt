package com.techun.memorygame.domain.usecases.recently

import com.techun.memorygame.domain.model.GameModel
import com.techun.memorygame.domain.repository.RecentlyRepository
import com.techun.memorygame.utils.DataState
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class SaveLastGameUseCase @Inject constructor(private val gamesRepository: RecentlyRepository) {
    suspend operator fun invoke(game: GameModel): Flow<DataState<Boolean>> =
        gamesRepository.saveLastGame(game)
}