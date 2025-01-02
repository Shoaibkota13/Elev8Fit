package com.fitness.elev8fit.data.repository

import com.fitness.elev8fit.data.constant.Constants
import com.fitness.elev8fit.domain.interfaces.authoperations
import com.fitness.elev8fit.domain.model.User
import com.fitness.elev8fit.presentation.activity.SignUp.SignUpViewModel
import com.fitness.elev8fit.presentation.activity.socialLoginSignIn.GoogleSignInViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions
import javax.inject.Inject

class authfirebaseimpl @Inject constructor(
    private val auth : FirebaseAuth,
    private val firestore: FirebaseFirestore

):authoperations {
    override suspend fun registerUser(signinactivity: SignUpViewModel, userInfo: User) {
        firestore.collection(Constants.user)
            .document(getcurrentuser()).set(userInfo, SetOptions.merge())
            .addOnSuccessListener {
                signinactivity.onregistersucess()
            }
    }

    override suspend fun registergoogle(
        googleSignInViewModel: GoogleSignInViewModel,
        userInfo: User
    ) {
        firestore.collection(Constants.user)
            .document(getcurrentuser())
            .set(userInfo,SetOptions.merge())
            .addOnSuccessListener{
                googleSignInViewModel.onsucess()
            }
    }

    override suspend fun getcurrentuser():String {
        return auth.currentUser?.uid ?: throw IllegalStateException("No User Found")

        TODO("Not yet implemented")
    }
}