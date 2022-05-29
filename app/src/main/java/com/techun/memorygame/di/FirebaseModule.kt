package com.techun.memorygame.di

import com.google.firebase.firestore.FirebaseFirestore
import com.techun.memorygame.utils.Constants.GAMES_COLLECTIONS
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Qualifier
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object FirebaseModule {

    @Singleton
    @Provides
    fun provideFirestore() = FirebaseFirestore.getInstance()

    @GamesCollection
    @Singleton
    @Provides
    fun provideGamesRef(db: FirebaseFirestore) =  db.collection(GAMES_COLLECTIONS)

    @Qualifier
    @Retention(AnnotationRetention.BINARY)
    annotation class GamesCollection
}