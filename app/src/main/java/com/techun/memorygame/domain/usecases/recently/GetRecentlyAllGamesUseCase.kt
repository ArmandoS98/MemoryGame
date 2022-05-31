package com.techun.memorygame.domain.usecases.recently

import com.techun.memorygame.domain.model.GameModel
import com.techun.memorygame.domain.repository.RecentlyRepository
import com.techun.memorygame.utils.DataState
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetRecentlyAllGamesUseCase @Inject constructor(private val gamesRepository: RecentlyRepository) {
    suspend operator fun invoke(): Flow<DataState<List<GameModel>>> =
        gamesRepository.getAllRecentlyGames()
}