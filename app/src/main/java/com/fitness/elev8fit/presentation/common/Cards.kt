package com.fitness.elev8fit.presentation.common

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.fitness.elev8fit.R
import com.fitness.elev8fit.ui.theme.PurpleGrey80
import com.fitness.elev8fit.ui.theme.bg_color
import com.fitness.elev8fit.ui.theme.card_color
import com.fitness.elev8fit.ui.theme.default_text


@Composable
fun cards(
    labeltext: String,
    icons: Int,
    input: String,
    label: String,
    onValueChange: (String) -> Unit
) {
    val textFieldColors = OutlinedTextFieldDefaults.colors(
        unfocusedLabelColor =MaterialTheme.colorScheme.onSecondaryContainer ,
        focusedLabelColor = Color.Black,
        unfocusedPlaceholderColor = Color.Black,
        unfocusedBorderColor = MaterialTheme.colorScheme.onSecondaryContainer,
        focusedBorderColor =Color.Black,
        errorBorderColor = MaterialTheme.colorScheme.error
    )

    Column(
        modifier = Modifier
            .padding(16.dp) // Add outer padding
    ) {
        // Label text
        Text(
            text = labeltext,
            style = MaterialTheme.typography.bodyMedium,
            modifier = Modifier
                .padding(bottom = 8.dp) // Space between label and text field
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
                label={ Text(text = label) },
                modifier = Modifier
                    .fillMaxWidth(),
                colors = textFieldColors,// Make text field take full width
                textStyle =
                TextStyle(color = default_text, fontSize = 16.sp)
                ,leadingIcon = {
                    Icon(
                        painter = painterResource(id = icons),
                        contentDescription = "icon",
                        modifier = Modifier
                            .size(24.dp) // Icon size
                    )
                }
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
