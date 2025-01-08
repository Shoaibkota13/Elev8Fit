package com.fitness.elev8fit.presentation.intent

sealed class SignUpIntent {

    data class Signup(val email:String,val password:String,val phonenumber:String):SignUpIntent()
}


