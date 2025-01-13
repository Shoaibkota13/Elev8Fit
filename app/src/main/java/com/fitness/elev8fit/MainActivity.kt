package com.fitness.elev8fit

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.widget.Toast
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.biometric.BiometricManager
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
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.lifecycleScope
import androidx.navigation.compose.rememberNavController
import com.fitness.elev8fit.data.constant.DataStoreManager
import com.fitness.elev8fit.presentation.activity.splash.SplashScreen
import com.fitness.elev8fit.presentation.navigation.displaynav
import com.fitness.elev8fit.ui.theme.Elev8FitTheme
import com.fitness.elev8fit.utils.setLocaleForApp
import com.google.firebase.FirebaseApp
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : FragmentActivity() {
   // private val languageCode = mutableStateOf("en")
    private lateinit var biometricPrompt: BiometricPrompt
    private lateinit var promptInfo: BiometricPrompt.PromptInfo
    private val isBioVerified = mutableStateOf(false)

    companion object {
        const val NOTIFICATION_PERMISSION_REQUEST_CODE = 123
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        FirebaseApp.initializeApp(this)
        val context = this
        val coroutineScope = lifecycleScope
        coroutineScope.launch {
            DataStoreManager.getLanguagePreference(context).collect { languageCode ->
                setLocaleForApp(context, languageCode) // Set the locale
            }
        }

       // LocaleManager.setLocale(this,languageCode.value)
        val chatroomId = intent.getStringExtra("chatroomId")

        // Setup biometric authentication
        setupBiometricPrompt()

        setContent {
            Elev8FitTheme(darkTheme = isSystemInDarkTheme(), dynamicColor = false) {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    if (!isBioVerified.value) {
                        SplashScreen() // Display splash screen while biometric is being verified
                        setupBiometric()
                    } else {
                        // Main app content after successful biometric authentication
                        val navController = rememberNavController()
                        val context = LocalContext.current
                        val isAuthenticated = remember { mutableStateOf(false) }
                        val deepLink = remember { mutableStateOf(intent?.data) }

                        LaunchedEffect(Unit) {
                            launch {
                                DataStoreManager.getAuthState(context).collect { authState ->
                                    isAuthenticated.value = authState
                                }
                            }
                        }

                        displaynav(
                            navController = navController,
                            isAuthenticated = isAuthenticated.value,
                            deepLink = deepLink.value,
                            chatid = chatroomId
                        )
                    }
                }
            }
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == NOTIFICATION_PERMISSION_REQUEST_CODE) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
             ///   Toast.makeText(this, "Notification permission granted", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(
                    this,
                    "Notification permission denied. Some features may not work.",
                    Toast.LENGTH_LONG
                ).show()
                finish()
            }
        }
    }

    private fun setupBiometricPrompt() {
        val executor = ContextCompat.getMainExecutor(this)
        biometricPrompt = BiometricPrompt(this, executor,
            object : BiometricPrompt.AuthenticationCallback() {
                override fun onAuthenticationSucceeded(result: BiometricPrompt.AuthenticationResult) {
                    super.onAuthenticationSucceeded(result)
                    isBioVerified.value = true
                    requestNotificationPermissionIfNeeded()
                }

                override fun onAuthenticationError(errorCode: Int, errString: CharSequence) {
                    super.onAuthenticationError(errorCode, errString)
                    Toast.makeText(
                        this@MainActivity,
                        "Biometric authentication error: $errString",
                        Toast.LENGTH_SHORT
                    ).show()
                    finish()
                }

                override fun onAuthenticationFailed() {
                    super.onAuthenticationFailed()
                    Toast.makeText(this@MainActivity, "Authentication failed", Toast.LENGTH_SHORT)
                        .show()
                    finish()
                }
            })

        promptInfo = BiometricPrompt.PromptInfo.Builder()
            .setTitle("Biometric Authentication")
            .setSubtitle("Log in using your biometric credential")
            .setNegativeButtonText("Cancel")
            .build()
    }

//    private fun setupBiometric() {
//        val biometricManager = BiometricManager.from(this)
//        val biometricAuthenticator = BiometricManager.Authenticators.BIOMETRIC_STRONG or BiometricManager.Authenticators.DEVICE_CREDENTIAL
//        when (biometricManager.canAuthenticate(biometricAuthenticator)) {
//            BiometricManager.BIOMETRIC_SUCCESS -> {
//                biometricPrompt.authenticate(promptInfo)
//            }
//            BiometricManager.BIOMETRIC_ERROR_NO_HARDWARE -> {
//                // No biometric hardware available on the device
//                Toast.makeText(this, "No biometric hardware detected. Using device credentials.", Toast.LENGTH_LONG).show()
//                promptDeviceCredential()
//            }
//            BiometricManager.BIOMETRIC_ERROR_HW_UNAVAILABLE -> {
//                // Biometric hardware is currently unavailable (e.g., in use by another app)
//                Toast.makeText(this, "Biometric hardware unavailable. Using device credentials.", Toast.LENGTH_LONG).show()
//                promptDeviceCredential()
//            }
//            BiometricManager.BIOMETRIC_ERROR_NONE_ENROLLED -> {
//                // Biometric authentication not enrolled (user has not set up biometrics)
//                Toast.makeText(this, "No biometrics enrolled. Using device credentials.", Toast.LENGTH_LONG).show()
//                promptDeviceCredential()
//            }
//            else -> {
//                Toast.makeText(
//                    this,
//                    "Biometric authentication is required to use this app",
//                    Toast.LENGTH_LONG
//                ).show()
//                finish()
//            }
//        }
//    }

    private fun setupBiometric() {
        val biometricManager = BiometricManager.from(this)
        val biometricAuthenticator = BiometricManager.Authenticators.BIOMETRIC_STRONG or BiometricManager.Authenticators.DEVICE_CREDENTIAL

        when (biometricManager.canAuthenticate(biometricAuthenticator)) {
            BiometricManager.BIOMETRIC_SUCCESS -> {
                // Biometric authentication is available and can be used
                biometricPrompt.authenticate(promptInfo)
            }
            BiometricManager.BIOMETRIC_ERROR_NO_HARDWARE -> {
                // No biometric hardware available on the device
                Toast.makeText(this, "No biometric hardware detected. Using device credentials.", Toast.LENGTH_LONG).show()
                promptDeviceCredential()
            }
            BiometricManager.BIOMETRIC_ERROR_HW_UNAVAILABLE -> {
                // Biometric hardware is currently unavailable (e.g., in use by another app)
                Toast.makeText(this, "Biometric hardware unavailable. Using device credentials.", Toast.LENGTH_LONG).show()
                promptDeviceCredential()
            }
            BiometricManager.BIOMETRIC_ERROR_NONE_ENROLLED -> {
                // No biometrics enrolled
                Toast.makeText(this, "No biometrics enrolled. Using device credentials.", Toast.LENGTH_LONG).show()
                promptDeviceCredential()
            }
            else -> {
                // Other error cases
                Toast.makeText(this, "Error occurred with biometric authentication.", Toast.LENGTH_LONG).show()
            }
        }
    }


//    private fun promptDeviceCredential() {
//        // This will prompt the user for device credentials (PIN, pattern, or password)
//        val biometricPrompt = BiometricPrompt(this, ContextCompat.getMainExecutor(this), object : BiometricPrompt.AuthenticationCallback() {
//            override fun onAuthenticationSucceeded(result: BiometricPrompt.AuthenticationResult) {
//                super.onAuthenticationSucceeded(result)
//                isBioVerified.value = true
//                requestNotificationPermissionIfNeeded()
//            }
//
//            override fun onAuthenticationError(errorCode: Int, errString: CharSequence) {
//                super.onAuthenticationError(errorCode, errString)
//                Toast.makeText(this@MainActivity, "Authentication error: $errString", Toast.LENGTH_SHORT).show()
//                finish()
//            }
//
//            override fun onAuthenticationFailed() {
//                super.onAuthenticationFailed()
//                Toast.makeText(this@MainActivity, "Authentication failed", Toast.LENGTH_SHORT).show()
//                finish()
//            }
//        })
//
//        val promptInfo = BiometricPrompt.PromptInfo.Builder()
//            .setTitle("Device Credential Authentication")
//            .setSubtitle("Please authenticate using your device credentials (PIN, pattern, or password).")
//            .setNegativeButtonText("Cancel")
//            .build()
//
//        biometricPrompt.authenticate(promptInfo)
//    }
private fun promptDeviceCredential() {
    // This will prompt the user for device credentials (PIN, pattern, or password)
    val biometricPrompt = BiometricPrompt(this, ContextCompat.getMainExecutor(this), object : BiometricPrompt.AuthenticationCallback() {
        override fun onAuthenticationSucceeded(result: BiometricPrompt.AuthenticationResult) {
            super.onAuthenticationSucceeded(result)
            isBioVerified.value = true
            requestNotificationPermissionIfNeeded()
        }

        override fun onAuthenticationError(errorCode: Int, errString: CharSequence) {
            super.onAuthenticationError(errorCode, errString)
            // Show the error message but don't finish the app
            Toast.makeText(this@MainActivity, "Authentication error: $errString", Toast.LENGTH_SHORT).show()
        }

        override fun onAuthenticationFailed() {
            super.onAuthenticationFailed()
            // Show failure message but don't finish the app
            Toast.makeText(this@MainActivity, "Authentication failed", Toast.LENGTH_SHORT).show()
        }
    })

    val promptInfo = BiometricPrompt.PromptInfo.Builder()
        .setTitle("Device Credential Authentication")
        .setSubtitle("Please authenticate using your device credentials (PIN, pattern, or password).")
        .setNegativeButtonText("Cancel")
        .build()

    biometricPrompt.authenticate(promptInfo)
}

    private fun requestNotificationPermissionIfNeeded() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (ContextCompat.checkSelfPermission(
                    this,
                    Manifest.permission.POST_NOTIFICATIONS
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                ActivityCompat.requestPermissions(
                    this,
                    arrayOf(Manifest.permission.POST_NOTIFICATIONS),
                    NOTIFICATION_PERMISSION_REQUEST_CODE
                )
            }
        }
    }
}
