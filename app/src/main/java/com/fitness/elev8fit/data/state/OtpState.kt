package com.fitness.elev8fit.data.state

data class OtpState (
    val isresend :Boolean = false,
    val isLoading:Boolean=false,
)

data class otpverify(
    val isverifed:Boolean = false,
    val isLoading: Boolean = false
)