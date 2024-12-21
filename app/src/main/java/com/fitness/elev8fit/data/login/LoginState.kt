package com.fitness.elev8fit.data.login

data class LoginState(
    val isIdle: Boolean = false,
    val isLoading: Boolean = false,
    val successMessage: String? = null,
    val errorMessage: String? = null
)
