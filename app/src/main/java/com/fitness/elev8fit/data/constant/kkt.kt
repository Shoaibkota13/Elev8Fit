package com.fitness.elev8fit.data.constant

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import com.fitness.elev8fit.presentation.activity.Otp.OtpViewModel

@Composable
fun kk(otpViewModel: OtpViewModel){
 val   otpViewModel by otpViewModel.state.collectAsState()

    Log.d("SignUpScreen", "OTP Verified: ${otpViewModel.isverified}")

}