package com.fitness.elev8fit.presentation.activity.Biometric

enum class biometricstatus(val id:Int) {
    Ready(1),
    Not_Available(-1),
    temp_not_available(-2),
    available_but_not_invoked(-3)

}