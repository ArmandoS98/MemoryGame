package com.techun.memorygame.data

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.SetOptions
import com.techun.memorygame.di.FirebaseModule
import com.techun.memorygame.domain.model.GameModel
import com.techun.memorygame.domain.repository.RecentlyRepository
import com.techun.memorygame.utils.Constants.GAMES_COLLECTIONS
import com.techun.memorygame.utils.Constants.USER_LOGGED_IN_ID
import com.techun.memorygame.utils.DataState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class RecentlyRepositoryImpl @Inject constructor
    (
    @FirebaseModule.RecentlyGamesCollection
    private val recentlyGamesCollection: CollectionReference,
    private val auth : FirebaseAuth
) : RecentlyRepository {
    override suspend fun saveLastGame(game: GameModel): Flow<DataState<Boolean>> = flow {
        emit(DataState.Loading)
        try {
            var uploadSuccessful = false
            recentlyGamesCollection
                .document(auth.uid!!)
                .collection(GAMES_COLLECTIONS)
                .document(game.id!!)
                .set(game, SetOptions.merge())
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

    override suspend fun getAllRecentlyGames(): Flow<DataState<List<GameModel>>> = flow {
        emit(DataState.Loading)
        try {
            val games = recentlyGamesCollection
                .document(auth.uid!!)
                .collection(GAMES_COLLECTIONS)
                .get().await().toObjects(GameModel::class.java)
            emit(DataState.Success(games))
            emit(DataState.Finished)
        } catch (e: Exception) {
            emit(DataState.Error(e))
            emit(DataState.Finished)
        }
    }
}