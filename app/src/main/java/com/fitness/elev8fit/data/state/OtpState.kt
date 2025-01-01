package com.fitness.elev8fit.data.state

data class OtpState (
    val isverified: Boolean = false,
    val isresend :Boolean = false,
    val isLoading:Boolean=false,
)