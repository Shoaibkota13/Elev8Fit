package com.fitness.elev8fit.presentation.activity.chat

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChatRoomListScreen(
    viewModel: ChatRoomListViewModel,
    onChatRoomSelected: (String) -> Unit
) {
    val chatRooms by viewModel.chatRooms.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Users") },
                colors = TopAppBarDefaults.topAppBarColors(MaterialTheme.colorScheme.primary),
                navigationIcon = {
                    IconButton(onClick = { }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        }
    ) { innerPadding ->
        LazyColumn(modifier = Modifier.padding(innerPadding)) {
            items(chatRooms) { chatRoom ->
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { onChatRoomSelected(chatRoom.id) }
                        .padding(16.dp)
                ) {
                    Card(modifier = Modifier.height(50.dp).fillMaxWidth().shadow(elevation = 4.dp, shape = RoundedCornerShape(8.dp)),
                        colors = CardDefaults.cardColors(MaterialTheme.colorScheme.tertiary)) {
                        Text(text = chatRoom.name, style = MaterialTheme.typography.bodyLarge, modifier = Modifier.padding(16.dp), color = MaterialTheme.colorScheme.primary)

                    }

                }
            }
        }
    }
}
