package com.fitness.elev8fit.presentation.activity.SignUp

import android.app.Activity
import android.content.Context
import androidx.lifecycle.ViewModel
import com.fitness.elev8fit.data.login.OtpState
import com.google.firebase.FirebaseException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthOptions
import com.google.firebase.auth.PhoneAuthProvider
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import java.util.concurrent.TimeUnit

class OtpViewModel:ViewModel() {
    private val auth = FirebaseAuth.getInstance()
    private val _state = MutableStateFlow(OtpState())
    val state : StateFlow<OtpState> = _state

    fun triggerOtp(activity: Activity, phoneNumber: String, onOtpSent: (String) -> Unit, onFailure: (String) -> Unit) {
        _state.value = _state.value.copy(isLoading = true)
        val formatedphone = "+91 $phoneNumber"
        val options = PhoneAuthOptions.newBuilder(auth)
            .setPhoneNumber(formatedphone)
            .setTimeout(60L, TimeUnit.SECONDS)
            .setActivity(activity) // Replace with your activity context
            .setCallbacks(object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
                override fun onVerificationCompleted(credential: PhoneAuthCredential) {
                    _state.value = _state.value.copy(isLoading = false)
                    // Auto-retrieval or instant verification
                }

                override fun onVerificationFailed(e: FirebaseException) {
                    _state.value = _state.value.copy(isLoading = false)
                    onFailure(e.message ?: "OTP verification failed")
                }

                override fun onCodeSent(verificationId: String, token: PhoneAuthProvider.ForceResendingToken) {
                    _state.value = _state.value.copy(isLoading = false)
                    onOtpSent(verificationId)
                }
            })
            .build()
        PhoneAuthProvider.verifyPhoneNumber(options)
    }

    fun verifyOtp(context: Context, verificationId: String, otp: String, onComplete: (Boolean) -> Unit) {
        _state.value = _state.value.copy(isverified = true)
        _state.value = _state.value.copy(isLoading = true)
        val credential = PhoneAuthProvider.getCredential(verificationId, otp)
        auth.signInWithCredential(credential)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    _state.value = _state.value.copy(isLoading = false)
                    onComplete(true)
                } else {
                    _state.value = _state.value.copy(isLoading = false)
                    onComplete(false)
                }
            }
    }
}
