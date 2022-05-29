package com.techun.memorygame.domain.usecases.games

import com.techun.memorygame.domain.model.GameModel
import com.techun.memorygame.domain.repository.GamesRepository
import com.techun.memorygame.utils.DataStates
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class SaveGameUseCase @Inject constructor(private val gamesRepository: GamesRepository) {
    suspend operator fun invoke(game: GameModel): Flow<DataStates<Boolean>> =
        gamesRepository.saveGame(game)
}