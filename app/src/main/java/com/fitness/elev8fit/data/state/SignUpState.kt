package com.fitness.elev8fit.data.state

data class SignUpState(
   var name:String="",
   var age:String="",
   var email :String ="",
   val password : String = "",
   val Confirmpassword : String = "",
   val phonenumber :String = "",
   val countrycode : String="",
   val otp :String ="",
   val isLoading: Boolean = false,
   val successMessage  :String? = null,
   val isSignedUp: Boolean = false,
   val errorMessage: String? = null
)