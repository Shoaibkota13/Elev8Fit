package com.fitness.elev8fit.presentation.activity.socialLoginSignIn

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.credentials.CredentialManager
import androidx.credentials.CustomCredential
import androidx.credentials.GetCredentialRequest
import androidx.credentials.exceptions.GetCredentialCancellationException
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.fitness.elev8fit.R
import com.fitness.elev8fit.data.constant.DataStoreManager
import com.fitness.elev8fit.data.repository.authfirebaseimpl
import com.fitness.elev8fit.domain.model.User
import com.fitness.elev8fit.presentation.navigation.Navdestination
//import com.fitness.elev8fit.service.FCMService
import com.google.android.libraries.identity.googleid.GetGoogleIdOption
import com.google.android.libraries.identity.googleid.GoogleIdTokenCredential
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.firestore.FirebaseFirestore
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

@Suppress("DEPRECATION")
@HiltViewModel
class GoogleSignInViewModel @Inject constructor(
    private val auth: FirebaseAuth,
    private val authrepo: authfirebaseimpl,
    private val firestore: FirebaseFirestore
) : ViewModel() {
    val user = MutableLiveData<User>(null)

    fun handleGoogleSignIn(context: Context, navController: NavController) {
        viewModelScope.launch {
            // Check if the user is already signed in
            val currentUser = auth.currentUser
            if (currentUser != null) {
                Toast.makeText(
                    context,
                    "You are already signed in as ${currentUser.displayName}",
                    Toast.LENGTH_LONG
                ).show()
                navController.navigate(Navdestination.home.toString())
            } else {
                googleSignIn(context).collect { result ->
                    result.fold(
                        onSuccess = { authResult ->
                            DataStoreManager.saveAuthState(context, true)
                            val currentUser = authResult.user

                            Log.d("AuthDebug", "User: $currentUser")
                            Log.d("AuthDebug", "DisplayName: ${currentUser?.displayName}")
                            Log.d("AuthDebug", "Email: ${currentUser?.email}")
                            Log.d("AuthDebug", "PhoneNumber: ${currentUser?.phoneNumber}")
                            Log.d("AuthDebug", "PhotoURL: ${currentUser?.photoUrl}")
                            if (currentUser != null) {
                                val userEmail = currentUser.email ?: "Unknown"
                                firestore.collection("Users")
                                    .whereEqualTo("email", userEmail)
                                    .get()
                                    .addOnSuccessListener { document ->
                                        if (!document.isEmpty) {

                                            Toast.makeText(
                                                context,
                                                "Welcome back",
                                                Toast.LENGTH_SHORT
                                            ).show()
                                        //    FCMService.getFCMToken()
                                            navController.navigate(Navdestination.home.toString())
                                        } else {

                                            val users = User(
                                                currentUser.uid,
                                                currentUser.displayName ?: "Unknown",
                                                currentUser.email ?: "Unknown",
                                                "",
                                                "",
                                                currentUser.photoUrl.toString(),
                                                ""
                                            )


                                            viewModelScope.launch {
                                            //    FCMService.getFCMToken()

                                                authrepo.registergoogle(
                                                    this@GoogleSignInViewModel, users)

                                                Toast.makeText(
                                                    context,
                                                    "Account created successfully!",
                                                    Toast.LENGTH_LONG
                                                ).show()


                                                navController.navigate(Navdestination.home.toString())
                                            }
                                        }
                                    }
                                    .addOnFailureListener { e ->
                                        Toast.makeText(
                                            context,
                                            "Something went wrong: ${e.message}",
                                            Toast.LENGTH_LONG
                                        ).show()
                                        Log.d("Issue", "Firestore error: ${e.message}")
                                    }
                            }
                        },
                        onFailure = { e ->
                            Toast.makeText(
                                context,
                                "Something went wrong: ${e.message}",
                                Toast.LENGTH_LONG
                            ).show()
                            Log.d("Issue", "Sign-in error: ${e.message}")
                        }
                    )
                }
            }
        }
    }

    private suspend fun googleSignIn(context: Context): Flow<Result<AuthResult>> {
        return callbackFlow {
            try {
                val credentialManager: CredentialManager = CredentialManager.create(context)
                val googleIdOption: GetGoogleIdOption = GetGoogleIdOption.Builder()
                    .setFilterByAuthorizedAccounts(false)
                    .setServerClientId(context.getString(R.string.Web_client_id))
                    .setAutoSelectEnabled(true)
                    .build()

                val request: GetCredentialRequest = GetCredentialRequest.Builder()
                    .addCredentialOption(googleIdOption)
                    .build()

                val result = credentialManager.getCredential(context, request)
                val credential = result.credential

                if (credential is CustomCredential && credential.type == GoogleIdTokenCredential.TYPE_GOOGLE_ID_TOKEN_CREDENTIAL) {
                    val googleIdTokenCredential = GoogleIdTokenCredential.createFrom(credential.data)
                    val authCredential = GoogleAuthProvider.getCredential(googleIdTokenCredential.idToken, null)
                    val authResult = auth.signInWithCredential(authCredential).await()
                    trySend(Result.success(authResult))
                } else {
                    throw RuntimeException("Received an invalid credential type.")
                }
            } catch (e: GetCredentialCancellationException) {
                trySend(Result.failure(Exception("Sign-in was canceled.")))
            } catch (e: Exception) {
                trySend(Result.failure(e))
            }
            awaitClose { }
        }
    }



    fun onsucess(){

    }
}

