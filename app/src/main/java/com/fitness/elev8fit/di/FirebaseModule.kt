package com.fitness.elev8fit.di

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object FirebaseModule {

    @Provides

    fun ProvideFireBaseAuth():FirebaseAuth = FirebaseAuth.getInstance()


    @Provides

    fun ProvideFirestore():FirebaseFirestore = FirebaseFirestore.getInstance()

}