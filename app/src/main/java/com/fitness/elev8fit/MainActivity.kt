package com.fitness.elev8fit

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.compose.rememberNavController
import com.fitness.elev8fit.data.constant.DataStoreManager
import com.fitness.elev8fit.presentation.activity.Home.ExerciseScreen
import com.fitness.elev8fit.presentation.viewmodel.ExerciseViewModel
import com.fitness.elev8fit.ui.theme.Elev8FitTheme
import com.google.firebase.FirebaseApp
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    // private val navController: NavController = rememberNavController()

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        FirebaseApp.initializeApp(this)

        setContent {
            val navController = rememberNavController()
            val context = LocalContext.current
            val isAuthenticated = remember { mutableStateOf(false) }
            LaunchedEffect(Unit) {
                launch {
                    DataStoreManager.getAuthState(context).collect { authState ->
                        isAuthenticated.value = authState
                    }
                }
            }
            Elev8FitTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
//
//                    displaynav(
//                        navController = navController,
//                        isAuthenticated = isAuthenticated.value
//                    )

                    ExerciseScreen(ExerciseViewModel())

                 //  RecipeScreen(recipeScreenViewModel = hiltViewModel(), navController =navController )
//                    LoginFacebookButton(
//                        onAuthComplete = {
//                            Toast.makeText(this, "Login Successful", Toast.LENGTH_LONG).show()
//                        },
//                        onAuthError = {
//                            Toast.makeText(this, "Login Failed: ", Toast.LENGTH_LONG).show()
//                        }
//                    )
              //      ExerciseCard()

                }
            }
        }
    }
}


