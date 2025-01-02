package com.fitness.elev8fit.domain.interfaces

import com.fitness.elev8fit.domain.model.User
import com.fitness.elev8fit.presentation.activity.SignUp.SignUpViewModel
import com.fitness.elev8fit.presentation.activity.socialLoginSignIn.GoogleSignInViewModel

interface authoperations {

    suspend fun registerUser(signinactivity: SignUpViewModel, userInfo: User)

    suspend fun registergoogle(googleSignInViewModel: GoogleSignInViewModel,userInfo: User)

    suspend fun getcurrentuser():String
}