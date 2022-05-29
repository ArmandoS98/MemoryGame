package com.techun.memorygame.data

import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.SetOptions
import com.techun.memorygame.di.FirebaseModule
import com.techun.memorygame.domain.model.GameModel
import com.techun.memorygame.domain.repository.GamesRepository
import com.techun.memorygame.utils.DataStates
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await
import java.lang.Exception
import javax.inject.Inject

class GamesRepositoryImpl @Inject constructor(@FirebaseModule.GamesCollection private val gamesCollection: CollectionReference) : GamesRepository {
    override suspend fun saveGame(game: GameModel): Flow<DataStates<Boolean>> = flow {
        emit(DataStates.Loading)
        try {
            var uploadSuccessful = false
            gamesCollection.document(game.id).set(game, SetOptions.merge())
                .addOnSuccessListener {
                    uploadSuccessful = true
                }
                .addOnFailureListener {
                    uploadSuccessful = false
                }.await()
            emit(DataStates.Success(uploadSuccessful))
            emit(DataStates.Finished)
        } catch (e: Exception) {
            emit(DataStates.Error(e))
            emit(DataStates.Finished)
        }
    }

    override suspend fun getAllGames(): Flow<DataStates<List<GameModel>>> = flow {
        emit(DataStates.Loading)
        try {
            val games = gamesCollection.get().await().toObjects(GameModel::class.java)
            emit(DataStates.Success(games))
            emit(DataStates.Finished)
        } catch (e: Exception) {
            emit(DataStates.Error(e))
            emit(DataStates.Finished)
        }
    }
}