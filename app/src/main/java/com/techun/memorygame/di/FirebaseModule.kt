package com.techun.memorygame.di

import android.content.Context
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore
import com.techun.memorygame.R
import com.techun.memorygame.utils.Constants.GAMES_COLLECTIONS
import com.techun.memorygame.utils.Constants.RECENTLY_COLLECTIONS
import com.techun.memorygame.utils.Constants.USERS_COLLECTIONS
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Qualifier
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object FirebaseModule {

    @Provides
    @Singleton
    fun provideFirebaseAuth(): FirebaseAuth {
        return FirebaseAuth.getInstance()
    }

    @Provides
    @Singleton
    fun provideGso(@ApplicationContext context: Context) =
        GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(context.getString(R.string.default_web_client_id))
            .requestEmail()
            .build()

    @Provides
    @Singleton
    fun provideGoogleClient(@ApplicationContext context: Context, gso: GoogleSignInOptions) =
        GoogleSignIn.getClient(context, gso)

    @Singleton
    @Provides
    fun provideFirestore() = FirebaseFirestore.getInstance()

    @GamesCollection
    @Singleton
    @Provides
    fun provideGamesRef(db: FirebaseFirestore) = db.collection(GAMES_COLLECTIONS)

    @UsersCollection
    @Provides
    @Singleton
    fun provideUserCollection(db: FirebaseFirestore) = db.collection(USERS_COLLECTIONS)

    @RecentlyGamesCollection
    @Provides
    @Singleton
    fun provideRGCollection(db: FirebaseFirestore) = db.collection(RECENTLY_COLLECTIONS)

    @Qualifier
    @Retention(AnnotationRetention.BINARY)
    annotation class GamesCollection
    @Qualifier
    @Retention(AnnotationRetention.BINARY)
    annotation class UsersCollection
    @Qualifier
    @Retention(AnnotationRetention.BINARY)
    annotation class RecentlyGamesCollection
}