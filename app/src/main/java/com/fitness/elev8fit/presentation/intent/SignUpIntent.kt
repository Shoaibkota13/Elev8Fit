package com.fitness.elev8fit.presentation.intent

sealed class SignUpIntent {

    data class userdetails(val username:String,val password:String):SignUpIntent()
    data class confirmpass(val confirmpass:String):SignUpIntent()
//    data class password(val password: String) : SignUpIntent()
    data class phonenumber(val phonenumber:String):SignUpIntent()

    data class otp(val otp:String):SignUpIntent()
//    object VerifyOtpClicked : SignUpIntent()
    object SignInClicked :SignUpIntent()
}