package com.techun.memorygame.domain.repository

import com.techun.memorygame.domain.model.GameModel
import com.techun.memorygame.utils.DataState
import kotlinx.coroutines.flow.Flow

interface GamesRepository {
    suspend fun saveGame(game: GameModel): Flow<DataState<Boolean>>
    suspend fun getAllGames(): Flow<DataState<List<GameModel>>>
}