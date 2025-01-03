
package com.fitness.elev8fit.presentation.activity.Profile

import android.content.Context
import android.util.Log
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.fitness.elev8fit.R
import com.fitness.elev8fit.presentation.common.commonviewmodel
import com.fitness.elev8fit.presentation.navigation.Navdestination

@Composable
fun ProfileScreen(
    profileViewModel: ProfileViewModel,navController: NavController, common: commonviewmodel,context: Context
) {

    val user by profileViewModel.user.observeAsState()

    LaunchedEffect(Unit) {
        profileViewModel.fetchCurrentUser()
    }

    Box(modifier = Modifier.fillMaxSize()) {


        IconButton(
            onClick = { navController.navigate(Navdestination.home.toString()){
                popUpTo(Navdestination.home.toString()) { inclusive = true}
            } },
            modifier = Modifier
                .align(Alignment.TopStart)
                .padding(top = 32.dp, start = 8.dp)
        ) {
            Icon(
                imageVector = Icons.Filled.KeyboardArrowLeft,
                tint = Color.White,
                contentDescription = "Arrow Back",
                modifier = Modifier.size(35.dp)
            )
        }



        Card(
            modifier = Modifier
                .padding(
                    start = 16.dp,
                    end = 16.dp,
                    top = 86.dp
                )
                .fillMaxWidth()
                .shadow(elevation = 4.dp, shape = RoundedCornerShape(8.dp)),
            colors = CardDefaults.cardColors(
                containerColor = Color.White,
            ),
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(20.dp),
                modifier = Modifier.padding(vertical = 24.dp),
            ) {
                Text(
                    modifier = Modifier,
                    text = "Profile",
                    style = TextStyle(
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.Black
                    )
                )
                // User Profile Picture
                Box {


                    Box(
                        modifier = Modifier
                            .size(120.dp)
                            .clip(CircleShape)
                    ) {

                        AsyncImage(
                            model = user?.photoUrl,
                            contentDescription = "Avatar",
                            placeholder = painterResource(R.drawable.user),
                            modifier = Modifier.fillMaxSize(),
                            contentScale = ContentScale.Crop
                        )
                        Log.d("Profile", "User photoUrl: ${user?.photoUrl}")
                    }

                    Box(
                        modifier = Modifier
                            .wrapContentSize()
                            .size(34.dp)
                            .offset(y = (-4).dp)
                            .clip(CircleShape)
                            .background(
                                brush = Brush.verticalGradient(
                                    colors = listOf(
                                        Color(0xFFF953C6).copy(alpha = 0.8f),
                                        Color(0xFFB91D73).copy(alpha = 0.8f)
                                    )
                                )
                            )
                            .align(Alignment.BottomEnd)
                            .padding(8.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Edit,
                            contentDescription = "Edit Image",
                            tint = Color.White
                        )
                    }
                }

                // User Details
                user?.let {
                    ProfileItem(
                        title = "Name",
                        subtitle = it.name ?: "No name",
                        onEdit = { newValue -> profileViewModel.updateSingleField("name", newValue) }
                    )
                    ProfileItem(
                        title = "Email",
                        subtitle = it.email ?: "No email",
                        onEdit = { newValue -> profileViewModel.updateSingleField("email", newValue) }
                    )
                    ProfileItem(
                        title = "Mobile",
                        subtitle = it.mobile ?: "No mobile",
                        onEdit = { newValue -> profileViewModel.updateSingleField("mobile", newValue) }
                    )
                    ProfileItem(
                        title = "Age",
                        subtitle = it.age ?: "No age",
                        onEdit = { newValue -> profileViewModel.updateSingleField("age", newValue) }
                    )
                }

                // Logout Button
                Button(
                    onClick = { common.logout(context,navController)
                              },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(45.dp)
                        .padding(horizontal = 16.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color.White
                    ),
                    shape = RoundedCornerShape(8.dp),
                    border = BorderStroke(
                        width = 1.5.dp,
                        brush = Brush.linearGradient(
                            colors = listOf(
                                Color(0xFFF953C6),
                                Color(0xFFB91D73)
                            ),
                            start = Offset.Zero,
                            end = Offset.Infinite
                        ),
                    ),
                    contentPadding = PaddingValues(0.dp),
                ) {
                    Text(
                        text = "Log out",
                        textAlign = TextAlign.Center,
                        style = TextStyle(
                            fontSize = 17.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFFB91D73),
                        )
                    )
                }
            }
        }
    }
}


//@Composable
//fun ProfileItem(
//    title: String,
//    subtitle: String,
//    onEdit: (String) -> Unit
//) {
//    var isEditing by remember { mutableStateOf(false) }
//    var text by remember { mutableStateOf(subtitle) }
//
//    Column(
//        modifier = Modifier
//            .padding(horizontal = 16.dp)
//            .fillMaxWidth()
//    ) {
//        Text(
//            text = title,
//            style = TextStyle(
//                fontSize = 18.sp,
//                color = Color.Green
//            )
//        )
//        Row {
//            if (isEditing) {
//                TextField(
//                    value = text,
//                    onValueChange = { text = it },
//                    modifier = Modifier.fillMaxWidth(),
//                    label = { Text(title) },
//                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone)
//                )
//                IconButton(
//                    onClick = {
//                        onEdit(text) // Save changes
//                        isEditing = false
//                    }
//                ) {
//                    Icon(imageVector = Icons.Default.Check, contentDescription = "Save Changes", modifier = Modifier.size(15.dp),tint = Color.White)
//                }
//            } else {
//                Text(
//                    text = text,
//                    modifier = Modifier
//                        .padding(end = 4.dp)
//                        .align(Alignment.CenterVertically),
//                    style = TextStyle(
//                        fontSize = 14.sp,
//                        color = Color(0xFFA5A5A5)
//                    )
//                )
//                IconButton(
//                    onClick = { isEditing = true }
//                ) {
//                    Icon(
//                        imageVector = Icons.Default.Edit,
//                        contentDescription = null,
//                        modifier = Modifier.size(16.dp),
//                        tint = Color.Green
//                    )
//                }
//            }
//        }
//    }
//}



@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileItem(
    title: String,
    subtitle: String,
    onEdit: (String) -> Unit
) {
    var isEditing by remember { mutableStateOf(false) }
    var text by remember { mutableStateOf(subtitle) }

    Column(
        modifier = Modifier
            .padding(horizontal = 16.dp)
            .fillMaxWidth()
    ) {
        Text(
            text = title,
            style = TextStyle(
                fontSize = 18.sp,
                color = Color.Green
            )
        )
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            if (isEditing) {
                // Reduced size of TextField
                TextField(
                    value = text,
                    onValueChange = { text = it },
                    modifier = Modifier
                        .wrapContentWidth()
                        ,  // Smaller height
                    label = { Text(title) },
                    colors = TextFieldDefaults.colors(
                        focusedContainerColor = Color.White,
                        unfocusedContainerColor = Color.White,
                        focusedTextColor = Color.Black, // Change the text color when the field is focused
                        unfocusedTextColor = Color.Gray, // Optional: Change the text color when unfocused
                        focusedIndicatorColor = Color.Black, // Optional: Customize the indicator color when focused
                        unfocusedIndicatorColor = Color.Gray // Optional: Customize the indicator color when unfocused
                    ),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone)
                )

                // Save (Check) Icon
                IconButton(
                    onClick = {
                        onEdit(text) // Save changes
                        isEditing = false
                    },
                    modifier = Modifier
                        .padding(start = 8.dp)
                        // Background to make icon visible
                ) {
                    Icon(
                        imageVector = Icons.Default.Check,
                        contentDescription = "Save Changes",
                        tint = Color.Green // Ensure the icon is visible
                    )
                }
            } else {
                Text(
                    text = text,
                    modifier = Modifier
                        .padding(end = 4.dp)
                        .align(Alignment.CenterVertically),
                    style = TextStyle(
                        fontSize = 14.sp,
                        color = Color(0xFFA5A5A5)
                    )
                )
                // Edit Icon
                IconButton(
                    onClick = { isEditing = true },
                    modifier = Modifier
                        .padding(start = 8.dp)
                        .size(24.dp) // Same size as the Check icon
                ) {
                    Icon(
                        imageVector = Icons.Default.Edit,
                        contentDescription = null,
                        tint = Color.Green // Keep icon color consistent
                    )
                }
            }
        }
    }
}
