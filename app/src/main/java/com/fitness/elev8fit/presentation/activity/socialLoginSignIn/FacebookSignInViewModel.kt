//package com.fitness.elev8fit.presentation.activity.socialLoginSignIn
//
//import android.content.Context
//import android.util.Log
//import android.widget.Toast
//import androidx.lifecycle.ViewModel
//import androidx.lifecycle.viewModelScope
//import androidx.navigation.NavController
//import com.facebook.AccessToken
//import com.facebook.FacebookException
//import com.facebook.login.LoginManager
//import com.facebook.login.LoginResult
//import com.fitness.elev8fit.domain.Firebase.FirebaseClass
//import com.fitness.elev8fit.domain.model.User
//import com.fitness.elev8fit.presentation.navigation.Navdestination
//import com.google.firebase.auth.FacebookAuthProvider
//import com.google.firebase.auth.FirebaseAuth
//import dagger.hilt.android.lifecycle.HiltViewModel
//import kotlinx.coroutines.launch
//import kotlinx.coroutines.tasks.await
//import javax.inject.Inject
//
//@HiltViewModel
//class FacebookSignInViewModel @Inject constructor(
//    private val auth: FirebaseAuth,
//    private val firebaseClass: FirebaseClass
//) : ViewModel() {
//
//    fun handleFacebookSignIn(
//        context: Context,
//        navController: NavController,
//        loginResult: LoginResult
//    ) {
//        viewModelScope.launch {
//            // Get Facebook Access Token
//            val token: AccessToken = loginResult.accessToken
//
//            // Create an auth credential using the Facebook token
//            val credential = FacebookAuthProvider.getCredential(token.token)
//
//            try {
//                // Sign in to Firebase using the credential
//                val authResult = auth.signInWithCredential(credential).await()
//
//                // Handle successful sign-in
//                val currentUser = authResult.user
//                Log.d("AuthDebug", "Facebook User: $currentUser")
//
//                if (currentUser != null) {
//                    val user = User(
//                        currentUser.uid,
//                        currentUser.displayName ?: "Unknown",
//                        currentUser.email ?: "Unknown",
//                        "",
//                        "",
//                        currentUser.photoUrl.toString()
//                    )
//
//                    // Register the user in your Firebase backend
//                    firebaseClass.facebookregister(this@FacebookSignInViewModel, user)
//
//                    Toast.makeText(
//                        context,
//                        "Facebook Sign-In successful!",
//                        Toast.LENGTH_LONG
//                    ).show()
//
//                    // Navigate to the home screen
//                    navController.navigate(Navdestination.home.toString())
//                }
//            } catch (e: Exception) {
//                // Handle sign-in error
//                Toast.makeText(
//                    context,
//                    "Facebook Sign-In failed: ${e.message}",
//                    Toast.LENGTH_LONG
//                ).show()
//                Log.d("Issue", "handleFacebookSignIn: ${e.message}")
//            }
//        }
//    }
//
//    fun handleFacebookSignInFailure(context: Context, exception: FacebookException) {
//        Toast.makeText(context, "Facebook Sign-In failed: ${exception.message}", Toast.LENGTH_LONG)
//            .show()
//        Log.d("FacebookSignIn", "Error: ${exception.message}")
//    }
//
//    fun logout(navController: NavController) {
//        // Log out of Firebase
//        auth.signOut()
//
//        // Log out of Facebook
//        LoginManager.getInstance().logOut()
//
//        navController.navigate(Navdestination.Signup.toString())
//    }
//}
