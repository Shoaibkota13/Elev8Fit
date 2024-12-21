package com.fitness.elev8fit.data.login

data class SignUpState(
   val username :String ="",
   val password : String = "" ,
    val Confirmpassword : String = "",
    val phonenumber :String = "",
    val otp :String ="",
   val isLoading: Boolean = false,
   val issucess :String = "",
   val isSignedUp: Boolean = false,
   val errorMessage: String? = null
)
