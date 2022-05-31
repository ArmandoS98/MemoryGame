package com.techun.memorygame.domain.repository

import com.techun.memorygame.domain.model.GameModel
import com.techun.memorygame.utils.DataState
import kotlinx.coroutines.flow.Flow

interface RecentlyRepository {
    suspend fun saveLastGame(game: GameModel): Flow<DataState<Boolean>>
    suspend fun getAllRecentlyGames(): Flow<DataState<List<GameModel>>>
}