package com.fitness.elev8fit.presentation.common

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.focusable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CutCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.fitness.elev8fit.presentation.viewmodel.imageview
import com.fitness.elev8fit.ui.theme.CustomBackgroundColor
import com.fitness.elev8fit.ui.theme.card_color

@Composable
fun OnBoardingCommon(
    viewModel: imageview,
    cardcontent:String,
    imageresid1:Int,
    imageresid2:Int?,
    imageresid3:Int?,
    text1:String?,
    text2:String?,
    showimage2:Boolean=false,
    showimage3:Boolean=false,
    buttontext:String,
    buttononclick:()-> Unit
) {

    var selectedImage by remember { mutableStateOf(imageresid1) } // Default Android logo
    var bgcolor by remember {
        mutableStateOf(CustomBackgroundColor)
    }
    var nameinputs by remember {
        mutableStateOf("")
    }
    var ageInputString by remember { mutableStateOf(0) }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.secondary)
            .padding(16.dp)
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Image(
                painter = painterResource(id = selectedImage), contentDescription = "",
                modifier = Modifier
                    .border(
                        1.dp,
                        Color.Gray
                    )
                    .width(200.dp)
                    .height(200.dp)
            )
            Card(
                modifier = Modifier.padding(16.dp),
                shape = CardDefaults.shape,
                colors = CardDefaults.cardColors(
                    MaterialTheme.colorScheme.tertiary
                )
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        text = cardcontent,
                        color = MaterialTheme.colorScheme.primary,
                        fontSize = 24.sp,
                    )
                }

            }

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            )
            {



                if (showimage2 && imageresid2 != null) {
                    Column {
                        Image(painter = painterResource(id = imageresid2),
                            contentDescription = "boy",
                            modifier = Modifier
                                .size(150.dp)
                                .border(1.dp, Color.DarkGray, shape = RectangleShape)
                                .clickable {
                                    viewModel.setimg(imageresid2)
                                    selectedImage = imageresid2
                                }
                                .then(
                                    Modifier.background(
                                        if (selectedImage == imageresid2) bgcolor else Color.Transparent // Change background color inside clickable
                                    )
                                )
                                .focusable(enabled = true))
                        if (text1 != null) {
                            Box(modifier = Modifier.align(Alignment.CenterHorizontally)) {
                                Card(
                                    modifier = Modifier.padding(8.dp),
                                    shape = MaterialTheme.shapes.medium,
                                    colors = CardDefaults.cardColors(card_color)
                                ) {
                                    Column(
                                        horizontalAlignment = Alignment.CenterHorizontally,
                                        modifier = Modifier.padding(8.dp)
                                    ) {
                                        Text(
                                            text = text1,
                                            fontSize = 16.sp,
                                            color = CustomBackgroundColor
                                        )
                                    }
                                }
                            }
                        }
                    }

                }
                if (showimage3 && imageresid3 != null) {
                    Column {
                        Image(painter = painterResource(id = imageresid3),
                            contentDescription = "girl",
                            modifier = Modifier
                                .size(150.dp)
                                .border(1.dp, Color.Black, shape = RectangleShape)
                                .clickable {
                                    viewModel.setimg(imageresid3)
                                    selectedImage = imageresid3
                                }
                                .then(
                                    Modifier.background(
                                        if (selectedImage == imageresid3) CustomBackgroundColor else Color.Transparent // Change background color inside clickable
                                    )
                                ))

                        if (text2 != null) {
                            Box(modifier = Modifier.align(Alignment.CenterHorizontally)) {
                                Card(
                                    modifier = Modifier.padding(8.dp),
                                    shape = MaterialTheme.shapes.medium,
                                    colors = CardDefaults.cardColors(MaterialTheme.colorScheme.tertiary)
                                ) {
                                    Column(
                                        horizontalAlignment = Alignment.CenterHorizontally,
                                        modifier = Modifier.padding(8.dp)
                                    ) {
                                        Text(
                                            text = text2,
                                            fontSize = 16.sp,
                                            color = MaterialTheme.colorScheme.primary
                                        )
                                    }
                                }
                            }
                        }
                    }

                }
            }

            Column( modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Bottom) {
                Button(onClick = buttononclick, modifier = Modifier.padding(16.dp),
                    shape = CutCornerShape(8.dp)) {

                    Text(text = buttontext)

                }
            }
        }
    }
}
