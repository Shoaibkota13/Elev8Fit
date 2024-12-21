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
import androidx.compose.foundation.layout.width
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.fitness.elev8fit.R
import com.fitness.elev8fit.presentation.common.cards
import com.fitness.elev8fit.presentation.intent.LoginIntent
import com.fitness.elev8fit.presentation.viewmodel.LoginViewModel
import com.fitness.elev8fit.ui.theme.bg_color
import com.fitness.elev8fit.ui.theme.card_color

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun LoginScreen(viewModel: LoginViewModel) {
    val context = LocalContext.current
    var isLogin by remember { mutableStateOf(true) }  // Track if login is active

    val loginstate by viewModel.state.collectAsState()
    var username by remember {
        mutableStateOf("")
    }
    var password by remember {
        mutableStateOf("")
    }
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

            // Welcome Message
            Text(
                text = "Welcome To Elev8Fit",
                modifier = Modifier.padding(16.dp),
                fontSize = 24.sp
            )

            Spacer(modifier = Modifier.height(8.dp))


            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                horizontalArrangement = Arrangement.SpaceAround
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
                        .clickable {
                            isLogin = false
                            // SignUpScreen(viewModel = SignUpViewModel())
                        }
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
                }, label = " "
            ) { targetState ->
                if (targetState) {
                    Card(
                        shape = CutCornerShape(16.dp),
                        colors = CardDefaults.cardColors(card_color)
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
                                    input = username,
                                    onValueChange = { username = it },
                                    label = "User Name"
                                )
                                cards(
                                    labeltext = "Password",
                                    icons = R.drawable.ic_username,
                                    input = password,
                                    onValueChange = {password = it },
                                    label = "Enter Password"
                                )

                                Button(
                                    onClick = {
                                        viewModel.LoginIntent(LoginIntent.LoginDetails(username,password))
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

                }
                else{
                //    SignUpScreen(viewModel = SignUpViewModel())
                }
                }

            if(loginstate.isLoading){
                CircularProgressIndicator()
            }

            if (loginstate.successMessage != null) {
                Toast.makeText(context, loginstate.successMessage, Toast.LENGTH_LONG).show()
            }
            if (loginstate.errorMessage != null) {
                Toast.makeText(context, loginstate.errorMessage, Toast.LENGTH_LONG).show()
            }


            Text(text = "Or")
            Row(horizontalArrangement = Arrangement.SpaceEvenly) {
                Image(painter = painterResource(id = R.drawable.logo),
                    contentDescription = "",
                    modifier = Modifier
                        .width(60.dp)
                        .height(70.dp)
                        .clickable {

                        })
                Image(painter = painterResource(id = R.drawable.logo),
                    contentDescription = "",
                    modifier = Modifier
                        .width(60.dp)
                        .height(70.dp)
                        .clickable {

                        })
                Image(painter = painterResource(id = R.drawable.logo),
                    contentDescription = "",
                    modifier = Modifier
                        .width(60.dp)
                        .height(70.dp)
                        .clickable {

                        })


            }


        }
    }
}

