package com.fitness.elev8fit.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.fitness.elev8fit.presentation.activity.onboarding.Firstonboard
import com.fitness.elev8fit.presentation.activity.onboarding.thirdOnboarding
import com.fitness.elev8fit.presentation.viewmodel.imageview
import secondOnboarding


@Composable
fun displaynav() {
    val navController = rememberNavController()
    val viewModel: imageview = imageview() // Create ViewModel here

    NavHost(navController = navController, startDestination = Navdestination.onboarding1.toString()) {
        composable(Navdestination.onboarding1.toString()) {
            Firstonboard(navController = navController)
        }
        composable(Navdestination.onboarding2.toString()) {
            secondOnboarding(viewModel = viewModel, navController = navController)
        }
        composable(Navdestination.onboarding3.toString()) {
            thirdOnboarding(viewModel = viewModel)
        }
    }
}
