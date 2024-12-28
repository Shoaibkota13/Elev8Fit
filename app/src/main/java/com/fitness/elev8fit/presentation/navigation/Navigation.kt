package com.fitness.elev8fit.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.fitness.elev8fit.presentation.activity.Home.HomePage
import com.fitness.elev8fit.presentation.activity.Home.RecipeScreen
import com.fitness.elev8fit.presentation.activity.Recipe.RecipeEntry
import com.fitness.elev8fit.presentation.activity.Recipe.RecipeViewModel
import com.fitness.elev8fit.presentation.activity.SignUp.OTPVerificationScreen
import com.fitness.elev8fit.presentation.activity.SignUp.OtpViewModel
import com.fitness.elev8fit.presentation.activity.SignUp.SignUpScreen
import com.fitness.elev8fit.presentation.activity.SignUp.SignUpViewModel
import com.fitness.elev8fit.presentation.activity.login.LoginScreen
import com.fitness.elev8fit.presentation.activity.login.LoginViewModel
import com.fitness.elev8fit.presentation.activity.onboarding.Firstonboard
import com.fitness.elev8fit.presentation.activity.onboarding.thirdOnboarding
import com.fitness.elev8fit.presentation.viewmodel.imageview
import secondOnboarding


@Composable
fun displaynav() {
    val navController = rememberNavController()
    val recipeViewModel = RecipeViewModel()
    val signUpViewModel = SignUpViewModel()
    val otpViewModel = OtpViewModel()
    val loginviewmodel = LoginViewModel()
    val viewModel: imageview = imageview() // Create ViewModel here

    NavHost(navController = navController, startDestination = Navdestination.onboarding1.toString()) {
        composable(Navdestination.onboarding1.toString()) {
            Firstonboard(navController = navController)
        }
        composable(Navdestination.onboarding2.toString()) {
            secondOnboarding(viewModel = viewModel, navController = navController)
        }
        composable(Navdestination.onboarding3.toString()) {
            thirdOnboarding(viewModel = viewModel, navController = navController)
        }
        composable(Navdestination.login.toString()){
            LoginScreen(loginview = loginviewmodel, signUpViewModel =signUpViewModel,navController=navController, otpViewModel = otpViewModel )
        }
        composable(Navdestination.Signup.toString()){
            SignUpScreen(viewModel =signUpViewModel,navController=navController, otpViewModel = otpViewModel)
        }
        composable(Navdestination.home.toString()){
            HomePage(navController=navController)
        }

        composable(Navdestination.recipeentry.toString()){
            RecipeEntry(recipemodel = recipeViewModel,navController=navController )
        }
        composable(Navdestination.Recipe.toString()) {
            RecipeScreen(navController = navController)
        }
        composable(Navdestination.otp.toString()) {
            OTPVerificationScreen(otpViewModel, navController)
        }
    }
}
