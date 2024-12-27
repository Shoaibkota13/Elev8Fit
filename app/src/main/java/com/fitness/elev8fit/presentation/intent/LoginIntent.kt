package com.fitness.elev8fit.presentation.intent

sealed class LoginIntent {
data class LoginDetails(val username:String,val password:String):LoginIntent()

object GoogleLogin:LoginIntent()
object FacebookLogin:LoginIntent()

}

