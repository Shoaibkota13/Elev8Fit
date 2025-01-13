package com.fitness.elev8fit.domain.di

import android.content.Context
import com.fitness.elev8fit.data.repository.authfirebaseimpl
import com.fitness.elev8fit.utils.S3Uploader
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
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
    @Singleton
    fun provideS3Uploader(@ApplicationContext context: Context): S3Uploader {
        return S3Uploader(context)
    }


    @Provides

    fun ProvideFireBaseAuth():FirebaseAuth = FirebaseAuth.getInstance()


    @Provides

    fun ProvideFirestore():FirebaseFirestore = FirebaseFirestore.getInstance()

}