package com.fitness.elev8fit.presentation.activity.Otp

import android.app.Activity
import android.content.Context
import android.util.Log
import androidx.lifecycle.ViewModel
import com.fitness.elev8fit.data.state.OtpState
import com.google.firebase.FirebaseException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthOptions
import com.google.firebase.auth.PhoneAuthProvider
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import java.util.concurrent.TimeUnit
import javax.inject.Inject


@HiltViewModel
class OtpViewModel @Inject constructor(
    private val auth: FirebaseAuth
) : ViewModel() {
    private val _state = MutableStateFlow(OtpState())
    val state: StateFlow<OtpState> = _state

    fun triggerOtp(activity: Activity, phoneNumber: String, onOtpSent: (String) -> Unit, onFailure: (String) -> Unit) {
        _state.value = _state.value.copy(isLoading = true)
        val formattedPhone = "+91 $phoneNumber"
        val options = PhoneAuthOptions.newBuilder(auth)
            .setPhoneNumber(formattedPhone)
            .setTimeout(60L, TimeUnit.SECONDS)
            .setActivity(activity)
            .setCallbacks(object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
                override fun onVerificationCompleted(credential: PhoneAuthCredential) {
                    _state.value = _state.value.copy(isLoading = false)
                    Log.d("OTP", "onVerificationCompleted: Auto-retrieval or instant verification succeeded.")
                }

                override fun onVerificationFailed(e: FirebaseException) {
                    _state.value = _state.value.copy(isLoading = false)
                    Log.e("OTP", "onVerificationFailed: ${e.message}", e)
                    onFailure(e.message ?: "OTP verification failed")
                }

                override fun onCodeSent(verificationId: String, token: PhoneAuthProvider.ForceResendingToken) {
                    _state.value = _state.value.copy(isLoading = false)
                    Log.d("OTP", "onCodeSent: VerificationId received - $verificationId")
                    onOtpSent(verificationId)
                }
            })
            .build()
        PhoneAuthProvider.verifyPhoneNumber(options)
    }

    fun verifyOtp(context: Context, verificationId: String, otp: String, onComplete: (Boolean) -> Unit) {
        val credential = PhoneAuthProvider.getCredential(verificationId, otp)
        _state.value = _state.value.copy(isLoading = true)

        auth.signInWithCredential(credential)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    _state.value = _state.value.copy(isLoading = false, isverified = true)
                    Log.d("OTP", "verifyOtp: OTP verification successful.")
                    onComplete(true)
                } else {
                    _state.value = _state.value.copy(isLoading = false)
                    Log.e("OTP", "verifyOtp: OTP verification failed.", task.exception)
                    onComplete(false)
                }
            }
    }
}
