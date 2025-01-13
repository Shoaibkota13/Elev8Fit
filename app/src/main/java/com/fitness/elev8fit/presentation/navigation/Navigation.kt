package com.fitness.elev8fit.presentation.navigation

import android.net.Uri
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.fitness.elev8fit.presentation.activity.Excercise.ExerciseDetailScreen
import com.fitness.elev8fit.presentation.activity.Home.HomePage
import com.fitness.elev8fit.presentation.activity.Otp.OTPVerificationScreen
import com.fitness.elev8fit.presentation.activity.Recipe.RecipeEntry
import com.fitness.elev8fit.presentation.activity.Recipe.RecipeScreen.RecipeScreen
import com.fitness.elev8fit.presentation.activity.SignUp.SignUpScreen
import com.fitness.elev8fit.presentation.activity.chat.ui.ChatMain
import com.fitness.elev8fit.presentation.activity.chat.ui.ChatScreen
import com.fitness.elev8fit.presentation.activity.login.LoginScreen
import com.fitness.elev8fit.presentation.activity.onboarding.Firstonboard
import com.fitness.elev8fit.presentation.activity.onboarding.secondOnboarding
import com.fitness.elev8fit.presentation.activity.onboarding.thirdOnboarding

@Composable
fun displaynav(
    navController: NavHostController,
    isAuthenticated: Boolean,
    deepLink: Uri?,
    chatid: String?
) {
    //val auth = Firebase.auth.currentUser?.uid

    val startDestination = when {
        !isAuthenticated -> {
            Navdestination.onboarding1.toString()
        }
        deepLink != null -> {
            handleDeepLink(deepLink)
        }
        chatid != null -> {

            Navdestination.chatuser.toString()
        }
        else -> {
            Navdestination.home.toString()
        }
    }

    NavHost(navController = navController, startDestination = startDestination) {
        composable(
            route = Navdestination.onboarding1.toString(),

        ) {
            Firstonboard(navController = navController)
        }
        composable(Navdestination.onboarding2.toString()) {
            secondOnboarding(viewModel = hiltViewModel(), navController = navController)
        }
        composable(Navdestination.onboarding3.toString()) {
            thirdOnboarding(signUpViewModel = hiltViewModel(), imageview = hiltViewModel(), navController = navController)
        }
        composable(Navdestination.login.toString()) {
            LoginScreen(loginview = hiltViewModel(), signUpViewModel = hiltViewModel(), navController = navController, otpViewModel = hiltViewModel(), googleSignInViewModel = hiltViewModel())
        }
        composable(Navdestination.Signup.toString()) {
            SignUpScreen(viewModel = hiltViewModel(), navController = navController,
                hiltViewModel(), googleSignInViewModel = hiltViewModel())
        }
        composable(Navdestination.home.toString()) {
            HomePage(navController = navController)
        }

        composable(Navdestination.recipeentry.toString()) {
            RecipeEntry(recipemodel = hiltViewModel(), navController = navController)
        }
        composable(Navdestination.Recipe.toString()) {
            RecipeScreen(recipeScreenViewModel = hiltViewModel(), navController = navController)
        }
        composable(Navdestination.otp.toString()) {
            OTPVerificationScreen(navController, hiltViewModel())
        }
        composable(Navdestination.chat.toString()) {
            ChatMain()
        }
        composable(Navdestination.chatuser.toString()) {
            if (chatid != null ) {
                ChatScreen(
                    viewModel = hiltViewModel(),
                    chatRoomId = chatid,
                    navController=navController
                )

            }
        }

        composable(
            route = "exercise_detail/{exerciseId}",
            arguments = listOf(navArgument("exerciseId") { type = NavType.StringType })
        ) { backStackEntry ->
            val exerciseId = backStackEntry.arguments?.getString("exerciseId") ?: return@composable
            ExerciseDetailScreen(
                exerciseId = exerciseId,
                navController = navController,
                viewModel = hiltViewModel()
            )
        }
    }
    LaunchedEffect(deepLink) {
        deepLink?.let { uri ->
            val destination = handleDeepLink(uri)
            navController.navigate(destination) {
                popUpTo(navController.graph.startDestinationId) {
                    saveState = true
                }
                launchSingleTop = true
                restoreState = true
            }
        }
    }
}

private fun handleDeepLink(uri: Uri): String {
    return when {
        uri.scheme == "https" && uri.host == "elev8fit" -> {
            when (uri.path) {
                "/onboard" -> Navdestination.onboarding1.toString()
                "/login"->Navdestination.login.toString()
                else -> Navdestination.onboarding1.toString()
            }
        }
        else -> Navdestination.onboarding1.toString()
    }
}
