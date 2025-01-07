package com.fitness.elev8fit.presentation.activity.onboarding


import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CutCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.fitness.elev8fit.R
import com.fitness.elev8fit.presentation.navigation.Navdestination
import com.fitness.elev8fit.ui.theme.CustomBackgroundColor
import com.fitness.elev8fit.ui.theme.Quotes
import com.fitness.elev8fit.ui.theme.bg_color
import com.fitness.elev8fit.ui.theme.quicksand

@Composable
fun Firstonboard(navController: NavController){

    Box(modifier = Modifier
        .fillMaxSize()
        .background(bg_color)
        .padding(16.dp),
        contentAlignment = Alignment.Center
    )
    {

        Column(modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row(horizontalArrangement = Arrangement.Center) {
                Text(text = "Elev",
                    color = Color.Blue,
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold)
                Text(
                    text = "8Fit",
                    color = Color.Green,
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold
                )
            }
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = " Have A Good Health",
                fontFamily = quicksand,
                fontWeight = FontWeight.Bold,
                fontSize = 24.sp,
                color = MaterialTheme.colorScheme.primary
            )

            Image(painter = painterResource(id = R.drawable.logo),
                contentDescription = "logo")

            Spacer(modifier = Modifier.height(16.dp))

            Card(
                modifier = Modifier.padding(16.dp),
                shape = CardDefaults.shape,
                colors = CardDefaults.cardColors(MaterialTheme.colorScheme.tertiary)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        text = Quotes.getRandomQuote(),
                        fontSize = 24.sp,
                        fontFamily = quicksand,
                        fontWeight = FontWeight.Black,
                        color = CustomBackgroundColor,
                        textAlign = TextAlign.Justify
                    )
                }
            }
            val context = LocalContext.current
            Text(text = "If you have account,please sign in",
                modifier = Modifier.clickable {
            navController.navigate(Navdestination.login.toString())
                }, fontFamily = quicksand, fontSize = 16.sp,
                fontWeight = FontWeight.Bold,color = MaterialTheme.colorScheme.primary)

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp), // Optional padding around the Column
                verticalArrangement = Arrangement.Bottom, // Places content at the bottom
                horizontalAlignment = Alignment.CenterHorizontally // Centers content horizontally
            ) {
                Button(
                    onClick = { navController.navigate(Navdestination.onboarding2.toString())},
                    modifier = Modifier.padding(16.dp),
                    shape = CutCornerShape(8.dp),
                    colors = ButtonDefaults.buttonColors(
                       MaterialTheme.colorScheme.primary
                    )
                ) {
                    Text(
                        text = "Let's Start",
                        fontSize = 24.sp,
                        color = Color.White
                    )

                }
            }
        }

    }
}
@Preview(showBackground = true)
@Composable
fun OnboardingPreview() {
    val navController = rememberNavController() // Remember NavController for preview
    Firstonboard(navController = navController) // Pass NavController to onboarding function
}