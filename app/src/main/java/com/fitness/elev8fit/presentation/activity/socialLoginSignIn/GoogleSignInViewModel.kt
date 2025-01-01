package com.fitness.elev8fit.presentation.activity.socialLoginSignIn

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.credentials.CredentialManager
import androidx.credentials.CustomCredential
import androidx.credentials.GetCredentialRequest
import androidx.credentials.exceptions.GetCredentialCancellationException
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.fitness.elev8fit.R
import com.fitness.elev8fit.domain.Firebase.FirebaseClass
import com.fitness.elev8fit.domain.model.User
import com.fitness.elev8fit.presentation.navigation.Navdestination
import com.google.android.libraries.identity.googleid.GetGoogleIdOption
import com.google.android.libraries.identity.googleid.GoogleIdTokenCredential
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

@HiltViewModel
class GoogleSignInViewModel @Inject constructor(
    private val auth :FirebaseAuth,
    private val firebaseClass: FirebaseClass
):ViewModel() {
//    fun handleGoogleSignIn(context: Context, navController: NavController) {
//        viewModelScope.launch {
//
//            // Collect the result of the Google Sign-In process
//            googleSignIn(context).collect { result ->
//                result.fold( // It allows you to specify actions for both success and failure cases of the operation, making it easy to manage the different outcomes.
//                    onSuccess = { authResult ->
//                        // Handle successful sign-in
//                        val currentUser = authResult.user
//                        Log.d("AuthDebug", "User: $currentUser")
//                        Log.d("AuthDebug", "DisplayName: ${currentUser?.displayName}")
//                        Log.d("AuthDebug", "Email: ${currentUser?.email}")
//                        Log.d("AuthDebug", "PhoneNumber: ${currentUser?.phoneNumber}")
//                        Log.d("AuthDebug", "PhotoURL: ${currentUser?.photoUrl}")
//                        if (currentUser != null) {
//                            val user = User(currentUser.uid,currentUser.displayName!!,currentUser.email,"","",currentUser.photoUrl.toString())
//                            // Show success message
//
//                            firebaseClass.googleregister(this@GoogleSignInViewModel,user)
//                            Toast.makeText(
//                                context,
//                                "Account created successfully!",
//                                Toast.LENGTH_LONG
//                            ).show()
//                            // Navigate to the home screen
//                            navController.navigate(Navdestination.home.toString())
//                        }
//                    },
//                    onFailure = { e ->
//                        // Handle sign-in error
//                        Toast.makeText(
//                            context,
//                            "Something went wrong: ${e.message}",
//                            Toast.LENGTH_LONG
//                        ).show()
//                        Log.d("Issue", "handleGoogleSignIn: ${e.message}")
//                    }
//                )
//            }
//        }
//    }

    fun handleGoogleSignIn(context: Context, navController: NavController) {
        viewModelScope.launch {

            // Check if the user is already signed in
            val currentUser = auth.currentUser
            if (currentUser != null) {
                // If the user is already signed in, show a different toast and navigate to the home screen
                Toast.makeText(
                    context,
                    "You are already signed in as ${currentUser.displayName}",
                    Toast.LENGTH_LONG
                ).show()
                navController.navigate(Navdestination.home.toString()) // Navigate to home or wherever you need

            }

            else{
                googleSignIn(context).collect { result ->
                    result.fold(
                        onSuccess = { authResult ->
                            // Handle successful sign-in
                            val currentUser = authResult.user
                            Log.d("AuthDebug", "User: $currentUser")
                            Log.d("AuthDebug", "DisplayName: ${currentUser?.displayName}")
                            Log.d("AuthDebug", "Email: ${currentUser?.email}")
                            Log.d("AuthDebug", "PhoneNumber: ${currentUser?.phoneNumber}")
                            Log.d("AuthDebug", "PhotoURL: ${currentUser?.photoUrl}")
                            if (currentUser != null) {
                                val user = User(
                                    currentUser.uid,
                                    currentUser.displayName ?: "Unknown",
                                    currentUser.email ?: "Unknown",
                                    "",
                                    "",
                                    currentUser.photoUrl.toString()
                                )
                                firebaseClass.googleregister(this@GoogleSignInViewModel, user)
                                Toast.makeText(context, "Account created successfully!", Toast.LENGTH_LONG).show()
                                navController.navigate(Navdestination.home.toString()) // Navigate to the home screen
                            }
                        },
                        onFailure = { e ->
                            // Handle sign-in error
                            Toast.makeText(context, "Something went wrong: ${e.message}", Toast.LENGTH_LONG).show()
                            Log.d("Issue", "handleGoogleSignIn: ${e.message}")
                        }
                    )
                }
            }

            // Collect the result of the Google Sign-In process if no user is signed in

        }
    }


    private suspend fun googleSignIn(context: Context): Flow<Result<AuthResult>> {


        // Return a Flow that emits the result of the Google Sign-In process
        return callbackFlow {
            try {

                // Initialize Credential Manager
                val credentialManager: CredentialManager = CredentialManager.create(context)


                // Set up Google ID option with necessary parameters
                val googleIdOption: GetGoogleIdOption = GetGoogleIdOption.Builder()
                    .setFilterByAuthorizedAccounts(false)  // To give the user the option to choose from any Google account on their device, not just the ones they've used with your app before.
                    .setServerClientId(context.getString(R.string.Web_client_id)) // This is required to identify the app on the backend server.
                    .setAutoSelectEnabled(true) // Which allows the user to be automatically signed in without additional user interaction if there is a single eligible account.
                    .build()

                // Create a credential request with the Google ID option
                val request: GetCredentialRequest = GetCredentialRequest.Builder()
                    .addCredentialOption(googleIdOption)
                    .build()

                // Get the credential result from the Credential Manager
                val result = credentialManager.getCredential(context, request)
                val credential = result.credential

                // Check if the received credential is a valid Google ID Token
                if (credential is CustomCredential && credential.type == GoogleIdTokenCredential.TYPE_GOOGLE_ID_TOKEN_CREDENTIAL) {
                    // Extract the Google ID Token credential
                    val googleIdTokenCredential = GoogleIdTokenCredential.createFrom(credential.data)
                    // Create an auth credential using the Google ID Token
                    val authCredential = GoogleAuthProvider.getCredential(googleIdTokenCredential.idToken, null)
                    // Sign in with Firebase using the auth credential
                    val authResult = auth.signInWithCredential(authCredential).await() // .await() -> allows the coroutine to wait for the result of the authentication operation before proceeding.
                    // Send the successful result
                    trySend(Result.success(authResult)) // Is used to send the result of the Firebase sign-in operation to the Flow's collectors.
                } else {
                    // Throw an exception if the credential type is invalid
                    throw RuntimeException("Received an invalid credential type.")
                }

            } catch (e: GetCredentialCancellationException) {
                // Handle sign-in cancellation
                trySend(Result.failure(Exception("Sign-in was canceled.")))
            } catch (e: Exception) {
                // Handle other exceptions
                trySend(Result.failure(e))
            }

            // When a collector starts collecting from the callbackFlow, the flow remains open and ready to emit values until the awaitClose block is reached or the flow is cancelled.
            // Even though the current block is empty, in other scenarios, you might use the awaitClose block to unregister listeners or release resources associated with the callback-based API.
            awaitClose { }
        }
    }

    fun onsucess(){

    }

    fun logout(navController: NavController){
        auth.signOut()
        navController.navigate(Navdestination.Signup.toString())
    }

}