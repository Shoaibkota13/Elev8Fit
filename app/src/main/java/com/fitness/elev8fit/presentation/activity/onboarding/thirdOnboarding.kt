package com.fitness.elev8fit.presentation.activity.onboarding

import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import com.fitness.elev8fit.R
import com.fitness.elev8fit.presentation.common.OnBoardingCommon
import com.fitness.elev8fit.presentation.navigation.Navdestination
import com.fitness.elev8fit.presentation.viewmodel.imageview

//@Composable
//fun thirdOnboarding(viewModel: imageview,navController: NavController){
//    val selectedImage = viewModel.selectedimg.value
//
//    OnBoardingCommon(
//        cardcontent = "Enter the details",
//        imageresid1 = selectedImage?: R.drawable.boy,
//        imageresid2 = null,
//        imageresid3 = null,
//        text1 = null,
//        text2 = null,
//        ageinput = 22,
//        nameinput = "null",
//        buttontext ="Continue",
//        viewModel = viewModel
//    ) {
//        navController.navigate(Navdestination.Signup.toString())
//
//    }
//}
//

@Composable
fun thirdOnboarding(viewModel: imageview, navController: NavController) {
    val selectedImage = viewModel.selectedimg.value
    val ageInput = viewModel.ageinput.value ?: 22  // Default age if not set
    val nameInput = viewModel.nameinput.value ?: "null"  // Default name if not set

    OnBoardingCommon(
        cardcontent = "Enter the details",
        imageresid1 = selectedImage ?: R.drawable.boy,
        imageresid2 = null,
        imageresid3 = null,
        text1 = null,
        text2 = null,
        ageinput = ageInput,
        nameinput = nameInput,
        buttontext = "Continue",
        viewModel = viewModel
    ) {
        // Pass age and name to SignUpViewModel when user clicks Continue
        viewModel.setage(ageInput)  // Set age in the ViewModel
        viewModel.setname(nameInput)  // Set name in the ViewModel

        navController.navigate(Navdestination.Signup.toString())
    }
}
