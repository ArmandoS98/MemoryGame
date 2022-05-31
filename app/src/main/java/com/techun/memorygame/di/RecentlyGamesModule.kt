package com.techun.memorygame.di

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.CollectionReference
import com.techun.memorygame.data.RecentlyRepositoryImpl
import com.techun.memorygame.domain.repository.RecentlyRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RecentlyGamesModule {
    @Provides
    @Singleton
    fun provideRecentlyGamesRepository(
        @FirebaseModule.RecentlyGamesCollection recentlyGamesCollection: CollectionReference,
        auth: FirebaseAuth
    ): RecentlyRepository =
        RecentlyRepositoryImpl(recentlyGamesCollection, auth)
}