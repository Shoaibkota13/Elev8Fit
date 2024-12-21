package com.fitness.elev8fit.presentation.viewmodel

import android.app.Activity
import androidx.lifecycle.ViewModel
import com.fitness.elev8fit.data.login.SignUpState
import com.fitness.elev8fit.presentation.intent.SignUpIntent
import com.google.firebase.FirebaseException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthOptions
import com.google.firebase.auth.PhoneAuthProvider
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import java.util.concurrent.TimeUnit

class SignUpViewModel:ViewModel() {
//    private lateinit var auth: FirebaseAuth
//    init {
//        initalizeAuth()
//    }

//    private fun initalizeAuth() {
//        auth = Firebase.auth
//    }

    private val _state = MutableStateFlow(SignUpState())
    val state: StateFlow<SignUpState> = _state


    fun SignUpIntentHandler(intent: SignUpIntent) {

        when (intent) {
            is SignUpIntent.userdetails -> {
                usersignin(_state.value.username,_state.value.password)
                
            }
//            is SignUpIntent.password -> {
//                _state.value = _state.value.copy(password = intent.password)
//            }

            is SignUpIntent.confirmpass -> {

                _state.value = _state.value.copy(intent.confirmpass)

            }
            is SignUpIntent.otp -> {
                _state.value = _state.value.copy(intent.otp)

            }
            is SignUpIntent.phonenumber -> {
                _state.value = _state.value.copy(intent.phonenumber)

            }

            SignUpIntent.SignInClicked -> {
                if (_state.value.password == _state.value.Confirmpassword) {
                    _state.value = _state.value.copy(isLoading = true, errorMessage = null)
//         sendOtp(_state.value.phonenumber)
                } else {
                    _state.value = _state.value.copy(errorMessage = "Passwords do not match")
                }
            }

//            SignUpIntent.VerifyOtpClicked -> {
//                _state.value = _state.value.copy(isLoading = true)
////
//            }
        }
    }

    private fun usersignin(username: String, password: String) {

    }
}
