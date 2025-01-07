package com.fitness.elev8fit.presentation.activity.login

import android.annotation.SuppressLint
import android.content.Context
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.fitness.elev8fit.data.repository.authfirebaseimpl
import com.fitness.elev8fit.data.state.LoginState
import com.fitness.elev8fit.presentation.intent.LoginIntent
import com.fitness.elev8fit.presentation.navigation.Navdestination
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val auth: FirebaseAuth,
    private val authrepo :authfirebaseimpl
):  ViewModel() {
    private val _state = MutableStateFlow(LoginState())
    val state: StateFlow<LoginState> = _state
  //  private val auth: FirebaseAuth = FirebaseAuth.getInstance()

    fun username(newusername: String) {
        _state.value = _state.value.copy(username = newusername)
    }

    fun password(newpassword: String) {
        _state.value = _state.value.copy(password = newpassword)
    }

    fun handleLoginIntent(intent: LoginIntent, context: Context, navController: NavController) {
        when (intent) {
            is LoginIntent.LoginDetails -> performLogin(intent.username, intent.password, navController)
            LoginIntent.GoogleLogin -> ExGoogleLogin()
            LoginIntent.FacebookLogin -> ExFacebook()
        }
    }

    @SuppressLint("SuspiciousIndentation")
    fun performLogin(username: String, password: String, navController: NavController) {
        viewModelScope.launch {
            _state.value = _state.value.copy(isLoading = true)
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

    fun ExGoogleLogin() {
        // Add Google login implementation
    }

    fun logout(){
        auth.signOut()
        Log.i("loggedout","Hi")
    }

    fun clearSuccessMessage() {
        _state.value = _state.value.copy(successMessage = null)
    }

    fun clearErrorMessage() {
        _state.value = _state.value.copy(errorMessage = null)
    }
}
