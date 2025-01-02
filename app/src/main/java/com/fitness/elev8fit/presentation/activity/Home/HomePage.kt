
package com.fitness.elev8fit.presentation.activity.Home

import android.content.Context
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.Button
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.fitness.elev8fit.domain.model.BottomMenuItems
import com.fitness.elev8fit.presentation.activity.socialLoginSignIn.GoogleSignInViewModel
import com.fitness.elev8fit.presentation.navigation.Navdestination
import com.fitness.elev8fit.ui.theme.bg_color
import com.fitness.elev8fit.ui.theme.card_color




@Composable
fun HomePage(navController: NavController) {
    val bottomMenuItems = preparebottom()
    var selected by remember { mutableStateOf("Home") }
    val context = LocalContext.current

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(bg_color)
    ) {
        // Display content based on the selected item
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(bottom = 56.dp), // Adjust for NavigationBar height
            contentAlignment = Alignment.Center
        ) {
            when (selected) {
                "Home" -> Excercise()
                "Recipe" -> RecipeScreen(navController)
                "Account" -> ProfileScreen(googleSignInViewModel = hiltViewModel(),navController,context)
            }
        }

        // Bottom navigation bar
        NavigationBar(
            modifier = Modifier.align(Alignment.BottomCenter),
            containerColor = card_color,
            contentColor = Color.White
        ) {
            bottomMenuItems.forEach { menuItem ->
                NavigationBarItem(
                    selected = selected == menuItem.label,
                    onClick = { selected = menuItem.label },
                    icon = {
                        Icon(
                            imageVector = menuItem.icon,
                            contentDescription = menuItem.label
                        )
                    },
                    label = { Text(menuItem.label) },
                    alwaysShowLabel = true,
                    colors = NavigationBarItemDefaults.colors(
                        selectedIconColor = Color.Green,
                        unselectedIconColor = Color.Gray,
                        selectedTextColor = Color.Green,
                        unselectedTextColor = Color.Gray
                    )
                )
            }
        }
    }
}


@Composable
fun RecipeScreen(navController: NavController) {
    // Example implementation for the Recipe Screen
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFB2DFDB)), // Light greenish-blue background
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = "Explore Delicious Recipes Here!",
            color = Color.White,
            modifier = Modifier.padding(16.dp)
        )

        FloatingActionButton(onClick = { navController.navigate(Navdestination.recipeentry.toString()) },
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(16.dp, 10.dp, 10.dp, 30.dp), // Padding from the edges
            containerColor = Color.Black
        ) {
            Icon(
                imageVector = Icons.Filled.Add,
                contentDescription = "Add Recipe",
                tint = Color.White
            )
            
        }
    }
}

@Composable
fun ProfileScreen(googleSignInViewModel: GoogleSignInViewModel,navController: NavController,context: Context) {
    // Example implementation for the Profile Screen
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF80DEEA)), // Light cyan background
        contentAlignment = Alignment.Center
    ) {
       Button(onClick = { googleSignInViewModel.logout(context,navController)}) {
           Text(text = "Logout")
       }
    }
}

private fun preparebottom(): List<BottomMenuItems> {
    return listOf(
        BottomMenuItems("Home", Icons.Filled.Home),
        BottomMenuItems("Recipe", Icons.Filled.Add),
        BottomMenuItems("Account", Icons.Filled.AccountCircle)
    )
}

@Preview(showBackground = true)
@Composable
fun HomepagePreview() {
    val navController = rememberNavController()
    HomePage(navController)
}
