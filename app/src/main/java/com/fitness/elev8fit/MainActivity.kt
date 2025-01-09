package com.fitness.elev8fit

import android.os.Build
import android.os.Bundle
import android.widget.Toast
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.biometric.BiometricManager
import androidx.biometric.BiometricManager.Authenticators.BIOMETRIC_STRONG
import androidx.biometric.BiometricPrompt
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.core.content.ContextCompat
import androidx.fragment.app.FragmentActivity
import androidx.navigation.compose.rememberNavController
import com.fitness.elev8fit.data.constant.DataStoreManager
import com.fitness.elev8fit.presentation.navigation.displaynav
import com.fitness.elev8fit.ui.theme.Elev8FitTheme
import com.google.firebase.FirebaseApp
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : androidx.fragment.app.FragmentActivity() {
    private lateinit var biometricPrompt: BiometricPrompt
    private lateinit var promptInfo: BiometricPrompt.PromptInfo
    private val isbioverified = mutableStateOf(false)
    // private val navController: NavController = rememberNavController()

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        FirebaseApp.initializeApp(this)

        val executor = ContextCompat.getMainExecutor(this)
        biometricPrompt = BiometricPrompt(this as FragmentActivity, executor,
            object : BiometricPrompt.AuthenticationCallback() {
                override fun onAuthenticationSucceeded(result: BiometricPrompt.AuthenticationResult) {
                    super.onAuthenticationSucceeded(result)
                    Toast.makeText(this@MainActivity, 
                        "Authentication succeeded!", Toast.LENGTH_SHORT).show()
                    isbioverified.value =true
                    initializeApp()
                }

                override fun onAuthenticationError(errorCode: Int, errString: CharSequence) {
                    super.onAuthenticationError(errorCode, errString)
                    Toast.makeText(this@MainActivity,
                        "Authentication error: $errString", Toast.LENGTH_SHORT).show()
                    // Close the app on authentication error or cancel
                    finish()
                }

                override fun onAuthenticationFailed() {
                    super.onAuthenticationFailed()
                    Toast.makeText(this@MainActivity, 
                        "Authentication failed", Toast.LENGTH_SHORT).show()
                    // Close the app on authentication failure
                    finish()
                }
            })

        promptInfo = BiometricPrompt.PromptInfo.Builder()
            .setTitle("Biometric Authentication")
            .setSubtitle("Log in using your biometric credential")
            .setNegativeButtonText("Cancel")
            .build()

        // Check and request biometric authentication immediately
            setupBiometric()
    }

    private fun initializeApp() {
        setContent {
            val navController = rememberNavController()
            val context = LocalContext.current
            val isAuthenticated = remember { mutableStateOf(false) }
            val isDarkMode = isSystemInDarkTheme()
            val deepLink = remember { mutableStateOf(intent?.data) }

            // Handle deep links for both initial launch and runtime
            LaunchedEffect(Unit) {
                launch {
                    DataStoreManager.getAuthState(context).collect { authState ->
                        isAuthenticated.value = authState
                    }
                }
            }

            Elev8FitTheme(darkTheme = isDarkMode, dynamicColor = false) {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    if(isbioverified.value) {
                        displaynav(
                            navController = navController,
                            isAuthenticated = isAuthenticated.value,
                            deepLink = deepLink.value
                        )

                       // FCMTokenButton()
                    }
                }
            }
        }
    }

    private fun setupBiometric() {
        val biometricManager = BiometricManager.from(this)
        when (biometricManager.canAuthenticate(BIOMETRIC_STRONG)) {
            BiometricManager.BIOMETRIC_SUCCESS -> {
                // Biometric features are available, show prompt
                biometricPrompt.authenticate(promptInfo)
            }
            else -> {
                // If biometric is not available, show error and close app
                Toast.makeText(this, 
                    "Biometric authentication is required to use this app", 
                    Toast.LENGTH_LONG).show()
                finish()
            }
        }
    }


}
