package com.fitness.elev8fit.presentation.activity.onboarding

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.fitness.elev8fit.R
import com.fitness.elev8fit.presentation.common.OnBoardingCommon
import com.fitness.elev8fit.presentation.viewmodel.imageview

@Composable
fun thirdOnboarding(viewModel: imageview){
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

    }
}

@Preview(showBackground = true)
@Composable
fun thirdOnboardingpreview(){
    val mockViewModel = imageview().apply { setimg(R.drawable.boy) }

    thirdOnboarding(mockViewModel)
}
