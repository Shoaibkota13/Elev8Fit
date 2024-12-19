package com.fitness.elev8fit.presentation.viewmodel

import androidx.lifecycle.ViewModel
import com.fitness.elev8fit.data.login.LoginState
import com.fitness.elev8fit.presentation.intent.LoginIntent
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class LoginViewModel : ViewModel(){
    private val _state = MutableStateFlow(LoginState())
    val state:StateFlow<LoginState> = _state
    
    
    fun LoginIntent(intent:LoginIntent){
        
        when(intent){
            is LoginIntent.EmailLogin->{
                ExEmailLogin(intent.username,intent.password)
            }
            LoginIntent.GoogleLogin->ExGoogleLogin()
            LoginIntent.FacebookLogin->ExFacebook()
            LoginIntent.ApplePayLogin->ExAppleLogin()
            is LoginIntent.LoginResult -> TODO()
        }
    }


    private fun ExEmailLogin(username: String, password: String) {

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