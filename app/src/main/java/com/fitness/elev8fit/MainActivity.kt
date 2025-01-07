

package com.fitness.elev8fit

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.compose.foundation.isSystemInDarkTheme
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
import com.fitness.elev8fit.presentation.navigation.displaynav
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
            val isDarkMode = isSystemInDarkTheme()
            LaunchedEffect(Unit) {
                launch {
                    DataStoreManager.getAuthState(context).collect { authState ->
                        isAuthenticated.value = authState
                    }
                }
            }


            Elev8FitTheme(darkTheme = isDarkMode,dynamicColor = false) {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    displaynav(navController = navController, isAuthenticated = isAuthenticated.value )
                  //  ChatScreen(viewModel = hiltViewModel(), onBackClick = {})

                }
            }

        }
    }
}








//@Composable
//fun kt(){
//    LazyColumn(){
//        items(10){
//            Box(
//                modifier = Modifier
//                    .fillMaxWidth()
//                    .height(100.dp) // Set height for each shimmer item
//                    .padding(8.dp).background(Color.Gray)
//                    .shimmer() // Apply shimmer effect
//            )
//        }
//    }
//
//}
//








//@Composable
//fun kt(){
//    LazyColumn(){
//        items(10){
//            Box(
//                modifier = Modifier
//                    .fillMaxWidth()
//                    .height(100.dp) // Set height for each shimmer item
//                    .padding(8.dp).background(Color.Gray)
//                    .shimmer() // Apply shimmer effect
//            )
//        }
//    }
//
//}
//

