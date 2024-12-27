
package com.fitness.elev8fit.presentation.activity.login


import android.annotation.SuppressLint
import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.fitness.elev8fit.data.login.LoginState
import com.fitness.elev8fit.presentation.intent.LoginIntent
import com.fitness.elev8fit.presentation.navigation.Navdestination
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

class LoginViewModel : ViewModel() {
    private val _state = MutableStateFlow(LoginState())
    val state: StateFlow<LoginState> = _state
    private val auth: FirebaseAuth = FirebaseAuth.getInstance()



    fun username(newusername: String) {
        _state.value = _state.value.copy(username = newusername)
    }

    fun password(newpassword: String) {
        _state.value = _state.value.copy(password = newpassword)
    }

    fun handleLoginIntent(intent: LoginIntent, context: Context, navController: NavController) {

        when (intent) {
            is LoginIntent.LoginDetails -> performLogin(
                intent.username,
                intent.password,
                navController
            )

            LoginIntent.GoogleLogin -> ExGoogleLogin()
            LoginIntent.FacebookLogin -> ExFacebook()
         //   LoginIntent.ApplePayLogin -> ExAppleLogin()

        }

    }


    @SuppressLint("SuspiciousIndentation")
    fun performLogin(username: String, password: String, navController: NavController) {
        viewModelScope.launch {
            _state.value = _state.value.copy(isLoading = true)
            delay(1000) // Simulate network delay

            try {
                val authResult = auth.signInWithEmailAndPassword(username, password)
                authResult.await()

                if (authResult.isSuccessful) {
                    _state.value = _state.value.copy(
                        isLoading = false,
                        successMessage = "Login successful!",
                        errorMessage = null
                    )

                    navController.navigate(Navdestination.home.toString())


                } else {
                    _state.value = _state.value.copy(
                        isLoading = false,
                        errorMessage = "Login failed: user not found",
                        successMessage = null,
                        username = "",
                        password = ""
                    )
                }
            } catch (e: Exception) {
                _state.value = _state.value.copy(
                    isLoading = false,
                    errorMessage = "${e.message}",
                    successMessage = null,
                    username = "",
                    password = ""
                )
            }
        }
    }

    private fun ExFacebook() {
        TODO("Not yet implemented")
    }

    fun ExGoogleLogin(){

    }

    fun clearSuccessMessage() {
        _state.value = _state.value.copy(successMessage = null)
    }

    fun clearErrorMessage() {
        _state.value = _state.value.copy(errorMessage = null)
    }



}
