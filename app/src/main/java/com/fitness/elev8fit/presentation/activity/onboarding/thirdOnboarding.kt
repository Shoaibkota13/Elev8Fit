package com.fitness.elev8fit.presentation.activity.onboarding

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import com.fitness.elev8fit.R
import com.fitness.elev8fit.presentation.common.OnBoardingCommon
import com.fitness.elev8fit.presentation.navigation.Navdestination
import com.fitness.elev8fit.presentation.viewmodel.imageview

@Composable
fun thirdOnboarding(viewModel: imageview,navController: NavController){
    val selectedImage = viewModel.selectedimg.value

    OnBoardingCommon(
        cardcontent = "Enter the details",
        imageresid1 = selectedImage?: R.drawable.girl,
        imageresid2 = null,
        imageresid3 = null,
        text1 = null,
        text2 = null,
        ageinput = 22,
        nameinput = "null",
        buttontext ="Continue",
        viewModel = viewModel
    ) {
        navController.navigate(Navdestination.Signup.toString())

    }
}

