package com.techun.memorygame.di

import com.google.firebase.firestore.CollectionReference
import com.techun.memorygame.data.GamesRepositoryImpl
import com.techun.memorygame.domain.repository.GamesRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object GamesModule {
    @Provides
    @Singleton
    fun provideGamesRepository(@FirebaseModule.GamesCollection gamesCollection: CollectionReference): GamesRepository =
        GamesRepositoryImpl(gamesCollection)
}