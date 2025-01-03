package com.fitness.elev8fit.di

import com.fitness.elev8fit.data.repository.authfirebaseimpl
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object FirebaseModule {
    @Provides
    @Singleton
    fun provideAuthFirebaseImpl(auth: FirebaseAuth, firestore: FirebaseFirestore): authfirebaseimpl {
        return authfirebaseimpl(auth, firestore)
    }


    @Provides

    fun ProvideFireBaseAuth():FirebaseAuth = FirebaseAuth.getInstance()


    @Provides

    fun ProvideFirestore():FirebaseFirestore = FirebaseFirestore.getInstance()

}