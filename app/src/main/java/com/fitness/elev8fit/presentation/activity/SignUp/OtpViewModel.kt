package com.fitness.elev8fit.presentation.activity.SignUp

import android.app.Activity
import android.content.Context
import androidx.lifecycle.ViewModel
import com.google.firebase.FirebaseException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthOptions
import com.google.firebase.auth.PhoneAuthProvider
import java.util.concurrent.TimeUnit

class OtpViewModel:ViewModel() {
    private val auth = FirebaseAuth.getInstance()

    fun triggerOtp(activity: Activity, phoneNumber: String, onOtpSent: (String) -> Unit, onFailure: (String) -> Unit) {
        val formatedphone = "+91 $phoneNumber"
        val options = PhoneAuthOptions.newBuilder(auth)
            .setPhoneNumber(formatedphone)
            .setTimeout(60L, TimeUnit.SECONDS)
            .setActivity(activity) // Replace with your activity context
            .setCallbacks(object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
                override fun onVerificationCompleted(credential: PhoneAuthCredential) {
                    // Auto-retrieval or instant verification
                }

                override fun onVerificationFailed(e: FirebaseException) {
                    onFailure(e.message ?: "OTP verification failed")
                }

                override fun onCodeSent(verificationId: String, token: PhoneAuthProvider.ForceResendingToken) {
                    onOtpSent(verificationId)
                }
            })
            .build()
        PhoneAuthProvider.verifyPhoneNumber(options)
    }

    fun verifyOtp(context: Context, verificationId: String, otp: String, onComplete: (Boolean) -> Unit) {
        val credential = PhoneAuthProvider.getCredential(verificationId, otp)
        auth.signInWithCredential(credential)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    onComplete(true)
                } else {
                    onComplete(false)
                }
            }
    }
}
