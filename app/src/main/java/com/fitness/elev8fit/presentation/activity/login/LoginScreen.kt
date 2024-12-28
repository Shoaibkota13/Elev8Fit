package com.fitness.elev8fit.presentation.activity.login

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
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
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
import com.fitness.elev8fit.presentation.activity.SignUp.SignUpViewModel
import com.fitness.elev8fit.presentation.common.cards
import com.fitness.elev8fit.presentation.intent.LoginIntent
import com.fitness.elev8fit.presentation.intent.SignUpIntent
import com.fitness.elev8fit.ui.theme.bg_color
import com.fitness.elev8fit.ui.theme.text_color

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun LoginScreen(
    loginview: LoginViewModel,
    signUpViewModel: SignUpViewModel,
    navController: NavController
) {

    val context = LocalContext.current
    var isLogin by remember { mutableStateOf(true) }
    val loginstate by loginview.state.collectAsState()
    val signupstate by signUpViewModel.state.collectAsState()

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(bg_color)
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
                fontSize = 24.sp
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
                        colors = CardDefaults.cardColors(text_color)
                    ) {
                        Box(contentAlignment = Alignment.Center) {
                            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                Text(
                                    text = "Login",
                                    modifier = Modifier.padding(16.dp),
                                    fontSize = 24.sp
                                )

                                cards(
                                    labeltext = "Username",
                                    icons = R.drawable.ic_username,
                                    input = loginstate.username,
                                    onValueChange = { loginview.username(it) },
                                    label = "User Name"
                                )
                                cards(
                                    labeltext = "Password",
                                    icons = R.drawable.ic_username,
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
                        colors = CardDefaults.cardColors(text_color)
                    ) {
                        Box(contentAlignment = Alignment.Center) {
                            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                Text(
                                    text = "SignUp",
                                    modifier = Modifier.padding(16.dp),
                                    fontSize = 24.sp
                                )

                                cards(
                                    icons = R.drawable.ic_username,
                                    input = signupstate.username,
                                    label = "Enter user name",
                                    labeltext = "User Name",
                                    onValueChange = { signUpViewModel.username(it) }
                                )

                                cards(
                                    icons = R.drawable.ic_username,
                                    input = signupstate.password,
                                    label = "Enter password",
                                    onValueChange = { signUpViewModel.password(it) },
                                    labeltext = "Password",
                                    isPassword = true
                                )

                                cards(
                                    icons = R.drawable.ic_username,
                                    input = signupstate.Confirmpassword,
                                    label = "Confirm password",
                                    onValueChange = { signUpViewModel.confirmPassword(it) },
                                    labeltext = "Confirm Password",
                                    isPassword = true
                                )

                                cards(
                                    icons = R.drawable.ic_username,
                                    input = signupstate.phonenumber,
                                    label = "Phone number",
                                    onValueChange = { signUpViewModel.phonenumber(it) },
                                    labeltext = "Phone number",
                                    keyboardType = KeyboardType.Phone
                                )

                                Button(
                                    onClick = {
                                        signUpViewModel.SignUpIntentHandler(
                                            SignUpIntent.Signup(
                                                signupstate.username,
                                                signupstate.password,
                                                signupstate.phonenumber
                                            ), navController
                                        )
                                    },
                                    modifier = Modifier
                                        .padding(16.dp)
                                        .fillMaxWidth()
                                ) {
                                    Text("Sign Up")
                                }

                                signupstate.errorMessage?.let {
                                    Text(it, color = Color.Red, modifier = Modifier.padding(8.dp))
                                }

                                if (signupstate.isLoading) {
                                    CircularProgressIndicator()
                                }

                                signupstate.successMessage?.let {
                                    Toast.makeText(context, it, Toast.LENGTH_SHORT).show()
                                    signUpViewModel.clearSuccessMessage()
                                }
                            }
                        }
                    }
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


