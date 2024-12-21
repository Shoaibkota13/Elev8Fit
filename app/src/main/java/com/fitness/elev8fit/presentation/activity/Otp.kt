package com.fitness.elev8fit.presentation.activity

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.BasicText
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.fitness.elev8fit.ui.theme.bg_color
import com.fitness.elev8fit.ui.theme.text_color

@Composable
fun OTPVerificationScreen() {
    val otpLength = 6
    val otpFields = remember { mutableStateListOf("", "", "", "","","") }
    val focusRequesters = remember { List(otpLength) { FocusRequester() } }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Verification",
            fontSize = 24.sp,
            color = Color.Black,
            modifier = Modifier.padding(bottom = 8.dp)
        )

        Text(
            text = "We sent a code to hellobesnik@gmail.com",
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

                )
            }
        }

        Button(
            onClick = {
                // Handle OTP submission logic
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 24.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF0078D4))
        ) {
            Text(text = "Continue", color = Color.White)
        }

        Text(
            text = "Resend Code",
            fontSize = 14.sp,
            color = Color(0xFFFF5722),
            modifier = Modifier
                .padding(top = 16.dp)
                .clickable {
                    // Handle Resend OTP logic
                }
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OTPTextField(
    value: String,
    onValueChange: (String) -> Unit,
    focusRequester: FocusRequester
) {
    TextField(
        value = value,
        onValueChange = onValueChange,
        singleLine = true,
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
        textStyle = LocalTextStyle.current.copy(
            textAlign = TextAlign.Center,
            fontSize = 18.sp
        ),
        modifier = Modifier
            .size(56.dp)
            .clip(CircleShape)
            .focusRequester(focusRequester),
        colors = TextFieldDefaults.colors(
            text_color,
            disabledTextColor = Color.Transparent,
            cursorColor = Color.Black,
            errorCursorColor = Color.Red
        )
    )
}

@Preview(showBackground = true)
@Composable
fun PreviewOTPVerificationScreen() {
    OTPVerificationScreen()
}
