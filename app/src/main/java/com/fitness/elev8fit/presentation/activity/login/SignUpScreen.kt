//package com.fitness.elev8fit.presentation.activity.login
//
//import android.app.Activity
//import androidx.compose.foundation.background
//import androidx.compose.foundation.layout.Arrangement
//import androidx.compose.foundation.layout.Box
//import androidx.compose.foundation.layout.Column
//import androidx.compose.foundation.layout.Row
//import androidx.compose.foundation.layout.Spacer
//import androidx.compose.foundation.layout.fillMaxSize
//import androidx.compose.foundation.layout.fillMaxWidth
//import androidx.compose.foundation.layout.height
//import androidx.compose.foundation.layout.padding
//import androidx.compose.foundation.shape.CutCornerShape
//import androidx.compose.material3.Button
//import androidx.compose.material3.Card
//import androidx.compose.material3.CardColors
//import androidx.compose.material3.CardDefaults
//import androidx.compose.material3.CircularProgressIndicator
//import androidx.compose.material3.Text
//import androidx.compose.material3.TextField
//import androidx.compose.runtime.Composable
//import androidx.compose.runtime.collectAsState
//import androidx.compose.runtime.getValue
//import androidx.compose.ui.Alignment
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.graphics.Color
//import androidx.compose.ui.platform.LocalContext
//import androidx.compose.ui.text.font.FontWeight
//import androidx.compose.ui.tooling.preview.Preview
//import androidx.compose.ui.unit.dp
//import androidx.compose.ui.unit.sp
//import androidx.lifecycle.viewmodel.compose.viewModel
//import com.fitness.elev8fit.R
//import com.fitness.elev8fit.presentation.common.cards
//import com.fitness.elev8fit.presentation.intent.LoginIntent
//import com.fitness.elev8fit.presentation.intent.SignUpIntent
//import com.fitness.elev8fit.presentation.viewmodel.SignUpViewModel
//import com.fitness.elev8fit.ui.theme.bg_color
//import com.fitness.elev8fit.ui.theme.text_color
//import com.google.firebase.auth.FirebaseAuth
//
//@Composable
//fun SignUpScreen(viewModel: SignUpViewModel ){
//
//    val signupstate by viewModel.state.collectAsState()
//    //val activity = LocalContext.current as Activity
//
//
//    Box(modifier = Modifier
//        .fillMaxSize()
//        .background(bg_color)
//        .padding(8.dp),
//    contentAlignment = Alignment.Center){
//
//        Column(modifier = Modifier.fillMaxSize(),
//            horizontalAlignment = Alignment.CenterHorizontally){
//
//            Spacer(modifier = Modifier.height(8.dp))
//
//            // App Title
//            Row(horizontalArrangement = Arrangement.Center) {
//                Text(
//                    text = "Elev",
//                    color = Color.Blue,
//                    fontSize = 24.sp,
//                    fontWeight = FontWeight.Bold
//                )
//                Text(
//                    text = "8Fit",
//                    color = Color.Green,
//                    fontSize = 24.sp,
//                    fontWeight = FontWeight.Bold
//                )
//            }
//
//            Spacer(modifier = Modifier.height(8.dp))
//
//            // Welcome Message
//            Text(
//                text = "Welcome To Elev8Fit",
//                modifier = Modifier.padding(16.dp),
//                fontSize = 24.sp
//            )
//
//            Card(shape = CutCornerShape(8.dp),
//                colors = CardDefaults.cardColors(text_color)) {
//                Box(contentAlignment = Alignment.Center){
//                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
//                        Text(text = "SignUp",
//                            modifier = Modifier.padding(16.dp),
//                            fontSize = 24.sp)
//
//
//                        cards(icons = R.drawable.ic_username, input =signupstate.username ,
//                            label ="Enter user name" ,
//                            labeltext = "User Name",
//                            onValueChange ={
//                           viewModel.SignUpIntentHandler(SignUpIntent.userdetails(it,signupstate.password))
//                        }
//                        )
////                        cards(icons = R.drawable.ic_username, input =signupstate.password ,
////                            label ="Enter password" ,
////                            onValueChange ={
////                                viewModel.SignUpIntentHandler(SignUpIntent.password(it))
////                            }
////                        )
//
//                        cards(icons = R.drawable.ic_username,
//                            input =signupstate.Confirmpassword,
//                            label ="Confirm password" ,
//
////                            onValueChange ={
////                                viewModel.SignUpIntentHandler(SignUpIntent.confirmpass(it))
////                            }
////                        )
//
////                        cards(icons = R.drawable.ic_username, input =signupstate.phonenumber ,
////                            label ="Enter Phonenumber" ,
////                            onValueChange ={
////                                viewModel.SignUpIntentHandler(SignUpIntent.phonenumber(it),activity)
////                            }
////                        )
//
//                        Button(
//                            onClick = { viewModel.SignUpIntentHandler(SignUpIntent.SignInClicked) },
//                            modifier = Modifier
//                                .padding(16.dp)
//                                .fillMaxWidth()
//                        ) {
//                            Text("signin")
//                        }
//
//                        if (signupstate.errorMessage!=null) {
//                            Text(signupstate.errorMessage!!, color = Color.Red, modifier = Modifier.padding(8.dp))
//                        }
//
//                        if(signupstate.isLoading){
//                            CircularProgressIndicator()
//                        }
//
//                        TextField(
//                            value = signupstate.otp,
//                            onValueChange = { viewModel.SignUpIntentHandler(SignUpIntent.otp(it))},
//                            label = { Text("Enter OTP") },
//                            modifier = Modifier.fillMaxWidth()
//                        )
//
//                    }
//                }
//            }
//        }
//    }
//
//
//}