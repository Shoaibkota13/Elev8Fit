package com.fitness.elev8fit.data.repository

import android.util.Log
import com.fitness.elev8fit.data.constant.Constants
import com.fitness.elev8fit.domain.interfaces.authoperations
import com.fitness.elev8fit.domain.model.Recipe
import com.fitness.elev8fit.domain.model.User
import com.fitness.elev8fit.presentation.activity.Recipe.RecipeViewModel
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

  override suspend  fun fetchCurrentUser(onUserFetched: (User?) -> Unit) {
        val userId = getcurrentuser()

        firestore.collection("Users")
            .document(userId)
            .addSnapshotListener { snapshot, e ->
                if (e != null) {
                    Log.e("Firestore", "Error fetching user data: ${e.message}")
                    onUserFetched(null) // Return null in case of an error
                    return@addSnapshotListener
                }

                if (snapshot != null && snapshot.exists()) {
                    val currentUserData = snapshot.toObject(User::class.java)
                    Log.d("Firestore", "User data fetched successfully: $currentUserData")
                    onUserFetched(currentUserData) // Return the fetched user
                } else {
                    Log.d("Firestore", "No data found for user with ID: $userId")
                    onUserFetched(null) // Return null if no data found
                }
            }
    }

    override suspend fun RecipeDb(Recipe: RecipeViewModel, recipeInfo: Recipe) {
        firestore.collection(Constants.Recipe).document(getcurrentuser()).set(recipeInfo,
            SetOptions.merge()).addOnSuccessListener {
                    Recipe.saved()
        }
    }
}