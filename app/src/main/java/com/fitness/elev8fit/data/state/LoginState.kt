package com.fitness.elev8fit.data.state

data class LoginState(
    var username :String="",
    var password :String ="",
    val isIdle: Boolean = false,
    val isLoading: Boolean = false,
    val successMessage: String? = null,
    val errorMessage: String? = null
)
