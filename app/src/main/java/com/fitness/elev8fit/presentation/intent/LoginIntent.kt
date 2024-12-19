package com.fitness.elev8fit.presentation.intent

sealed class LoginIntent {
data class EmailLogin(val username:String,val password:String):LoginIntent()
object GoogleLogin:LoginIntent()
object FacebookLogin:LoginIntent()
object ApplePayLogin:LoginIntent()

data class LoginResult(val success:Boolean,val method:String):LoginIntent()
}