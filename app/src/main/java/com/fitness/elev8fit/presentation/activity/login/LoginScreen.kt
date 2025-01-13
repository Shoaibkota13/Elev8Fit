package com.fitness.elev8fit.presentation.activity.login

import android.app.Activity
import android.util.Log
import android.widget.Toast
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.animation.with
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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
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
import com.fitness.elev8fit.presentation.activity.Otp.OtpViewModel
import com.fitness.elev8fit.presentation.activity.SignUp.SignUpViewModel
import com.fitness.elev8fit.presentation.activity.socialLoginSignIn.GoogleSignInViewModel
import com.fitness.elev8fit.presentation.activity.common.cards
import com.fitness.elev8fit.presentation.intent.LoginIntent
import com.fitness.elev8fit.presentation.intent.SignUpIntent
import com.fitness.elev8fit.presentation.navigation.Navdestination

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun LoginScreen(
    loginview: LoginViewModel,
    signUpViewModel: SignUpViewModel,
    navController: NavController,
    otpViewModel: OtpViewModel,
    googleSignInViewModel: GoogleSignInViewModel
) {

    val context = LocalContext.current
    val activity = context as? Activity
    var isLogin by remember { mutableStateOf(true) }
    val loginstate by loginview.state.collectAsState()
    val signupstate by signUpViewModel.state.collectAsState()
    val otpstate by otpViewModel.state.collectAsState()

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.secondary)
            .padding(16.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
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

            Text(
                text = "Welcome To Elev8Fit",
                fontSize = 24.sp,
               color = MaterialTheme.colorScheme.primary
            )

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                Text(
                    text = "Login",
                    modifier = Modifier
                        .clickable { isLogin = true }
                        .padding(8.dp),
                    color = if (isLogin) Color.Blue else Color.Gray,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = "Sign Up",
                    modifier = Modifier
                        .clickable { isLogin = false }
                        .padding(8.dp),
                    color = if (!isLogin) Color.Blue else Color.Gray,
                    fontWeight = FontWeight.Bold
                )
            }

            AnimatedContent(
                targetState = isLogin,
                transitionSpec = {
                    slideInHorizontally(initialOffsetX = { if (targetState) -it else it }) + fadeIn() with
                            slideOutHorizontally(targetOffsetX = { if (targetState) it else -it }) + fadeOut()
                }, label = ""
            ) { targetState ->
                if (targetState) {
                    Card(
                        shape = CutCornerShape(8.dp),
                        colors = CardDefaults.cardColors(MaterialTheme.colorScheme.tertiary)
                    ) {
                        Box(contentAlignment = Alignment.Center) {
                            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                Text(
                                    text = "Login",
                                    modifier = Modifier.padding(16.dp),
                                    fontSize = 24.sp,
                                    color = MaterialTheme.colorScheme.primary
                                )

                                cards(
                                    labeltext = "Username",
                                    icons = R.drawable.user,
                                    input = loginstate.username,
                                    onValueChange = { loginview.username(it) },
                                    label = "User Name"
                                )
                                cards(
                                    labeltext = "Password",
                                    icons = R.drawable.padlock,
                                    input = loginstate.password,
                                    onValueChange = { loginview.password(it) },
                                    label = "Enter Password",
                                    isPassword = true
                                )

                                Button(
                                    onClick = {
                                        loginview.handleLoginIntent(
                                            LoginIntent.LoginDetails(
                                                username = loginstate.username,
                                                password = loginstate.password
                                            ),
                                            navController = navController ,context=context// Pass NavController here
                                        )
                                    },
                                    modifier = Modifier
                                        .padding(16.dp)
                                        .fillMaxWidth()
                                ) {
                                    Text("Login")
                                }
                            }
                        }
                    }
                } else {
                    Card(
                        shape = CutCornerShape(8.dp),
                        colors = CardDefaults.cardColors(MaterialTheme.colorScheme.tertiary)
                    ) {
                        Box(contentAlignment = Alignment.Center) {
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
                                    label = "Enter user name",
                                    labeltext = "User Name",
                                    onValueChange = { signUpViewModel.email(it) }
                                )

                                // Password Input
                                cards(
                                    icons = R.drawable.padlock,
                                    input = signupstate.password,
                                    label = "Enter password",
                                    onValueChange = { signUpViewModel.password(it) },
                                    labeltext = "Password",
                                    isPassword = true
                                )

                                // Confirm Password Input
                                cards(
                                    icons = R.drawable.padlock,
                                    input = signupstate.Confirmpassword,
                                    label = "Confirm password",
                                    onValueChange = { signUpViewModel.confirmPassword(it) },
                                    labeltext = "Confirm Password",
                                    isPassword = true
                                )

                                // Phone Number Input
                                cards(
                                    icons = R.drawable.telephone,
                                    input = signupstate.phonenumber,
                                    label = "Phone number",
                                    onValueChange = { input ->
                                        if (input.length <= 10) {
                                            signUpViewModel.phonenumber(input)
                                        }
                                    },
                                    labeltext = "Phone number",
                                    keyboardType = KeyboardType.Phone
                                )

                                // OTP Verification link
                                if (signupstate.phonenumber.length ==10){
                                    if (!otpstate.isverifed  ) {
                                        if (otpstate.isLoading) {
                                            CircularProgressIndicator(
                                                modifier = Modifier
                                                    .align(Alignment.CenterHorizontally)
                                                    .padding(8.dp)
                                            )
                                        } else {
                                            Text(
                                                text = "Verify OTP",
                                                color = MaterialTheme.colorScheme.primary,
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
                                                                    navController.navigate(
                                                                        Navdestination.otp.toString()
                                                                    )
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
                                }



                                // Sign Up Button
                                Button(
                                    onClick = {

                                        if (otpstate.isverifed && signupstate.phonenumber.length == 10 && signupstate.password == signupstate.Confirmpassword) {

                                            Log.e("ots ","${otpstate.isverifed}")
                                            // Proceed with the signup
                                            signUpViewModel.SignUpIntentHandler(
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

                                // Sign Up Button


                                // Error message
                                signupstate.errorMessage?.let {
                                    Text(it, color = Color.Red, modifier = Modifier.padding(8.dp))
                                }

                                if (signupstate.isLoading) {
                                    CircularProgressIndicator()
                                }

                                signupstate.successMessage?.let {
                                    Toast.makeText(context, it, Toast.LENGTH_SHORT).show()
                                    signUpViewModel.clearSuccessMessage()
                                    signUpViewModel.resetFields()
                                }
                                }
                            }

                    }

                  //  SignUpScreen(viewModel = hiltViewModel(), navController =navController , otpViewModel = hiltViewModel() )
                }
            }

            if (loginstate.isLoading) {
                CircularProgressIndicator()
            }

            loginstate.successMessage?.let {
                Text(it, color = Color.Red, modifier = Modifier.padding(8.dp))
                loginview.clearSuccessMessage()
            }

            loginstate.errorMessage?.let {
                Text(it, color = Color.Red, modifier = Modifier.padding(8.dp))

            }

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
                        .clickable { }
                )
                }
            }
        }
    }


