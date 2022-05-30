package com.techun.memorygame.data

import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.SetOptions
import com.techun.memorygame.di.FirebaseModule
import com.techun.memorygame.domain.model.GameModel
import com.techun.memorygame.domain.repository.GamesRepository
import com.techun.memorygame.utils.DataState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await
import java.lang.Exception
import javax.inject.Inject

class GamesRepositoryImpl @Inject constructor(@FirebaseModule.GamesCollection private val gamesCollection: CollectionReference) : GamesRepository {
    override suspend fun saveGame(game: GameModel): Flow<DataState<Boolean>> = flow {
        emit(DataState.Loading)
        try {
            var uploadSuccessful = false
            gamesCollection.document(game.id).set(game, SetOptions.merge())
                .addOnSuccessListener {
                    uploadSuccessful = true
                }
                .addOnFailureListener {
                    uploadSuccessful = false
                }.await()
            emit(DataState.Success(uploadSuccessful))
            emit(DataState.Finished)
        } catch (e: Exception) {
            emit(DataState.Error(e))
            emit(DataState.Finished)
        }
    }

    override suspend fun getAllGames(): Flow<DataState<List<GameModel>>> = flow {
        emit(DataState.Loading)
        try {
            val games = gamesCollection.get().await().toObjects(GameModel::class.java)
            emit(DataState.Success(games))
            emit(DataState.Finished)
        } catch (e: Exception) {
            emit(DataState.Error(e))
            emit(DataState.Finished)
        }
    }
}