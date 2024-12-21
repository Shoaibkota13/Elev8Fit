package com.fitness.elev8fit.presentation.viewmodel


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fitness.elev8fit.data.login.LoginState
import com.fitness.elev8fit.presentation.intent.LoginIntent
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class LoginViewModel : ViewModel(){
    private val _state = MutableStateFlow(LoginState())
    val state:StateFlow<LoginState> = _state

    fun LoginIntent(intent:LoginIntent){

        when(intent){
            is LoginIntent.LoginDetails -> performLogin(intent.username,intent.password)
            LoginIntent.GoogleLogin->ExGoogleLogin()
            LoginIntent.FacebookLogin->ExFacebook()
            LoginIntent.ApplePayLogin->ExAppleLogin()

        }

    }




 fun performLogin(username: String, password: String) {
     viewModelScope.launch {
         _state.value = _state.value.copy(isLoading = true)
         delay(1000) // Simulate network delay
         if (username == "user" && password == "password") {
             _state.value = _state.value.copy(
                 isLoading = false,
                 successMessage = "Login successful!",
                 errorMessage =null
             )
         } else {
             _state.value = _state.value.copy(
                 isLoading = false,
                 errorMessage = "Invalid username or password.",
                 successMessage = null
             )
         }
     }
 }



    private fun ExAppleLogin() {
        TODO("Not yet implemented")
    }

    private fun ExFacebook() {
        TODO("Not yet implemented")
    }

    private fun ExGoogleLogin() {
        TODO("Not yet implemented")
    }


}