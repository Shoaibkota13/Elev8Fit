package com.fitness.elev8fit.presentation.activity.onboarding

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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringArrayResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.fitness.elev8fit.R
import com.fitness.elev8fit.data.constant.DataStoreManager
import com.fitness.elev8fit.presentation.navigation.Navdestination
import com.fitness.elev8fit.ui.theme.CustomBackgroundColor
import com.fitness.elev8fit.ui.theme.bg_color
import com.fitness.elev8fit.ui.theme.quicksand
import com.fitness.elev8fit.utils.setLocaleForApp
import kotlinx.coroutines.launch

@Composable

//@Composable
fun Firstonboard(navController: NavController) {
    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()
    val selectedLanguage = remember { mutableStateOf("en") }
    val showLanguageDialog = remember { mutableStateOf(false) }
    val isLoading = remember { mutableStateOf(false) } // State for progress indicator
    val recomposeKey = remember { mutableStateOf(0) } // Key to trigger recomposition

    // Generate the random quote only once when the composable is first created
    val quotes = stringArrayResource(id = R.array.quotes)
    val randomQuote = remember(recomposeKey.value) { quotes.random() }

    // Retrieve the saved language preference from DataStore
    LaunchedEffect(Unit) {
        coroutineScope.launch {
            DataStoreManager.getLanguagePreference(context).collect { language ->
                selectedLanguage.value = language
            }
        }
    }

    if (isLoading.value) {
        // Show circular progress while recomposing
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background),
            contentAlignment = Alignment.Center
        ) {
            androidx.compose.material3.CircularProgressIndicator(
                color = MaterialTheme.colorScheme.primary
            )
        }
    } else {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(bg_color)
                .padding(16.dp)
        ) {
            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // App name header
                Row(horizontalArrangement = Arrangement.Center) {
                    Text(
                        text = stringResource(id = R.string.app_names),
                        color = Color.Blue,
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = stringResource(id = R.string.app_na),
                        color = Color.Green,
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold
                    )
                }

                Spacer(modifier = Modifier.height(8.dp))

                // Subtitle
                Text(
                    text = stringResource(id = R.string.health),
                    fontFamily = quicksand,
                    fontWeight = FontWeight.Bold,
                    fontSize = 24.sp,
                    color = MaterialTheme.colorScheme.primary
                )

                // Logo
                Image(
                    painter = painterResource(id = R.drawable.logo),
                    contentDescription = "Logo"
                )

                Spacer(modifier = Modifier.height(16.dp))

                // Quotes card
                Card(
                    modifier = Modifier.padding(16.dp),
                    shape = CardDefaults.shape,
                    colors = CardDefaults.cardColors(MaterialTheme.colorScheme.tertiary)
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text(
                            text = randomQuote,
                            fontSize = 24.sp,
                            fontFamily = quicksand,
                            fontWeight = FontWeight.Black,
                            color = CustomBackgroundColor,
                            textAlign = TextAlign.Justify
                        )
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Navigation to login
                Text(
                    text = stringResource(id = R.string.Accntpresent),
                    modifier = Modifier.clickable {
                        navController.navigate(Navdestination.login.toString())
                    },
                    fontFamily = quicksand,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.primary
                )
            }

            // Language selector and onboarding button are moved into a Box for proper alignment
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp)
            ) {
                // Language selector icon
                Icon(
                    imageVector = Icons.Default.Settings,
                    contentDescription = "Select Language",
                    modifier = Modifier
                        .align(Alignment.TopEnd) // Correct alignment in BoxScope
                        .clickable {
                            showLanguageDialog.value = true
                        },
                    tint = MaterialTheme.colorScheme.primary
                )

                // Onboarding navigation button
                Button(
                    onClick = {
                        navController.navigate(Navdestination.onboarding2.toString())
                    },
                    modifier = Modifier
                        .align(Alignment.BottomCenter) // Correct alignment in BoxScope
                        .padding(16.dp),
                    shape = CutCornerShape(8.dp),
                    colors = ButtonDefaults.buttonColors(MaterialTheme.colorScheme.primary)
                ) {
                    Text(
                        text = stringResource(id = R.string.let_s_start),
                        fontSize = 24.sp,
                        color = Color.White
                    )
                }
            }

            // Language selection dialog
            if (showLanguageDialog.value) {
                AlertDialog(
                    onDismissRequest = { showLanguageDialog.value = false },
                    title = {
                        Text(text = stringResource(id = R.string.select_language))
                    },
                    text = {
                        Column {
                            listOf("en" to "English", "hi" to "Hindi").forEach { (code, name) ->
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .clickable {
                                            selectedLanguage.value = code
                                            isLoading.value = true
                                            coroutineScope.launch {
                                                DataStoreManager.saveLanguagePreference(context, code)
                                                setLocaleForApp(context, code)
                                                recomposeKey.value++ // Trigger recomposition
                                                isLoading.value = false
                                            }
                                            showLanguageDialog.value = false
                                        }
                                        .padding(8.dp)
                                ) {
                                    RadioButton(
                                        selected = selectedLanguage.value == code,
                                        onClick = {
                                            selectedLanguage.value = code
                                            isLoading.value = true
                                            coroutineScope.launch {
                                                DataStoreManager.saveLanguagePreference(context, code)
                                                setLocaleForApp(context, code)
                                                recomposeKey.value++ // Trigger recomposition
                                                isLoading.value = false
                                            }
                                            showLanguageDialog.value = false
                                        }
                                    )
                                    Spacer(modifier = Modifier.width(8.dp))
                                    Text(text = name)
                                }
                            }
                        }
                    },
                    confirmButton = {
                        TextButton(onClick = { showLanguageDialog.value = false }) {
                            Text(text = "Close")
                        }
                    }
                )
            }
        }
    }
}



@Preview(showBackground = true)
@Composable
fun OnboardingPreview() {
    val navController = rememberNavController()
    Firstonboard(navController = navController)
}

