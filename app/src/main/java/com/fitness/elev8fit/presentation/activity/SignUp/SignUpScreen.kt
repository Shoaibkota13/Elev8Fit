package com.fitness.elev8fit.presentation.activity.SignUp

import android.app.Activity
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.CutCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.fitness.elev8fit.R
import com.fitness.elev8fit.presentation.common.cards
import com.fitness.elev8fit.presentation.intent.SignUpIntent
import com.fitness.elev8fit.presentation.navigation.Navdestination
import com.fitness.elev8fit.ui.theme.bg_color
import com.fitness.elev8fit.ui.theme.text_color

@Composable
fun SignUpScreen(viewModel: SignUpViewModel, navController: NavController) {
    val signupstate by viewModel.state.collectAsState()

    val context = LocalContext.current
    val activity = context as? Activity

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(bg_color)
            .padding(8.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(8.dp))

            // App Title
            Row(horizontalArrangement = Arrangement.Center) {
                Text(
                    text = "Elev",
                    color = Color.Blue,
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = "8Fit",
                    color = Color.Green,
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            // Welcome Message
            Text(
                text = "Welcome To Elev8Fit",
                modifier = Modifier.padding(16.dp),
                fontSize = 24.sp
            )

            Card(
                shape = CutCornerShape(8.dp),
                colors = CardDefaults.cardColors(text_color)
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.padding(16.dp)
                ) {
                    Text(
                        text = "SignUp",
                        fontSize = 24.sp
                    )

                    // Username Input
                    cards(
                        icons = R.drawable.ic_username,
                        input = signupstate.username,
                        label = "Enter user name",
                        labeltext = "User Name",
                        onValueChange = { viewModel.username(it) }
                    )

                    // Password Input
                    cards(
                        icons = R.drawable.ic_username,
                        input = signupstate.password,
                        label = "Enter password",
                        onValueChange = { viewModel.password(it) },
                        labeltext = "Password",
                        isPassword = true
                    )

                    // Confirm Password Input
                    cards(
                        icons = R.drawable.ic_username,
                        input = signupstate.Confirmpassword,
                        label = "Confirm password",
                        onValueChange = { viewModel.confirmPassword(it) },
                        labeltext = "Confirm Password",
                        isPassword = true
                    )

                    // Phone Number Input
                    cards(
                        icons = R.drawable.ic_username,
                        input = signupstate.phonenumber,
                        label = "Phone number",
                        onValueChange = { input ->
                            if (input.length <= 20) {
                                viewModel.phonenumber(input)
                            }
                        },
                        labeltext = "Phone number",
                        keyboardType = KeyboardType.Phone
                    )

                    // OTP Verification link
                    if (signupstate.phonenumber.length == 14) {
                        Text(
                            text = "Verify OTP",
                            modifier = Modifier
                                .padding(start = 8.dp)
                                .align(Alignment.Start)
                                .clickable {
                                    if (activity != null) {
                                        viewModel.triggerOtp(
                                            activity,
                                            phoneNumber = signupstate.phonenumber,
                                            onOtpSent = { verificationId ->
                                                // Pass the verificationId to the OTPVerificationScreen
                                                navController.currentBackStackEntry?.savedStateHandle?.set(
                                                    "verificationId",
                                                    verificationId
                                                )
                                                navController.navigate(Navdestination.otp.toString())
                                            },
                                            onFailure = { error ->
                                                Toast.makeText(
                                                    context,
                                                    error,
                                                    Toast.LENGTH_SHORT
                                                ).show()
                                            }
                                        )
                                    }
                                }
                        )
                    }

                    // Sign Up Button
                    Button(
                        onClick = {
                            if (signupstate.phonenumber.length == 10 && signupstate.password == signupstate.Confirmpassword) {
                                viewModel.SignUpIntentHandler(
                                    SignUpIntent.Signup(
                                        signupstate.username,
                                        signupstate.password,
                                        signupstate.phonenumber
                                    ),
                                    navController
                                )
                            }
                        },
                        modifier = Modifier
                            .padding(16.dp)
                            .fillMaxWidth()
                    ) {
                        Text("Sign Up")
                    }

                    // Error message
                    signupstate.errorMessage?.let {
                        Text(it, color = Color.Red, modifier = Modifier.padding(8.dp))
                    }

                    // Loading indicator
                    if (signupstate.isLoading) {
                        CircularProgressIndicator()
                    }

                    // Success message
                    signupstate.successMessage?.let {
                        Toast.makeText(context, it, Toast.LENGTH_SHORT).show()
                        viewModel.clearSuccessMessage()
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Social Media Login
            Text(text = "Or")
            Row(horizontalArrangement = Arrangement.SpaceEvenly) {
                Image(
                    painter = painterResource(R.drawable.google),
                    contentDescription = null,
                    modifier = Modifier
                        .size(60.dp)
                        .clickable {
                            // Handle Google login
                        }
                )
                Image(
                    painter = painterResource(R.drawable.facebook),
                    contentDescription = null,
                    modifier = Modifier
                        .size(60.dp)
                        .clickable {
                            // Handle Facebook login
                        }
                )
            }
        }
    }
}
