
import android.os.Build
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.facebook.CallbackManager
import com.facebook.FacebookCallback
import com.facebook.FacebookException
import com.facebook.login.LoginManager
import com.facebook.login.LoginResult
import com.google.firebase.Firebase
import com.google.firebase.auth.FacebookAuthProvider
import com.google.firebase.auth.auth
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

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


@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun LoginFacebookButton(
    onAuthComplete: () -> Unit,
    onAuthError: (Exception) -> Unit,
    modifier: Modifier = Modifier,
) {
    val scope = rememberCoroutineScope()
    val loginManager = LoginManager.getInstance()
    val callbackManager = remember { CallbackManager.Factory.create() }
    val launcher = rememberLauncherForActivityResult(
        loginManager.createLogInActivityResultContract(callbackManager, null)
    ) {

    }

    DisposableEffect(Unit) {
        loginManager.registerCallback(callbackManager, object : FacebookCallback<LoginResult> {
            override fun onCancel() {
                // do nothing
            }

            override fun onError(error: FacebookException) {
                onAuthError(error)
            }

            override fun onSuccess(result: LoginResult) {
                scope.launch {
                    val token = result.accessToken.token
                    val credential = FacebookAuthProvider.getCredential(token)
                    val authResult = Firebase.auth.signInWithCredential(credential).await()
                    if (authResult.user != null) {
                        onAuthComplete()
                    } else {
                        onAuthError(IllegalStateException("Unable to sign in with Facebook"))
                    }
                }
            }
        })

        onDispose {
            loginManager.unregisterCallback(callbackManager)
        }
    }
    Button(
        onClick = {
            launcher.launch(listOf("email", "public_profile"))
        },
        modifier = modifier
            .fillMaxWidth()
            .background(Color.White)
            .border(
                width = 0.5.dp,
                color = Color.LightGray,
                shape = RoundedCornerShape(30)
            ),
        colors = ButtonDefaults.buttonColors(Color.White)
    ) {
        Row(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {

            Text(
                modifier = Modifier
                    .padding(start = 10.dp, top = 11.dp, bottom = 11.dp),
                text = "buttonText",
                color = Color.Black,

            )
        }
    }
}

