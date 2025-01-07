package com.fitness.elev8fit.presentation.activity.SignUp

import android.app.Activity
import android.util.Log
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
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
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
import com.fitness.elev8fit.data.constant.DataStoreManager
import com.fitness.elev8fit.presentation.activity.Otp.OtpViewModel
import com.fitness.elev8fit.presentation.activity.Otp.otpverifyviewmodel
import com.fitness.elev8fit.presentation.activity.socialLoginSignIn.GoogleSignInViewModel
import com.fitness.elev8fit.presentation.common.cards
import com.fitness.elev8fit.presentation.intent.SignUpIntent
import com.fitness.elev8fit.presentation.navigation.Navdestination
import kotlinx.coroutines.launch

@Composable
fun SignUpScreen(viewModel: SignUpViewModel,
                 navController: NavController ,otpViewModel:OtpViewModel,
                 googleSignInViewModel: GoogleSignInViewModel,otpverifys: otpverifyviewmodel
) {
    val otpstate by otpViewModel.state.collectAsState()
    val otpverify by otpverifys.state.collectAsState()
    val signupstate by viewModel.state.collectAsState()
    val coroutine = rememberCoroutineScope()

    val context = LocalContext.current
    val activity = context as? Activity


    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.secondary)
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
                fontSize = 24.sp,
                color = MaterialTheme.colorScheme.primary
            )

            Card(
                shape = CutCornerShape(8.dp),
                colors = CardDefaults.cardColors(MaterialTheme.colorScheme.tertiary)
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.padding(16.dp)
                ) {
                    Text(
                        text = "SignUp",
                        fontSize = 24.sp,
                        color = MaterialTheme.colorScheme.primary
                    )

                    // Username Input
                    cards(
                        icons = R.drawable.user,
                        input = signupstate.email,
                        label = "Enter Email ",
                        labeltext = "User Name",
                        onValueChange = { viewModel.email(it) }
                    )

                    // Password Input
                    cards(
                        icons = R.drawable.padlock,
                        input = signupstate.password,
                        label = "Enter password",
                        onValueChange = { viewModel.password(it) },
                        labeltext = "Password",
                        isPassword = true
                    )

                    // Confirm Password Input
                    cards(
                        icons = R.drawable.padlock,
                        input = signupstate.Confirmpassword,
                        label = "Confirm password",
                        onValueChange = { viewModel.confirmPassword(it) },
                        labeltext = "Confirm Password",
                        isPassword = true
                    )

                    // Phone Number Input
                    cards(
                        icons = R.drawable.telephone,
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
                    if (!otpverify.isverifed) {
                        if (otpstate.isLoading) {
                            CircularProgressIndicator(
                                modifier = Modifier
                                    .align(Alignment.CenterHorizontally)
                                    .padding(8.dp)
                            )
                        } else {
                            Text(
                                text = "Verify OTP",
                                color = Color.Red,
                                modifier = Modifier
                                    .padding(start = 8.dp)
                                    .align(Alignment.Start)
                                    .clickable {
                                        if (activity != null) {
                                            otpViewModel.triggerOtp(
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
                                                    Toast
                                                        .makeText(
                                                            context,
                                                            error,
                                                            Toast.LENGTH_SHORT
                                                        )
                                                        .show()
                                                }
                                            )
                                        }
                                    }
                            )
                        }
                    }
                         else {
                        Text(
                            text = "Verified",
                            modifier = Modifier
                                .padding(start = 8.dp)
                                .align(Alignment.Start),
                            color = Color.Green
                        )
                    }


                    // Sign Up Button
                    Button(
                        onClick = {

                                if (otpverify.isverifed) {

                            Log.e("ots ","${otpverify.isverifed}")
                                    // Proceed with the signup
                                    viewModel.SignUpIntentHandler(
                                        SignUpIntent.Signup(
                                            signupstate.email,
                                            signupstate.password,
                                            signupstate.phonenumber
                                        ),
                                        navController
                                    )
                                }
                        else {

                                    Toast.makeText(context, "Please verify OTP first", Toast.LENGTH_SHORT).show()
                                }

                        }, colors = ButtonDefaults.buttonColors(MaterialTheme.colorScheme.primary),
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
                        coroutine.launch {
                            DataStoreManager.saveAuthState(context, true)
                            Toast.makeText(context, it, Toast.LENGTH_SHORT).show()
                            viewModel.clearSuccessMessage()
                            viewModel.resetFields()
                        }

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
                            googleSignInViewModel.handleGoogleSignIn(context, navController)
                        }
                )
                Image(
                    painter = painterResource(R.drawable.facebook),
                    contentDescription = null,
                    modifier = Modifier
                        .size(60.dp)
                        .clickable {
                            // Handle Facebook login
//                            facebookSignInViewModel.handleFacebookSignIn(context,navController,)


                        }
                )
            }
        }
    }
}
