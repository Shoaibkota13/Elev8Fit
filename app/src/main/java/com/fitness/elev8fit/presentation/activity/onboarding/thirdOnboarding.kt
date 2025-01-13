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
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.fitness.elev8fit.R
import com.fitness.elev8fit.presentation.activity.SignUp.SignUpViewModel
import com.fitness.elev8fit.presentation.navigation.Navdestination
import com.fitness.elev8fit.presentation.viewmodel.imageview

@Composable
fun thirdOnboarding(signUpViewModel: SignUpViewModel,imageview: imageview,navController: NavController) {
    val selectedimg = imageview.selectedimg.value
    val signupdata by signUpViewModel.state.collectAsState()

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.secondary)
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
                colors = CardDefaults.cardColors(MaterialTheme.colorScheme.tertiary),
                elevation = CardDefaults.elevatedCardElevation(defaultElevation = 8.dp)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        text = "Enter the Details",
                        color = MaterialTheme.colorScheme.primary,
                        fontSize = 24.sp,
                        textAlign = TextAlign.Center
                    )
                }
            }

            // Age selector text
            Text(
                text = stringResource(R.string.age),
                fontSize = 20.sp,
                color = MaterialTheme.colorScheme.primary,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                textAlign = TextAlign.Center
            )

            // LazyColumn for selecting age
            LazyColumn(
                modifier = Modifier
                    .height(50.dp)
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
                        textAlign = TextAlign.Center,
                        color = MaterialTheme.colorScheme.primary
                    )
                }
            }

            // Prompt to enter name
            Text(
                text = stringResource(R.string.name),
                fontSize = 20.sp,
                color = MaterialTheme.colorScheme.primary,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                textAlign = TextAlign.Start
            )

            // TextField for name input
            TextField(
                value = signupdata.name,
                onValueChange = { signUpViewModel.setUsername(it) },
                modifier = Modifier
                    .padding(8.dp)
                    .fillMaxWidth(),
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = Color.White,
                    unfocusedContainerColor = Color.White,
                    focusedTextColor = Color.Black
                )
            )
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                contentAlignment = Alignment.BottomCenter
            ) {
                Button(
                    onClick = {
                        navController.navigate(Navdestination.Signup.toString())
                    },
                    shape = CutCornerShape(8.dp),
                    colors = ButtonDefaults.buttonColors(MaterialTheme.colorScheme.primary)
                ) {
                    Text(text = stringResource(R.string.btn_continue))
                }
            }
        }
    }
}

//@Preview(showBackground = true)
//@Composable
//fun PreviewThirdOnboarding() {
//    thirdOnboarding()
//}


