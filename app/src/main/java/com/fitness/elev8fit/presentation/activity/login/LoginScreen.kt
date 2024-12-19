package com.fitness.elev8fit.presentation.activity.login

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.fitness.elev8fit.presentation.viewmodel.LoginViewModel
import com.fitness.elev8fit.ui.theme.bg_color


@Composable
fun LoginScreen(viewModel: LoginViewModel = androidx.lifecycle.viewmodel.compose.viewModel()){

    val state by viewModel.state.collectAsState()

    Box(modifier = Modifier
        .fillMaxSize()
        .background(bg_color)){

        Column() {

        }
    }

}

@Preview(showBackground = true)
@Composable
fun PreviewLoginScreen() {
    // Mock ViewModel or pass no viewModel if the screen can be previewed without dependencies
    LoginScreen()
}