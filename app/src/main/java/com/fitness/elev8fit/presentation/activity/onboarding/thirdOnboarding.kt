package com.fitness.elev8fit.presentation.activity.onboarding

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CutCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.fitness.elev8fit.presentation.activity.SignUp.SignUpViewModel
import com.fitness.elev8fit.presentation.navigation.Navdestination
import com.fitness.elev8fit.presentation.viewmodel.imageview
import com.fitness.elev8fit.ui.theme.CustomBackgroundColor
import com.fitness.elev8fit.ui.theme.bg_color
import com.fitness.elev8fit.ui.theme.card_color

@Composable
fun thirdOnboarding(signUpViewModel: SignUpViewModel,imageview: imageview,navController: NavController) {
    val selectedimg = imageview.selectedimg.value
    val signupdata by signUpViewModel.state.collectAsState()
    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(bg_color)
            .padding(16.dp)
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Display an image
            Image(
                painter = painterResource(id = selectedimg),
                contentDescription = "",
                modifier = Modifier
                    .border(1.dp, Color.Gray)
                    .width(200.dp)
                    .height(200.dp)
            )

            // Card with title
            Card(
                modifier = Modifier.padding(16.dp),
                shape = CardDefaults.shape,
                colors = CardDefaults.cardColors(card_color)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        text = "Enter the Details",
                        color = CustomBackgroundColor,
                        fontSize = 24.sp,
                        textAlign = TextAlign.Center
                    )
                }
            }

            // Age selector text
            Text(
                text = "Select Age",
                fontSize = 20.sp,
                color = CustomBackgroundColor,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                textAlign = TextAlign.Center
            )

            // LazyColumn for selecting age
            LazyColumn(
                modifier = Modifier
                    .height(150.dp)
                    .fillMaxWidth()
            ) {
                items((18..100).toList()) { age ->
                    Text(
                        text = "$age",
                        fontSize = 24.sp,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(8.dp)
                            .clickable {
                                signUpViewModel.setAge(age.toString())

                            },
                        textAlign = TextAlign.Center
                    )
                }
            }

            // Prompt to enter name
            Text(
                text = "Enter Your Name",
                fontSize = 20.sp,
                color = CustomBackgroundColor,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                textAlign = TextAlign.Start
            )

            // TextField for name input
            TextField(
                value = signupdata.name,
                onValueChange = { signUpViewModel.setUsername(it) },
                label = { Text("Enter Your Name") },
                modifier = Modifier
                    .padding(8.dp)
                    .fillMaxWidth()
            )

            Button(onClick = {



                    navController.navigate(Navdestination.Signup.toString())


            }, modifier = Modifier.padding(16.dp),
                shape = CutCornerShape(8.dp)
            ) {

                Text(text = "Continue")

            }
        }
    }
}

//@Preview(showBackground = true)
//@Composable
//fun PreviewThirdOnboarding() {
//    thirdOnboarding()
//}


