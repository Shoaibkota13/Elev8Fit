package com.fitness.elev8fit.presentation.activity.onboarding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.fitness.elev8fit.R
import com.fitness.elev8fit.presentation.common.OnBoardingCommon
import com.fitness.elev8fit.presentation.navigation.Navdestination
import com.fitness.elev8fit.presentation.viewmodel.imageview

@Composable
fun secondOnboarding(navController: NavController,viewModel: imageview) {
        val context = LocalContext.current

        // Your onBoardingCommon content
       OnBoardingCommon(
            cardcontent = stringResource(R.string.Gender),
            imageresid1 = R.drawable.boy,
            imageresid2 = R.drawable.boy,
            imageresid3 = R.drawable.girl,
            text1="Male",
            text2="Female",
            showimage2 = true,
            showimage3 = true,
            buttontext = "Continue",
           viewModel = viewModel
        ) {
            navController.navigate(Navdestination.onboarding3.toString())

        }
    }


@Preview(showBackground = true)
@Composable
fun OnboardingPagePreview() {
    MaterialTheme {
        Surface {
            val navController = rememberNavController() // Remember NavController for preview
            val mockViewModel = imageview().apply { setimg(R.drawable.boy) }
            secondOnboarding(navController, viewModel = mockViewModel)        }
    }
}
