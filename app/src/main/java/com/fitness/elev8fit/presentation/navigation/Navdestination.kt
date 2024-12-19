package com.fitness.elev8fit.presentation.navigation

sealed class Navdestination(val route:String) {
    object onboarding1 : Navdestination("onboarding1")
    object onboarding2 : Navdestination("onboarding2")
    object onboarding3 : Navdestination("onboarding3")
}