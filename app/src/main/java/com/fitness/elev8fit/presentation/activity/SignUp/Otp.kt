package com.fitness.elev8fit.presentation.activity.SignUp

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.key.Key
import androidx.compose.ui.input.key.key
import androidx.compose.ui.input.key.onKeyEvent
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.fitness.elev8fit.presentation.navigation.Navdestination
import com.fitness.elev8fit.ui.theme.card_color
import kotlinx.coroutines.delay

@Composable
fun OTPVerificationScreen(viewModel: SignUpViewModel,navController: NavController) {
    val verificationId = navController.previousBackStackEntry?.savedStateHandle?.get<String>("verificationId")
    val otpLength = 6
    val otpFields = remember { mutableStateListOf("", "", "", "", "", "") }
    val focusRequesters = remember { List(otpLength) { FocusRequester() } }
    val context = LocalContext.current
    val timerState = remember { mutableStateOf(30) }

    // LaunchedEffect to run a countdown timer for the "Resend Code" functionality
    LaunchedEffect(Unit) {
        while (timerState.value > 0) {
            delay(1000) // Wait for 1 second
            timerState.value -= 1
        }
    }
    LaunchedEffect(Unit) {
        focusRequesters[0].requestFocus()
    }


    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(card_color).padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Text(
            text = "Verification",
            fontSize = 24.sp,
            color = Color.Black,
            modifier = Modifier.padding(bottom = 8.dp)
        )

        Text(
            text = "We sent a code to  ",
            fontSize = 14.sp,
            color = Color.Gray,
            modifier = Modifier.padding(bottom = 24.dp)
        )

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentWidth(Alignment.CenterHorizontally),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            for (index in 0 until otpLength) {
                OTPTextField(
                    value = otpFields[index],
                    onValueChange = { newValue ->
                        if (newValue.length <= 1) {
                            otpFields[index] = newValue
                            if (newValue.isNotEmpty() && index < otpLength - 1) {
                                focusRequesters[index + 1].requestFocus()
                            }
                        }
                    },
                    focusRequester = focusRequesters[index],
                    onBackPressed = {
                        // Move focus to previous field if current OTP field is empty
                        if (index > 0) {
                            focusRequesters[index - 1].requestFocus()
                        }
                    }
                )
            }
        }

        Button(
            onClick = {
                val otp = otpFields.joinToString("")
                if (verificationId != null && otp.length == otpLength) {
                    viewModel.verifyOtp(context, verificationId, otp) { success ->
                        if (success) {
                            navController.navigate(Navdestination.home.toString())
                        } else {
                            Toast.makeText(context, "Invalid OTP", Toast.LENGTH_SHORT).show()
                        }
                    }
                } else {
                    Toast.makeText(context, "Please enter all OTP digits", Toast.LENGTH_SHORT).show()
                }

            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 24.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF0078D4))
        ) {
            Text(text = "Verify Otp", color = Color.White)
        }

        Text(
            text = if (timerState.value > 0) {
                "Resend Code in ${timerState.value}s"
            } else {
                "Resend Code"
            },
            fontSize = 14.sp,
            color = if (timerState.value > 0) Color.Gray else Color(0xFFFF5722),
            modifier = Modifier
                .padding(top = 16.dp)
                .clickable(enabled = timerState.value == 0) {
                    // Handle Resend OTP logic
                    // Reset timer to 30 seconds
                    timerState.value = 30
                }
        )
    }
}

@Composable
fun OTPTextField(
    value: String,
    onValueChange: (String) -> Unit,
    focusRequester: FocusRequester,
    onBackPressed: () -> Unit
) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        singleLine = true,
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
        textStyle = LocalTextStyle.current.copy(
            textAlign = TextAlign.Center,
            fontSize = 18.sp
        ),
        modifier = Modifier
            .size(60.dp)
            .clip(RoundedCornerShape(8.dp))
            .padding(2.dp)
            .focusRequester(focusRequester)
            .onKeyEvent { keyEvent ->

                if (keyEvent.key == Key.Backspace && value.isEmpty()) {
                    onBackPressed()
                    true // consume the event
                } else {
                    false
                }
            },
        colors = TextFieldDefaults.colors(
            focusedIndicatorColor = Color(0xFF0078D4),
            unfocusedIndicatorColor = Color.Gray,
            focusedContainerColor = Color.White,
            unfocusedContainerColor = Color.White
        )
    )
}


//@Preview(showBackground = true)
//@Composable
//fun PreviewOTPVerificationScreen() {
//    OTPVerificationScreen()
//}


