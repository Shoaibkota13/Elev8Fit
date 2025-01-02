package com.fitness.elev8fit.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.fitness.elev8fit.presentation.activity.Home.HomePage
import com.fitness.elev8fit.presentation.activity.Home.RecipeScreen
import com.fitness.elev8fit.presentation.activity.Otp.OTPVerificationScreen
import com.fitness.elev8fit.presentation.activity.Recipe.RecipeEntry
import com.fitness.elev8fit.presentation.activity.SignUp.SignUpScreen
import com.fitness.elev8fit.presentation.activity.login.LoginScreen
import com.fitness.elev8fit.presentation.activity.onboarding.Firstonboard
import com.fitness.elev8fit.presentation.activity.onboarding.thirdOnboarding
import secondOnboarding


@Composable
fun displaynav(
    navController: NavHostController,
    isAuthenticated: Boolean
){
    val startDestination = when {
        isAuthenticated -> {
            // User is logged in, show home
            Navdestination.home.toString()
        }
        else -> {
            // User is not logged in, show login
            Navdestination.onboarding1.toString()
        }
    }



    NavHost(navController = navController, startDestination = startDestination) {
        composable(Navdestination.onboarding1.toString()) {
            Firstonboard(navController = navController)
        }
        composable(Navdestination.onboarding2.toString()) {
            secondOnboarding(viewModel = hiltViewModel(), navController = navController)
        }
        composable(Navdestination.onboarding3.toString()) {
            thirdOnboarding(signUpViewModel = hiltViewModel(),imageview= hiltViewModel(),navController=navController)
        }
        composable(Navdestination.login.toString()){
            LoginScreen(loginview = hiltViewModel(), signUpViewModel = hiltViewModel(),navController=navController, otpViewModel = hiltViewModel(), googleSignInViewModel = hiltViewModel() )
        }
        composable(Navdestination.Signup.toString()){
            SignUpScreen(viewModel =hiltViewModel(),navController=navController, otpViewModel = hiltViewModel(), googleSignInViewModel = hiltViewModel())
        }
        composable(Navdestination.home.toString()){
            HomePage(navController=navController)
        }

        composable(Navdestination.recipeentry.toString()){
            RecipeEntry(recipemodel = hiltViewModel(),navController=navController )
        }
        composable(Navdestination.Recipe.toString()) {
            RecipeScreen(navController = navController)
        }
        composable(Navdestination.otp.toString()) {
            OTPVerificationScreen(hiltViewModel(), navController)
        }
    }
}
