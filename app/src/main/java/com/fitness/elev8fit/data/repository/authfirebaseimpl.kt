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
    private val auth: FirebaseAuth,
    private val firestore: FirebaseFirestore
) : authoperations {

    companion object {
        const val COACH_ID = "oriw3fgPs4NqpN5BxfDb58Uq6532" // Hardcoded coach ID
    }

    override suspend fun registerUser(signinactivity: SignUpViewModel, userInfo: User) {
        firestore.collection(Constants.user)
            .document(getcurrentuser())
            .set(userInfo, SetOptions.merge())
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
            .set(userInfo, SetOptions.merge())
            .addOnSuccessListener {
                googleSignInViewModel.onsucess()
            }
    }

    override suspend fun getcurrentuser(): String {
        return auth.currentUser?.uid ?: throw IllegalStateException("No User Found")
    }

    override suspend fun fetchCurrentUser(onUserFetched: (User?) -> Unit) {
        val userId = getcurrentuser()

        firestore.collection("Users")
            .document(userId)
            .addSnapshotListener { snapshot, e ->
                if (e != null) {
                    Log.e("Firestore", "Error fetching user data: ${e.message}")
                    onUserFetched(null)
                    return@addSnapshotListener
                }

                if (snapshot != null && snapshot.exists()) {
                    val currentUserData = snapshot.toObject(User::class.java)
                    Log.d("Firestore", "User data fetched successfully: $currentUserData")
                    onUserFetched(currentUserData)
                } else {
                    Log.d("Firestore", "No data found for user with ID: $userId")
                    onUserFetched(null)
                }
            }
    }

    override suspend fun RecipeDb(Recipe: RecipeViewModel, recipeInfo: Recipe) {
        firestore.collection(Constants.Recipe)
            .add(recipeInfo)
            .addOnSuccessListener {
                Recipe.saved()
            }
            .addOnFailureListener { e ->
                Log.e("Firestore", "Error saving recipe: ${e.message}")
            }
    }

    override suspend fun updateSingleField(field: String, value: String) {
        val currentUser = auth.currentUser
        currentUser?.let {
            val userId = it.uid
            firestore.collection("Users").document(userId)
                .update(field, value)
                .addOnSuccessListener {
                    // Optionally, fetch the updated data after update
                }
                .addOnFailureListener {
                    Log.w("ProfileViewModel", "Error updating document")
                }
        }
    }

    override suspend fun fetchExercises(offset: Int, limit: Int) {
        // Implementation for fetching exercises (if needed)
    }


    override suspend fun onNewToken(token: String) {
        // Store the new FCM token in Firestore
        val currentUser = FirebaseAuth.getInstance().currentUser ?: return
        FirebaseFirestore.getInstance()
            .collection("Users")
            .document(currentUser.uid)
            .update("fcmtoken", token)
            .addOnFailureListener { e ->
                Log.e("FCMService", "Failed to update FCM token", e)
            }
    }




}