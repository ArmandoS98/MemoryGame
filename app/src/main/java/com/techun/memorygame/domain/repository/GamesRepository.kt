package com.techun.memorygame.domain.repository

import com.techun.memorygame.domain.model.GameModel
import com.techun.memorygame.utils.DataStates
import kotlinx.coroutines.flow.Flow

interface GamesRepository {
    suspend fun saveGame(game: GameModel): Flow<DataStates<Boolean>>
    suspend fun getAllGames(): Flow<DataStates<List<GameModel>>>
}