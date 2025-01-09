package com.fitness.elev8fit.presentation.navigation

sealed class Navdestination(val route:String) {
    object onboarding1 : Navdestination("onboarding1")
    object onboarding2 : Navdestination("onboarding2")
    object onboarding3 : Navdestination("onboarding3")
    object login :Navdestination("Login")
    object home:Navdestination("Home")
    object Signup :Navdestination("SignUp")
    object recipeentry:Navdestination("RecipeEntry")
    object Recipe :Navdestination("Recipe")
    object otp :Navdestination("Otp")
    object account:Navdestination("Account")
    object chatC:Navdestination("ChatC")
    object chat:Navdestination("Chat")
    object dispnav :Navdestination("disp")
}