package com.fitness.elev8fit.presentation.common

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.fitness.elev8fit.R
import com.fitness.elev8fit.ui.theme.default_text


@Composable
fun cards(
    labeltext: String,
    icons: Int,
    input: String,
    label: String,
    onValueChange: (String) -> Unit,
    isPassword: Boolean = false, // Add a flag to determine if it's a password field
    keyboardType: KeyboardType = KeyboardType.Text
) {
    val textFieldColors = OutlinedTextFieldDefaults.colors(
        unfocusedLabelColor = MaterialTheme.colorScheme.onSecondaryContainer,
        focusedLabelColor = Color.Black,
        unfocusedPlaceholderColor = Color.Black,
        unfocusedBorderColor = MaterialTheme.colorScheme.onSecondaryContainer,
        focusedBorderColor = Color.Black,
        errorBorderColor = MaterialTheme.colorScheme.error
    )

    val visualTransformation: VisualTransformation =
        if (isPassword) PasswordVisualTransformation() else VisualTransformation.None

    Column(
        modifier = Modifier.padding(horizontal = 16.dp) // Adjust padding to only horizontal
    ) {
        // Label text
        Text(
            text = labeltext,
            style = MaterialTheme.typography.bodyMedium
        )

        // TextField with Icon
        Row(
            modifier = Modifier
                .fillMaxWidth() // Make Row take full width
                .height(60.dp), // Set consistent height
            verticalAlignment = Alignment.CenterVertically
        ) {
            OutlinedTextField(
                value = input,
                onValueChange = onValueChange,
                label = { Text(text = label) },
                singleLine = true,
                modifier = Modifier.fillMaxWidth(), // Make text field take full width
                colors = textFieldColors, // Use custom colors
                textStyle = TextStyle(color = default_text, fontSize = 16.sp),
                keyboardOptions = KeyboardOptions.Default.copy(keyboardType = keyboardType),
                leadingIcon = {
                    Icon(
                        painter = painterResource(id = icons),
                        contentDescription = "icon",
                        modifier = Modifier.size(24.dp) // Icon size
                    )
                },
                visualTransformation = visualTransformation // Apply visual transformation based on isPassword flag
            )
        }
    }
}


@Preview(showBackground = true)
@Composable
fun CardPreviewView() {
    cards(
        labeltext = "Username",
        icons = R.drawable.ic_username,
        input = "",
        onValueChange = { },
        label = "Enter user name"
    )
}



