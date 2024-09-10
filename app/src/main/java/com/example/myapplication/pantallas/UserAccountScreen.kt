package com.example.myapplication.pantallas

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.myapplication.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UserAccountScreen(
    navController: NavController,
    username: String,
    viewModel: TextSizeViewModel = viewModel(),
    getUserData: (String) -> User?
) {
    val user = getUserData(username)
    var textSizeMultiplier by remember { mutableFloatStateOf(viewModel.textSizeMultiplier.value) }

    LaunchedEffect(viewModel.textSizeMultiplier.value) {
        textSizeMultiplier = viewModel.textSizeMultiplier.value
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        "Cuenta",
                        fontSize = 18.sp * textSizeMultiplier,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                },
                navigationIcon = {
                    IconButton(onClick = { navController.navigateUp() }) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back", tint = Color.White)
                    }
                },
                actions = {
                    TextSizeIcon(
                        text = "A",
                        onClick = {
                            viewModel.decreaseTextSize()
                            textSizeMultiplier = viewModel.textSizeMultiplier.value
                        },
                        fontSize = 14
                    )
                    TextSizeIcon(
                        text = "A",
                        onClick = {
                            viewModel.increaseTextSize()
                            textSizeMultiplier = viewModel.textSizeMultiplier.value
                        },
                        fontSize = 18
                    )
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    titleContentColor = MaterialTheme.colorScheme.onPrimary
                )
            )
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(16.dp)
        ) {
            user?.let {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .verticalScroll(rememberScrollState()),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_android_black_24dp),
                        contentDescription = "User Avatar",
                        modifier = Modifier.size(120.dp),
                        tint = MaterialTheme.colorScheme.primary
                    )

                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
                    ) {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp),
                            verticalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            UserInfoItem(label = "Name", value = it.name, textSizeMultiplier = textSizeMultiplier)
                            UserInfoItem(label = "Username", value = it.username, textSizeMultiplier = textSizeMultiplier)
                            UserInfoItem(label = "Age", value = it.age.toString(), textSizeMultiplier = textSizeMultiplier)
                        }
                    }

                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
                    ) {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp)
                        ) {
                            Text(
                                text = "Poema",
                                style = MaterialTheme.typography.titleMedium,
                                fontSize = 18.sp * textSizeMultiplier,
                                fontWeight = FontWeight.Bold
                            )
                            Spacer(modifier = Modifier.height(8.dp))
                            Text(
                                text = it.poem,
                                style = MaterialTheme.typography.bodyMedium,
                                fontSize = 16.sp * textSizeMultiplier,
                                lineHeight = 20.sp * textSizeMultiplier
                            )
                        }
                    }

                    Button(
                        onClick = { navController.navigateUp() },
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(
                            "Cerrar sesión",
                            fontSize = 16.sp * textSizeMultiplier,
                            maxLines = 2,
                            overflow = TextOverflow.Ellipsis
                        )
                    }
                }
            } ?: Text("User not found", style = MaterialTheme.typography.headlineMedium, fontSize = 24.sp * textSizeMultiplier)
        }
    }
}

@Composable
fun UserInfoItem(label: String, value: String, textSizeMultiplier: Float) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        Text(
            text = label,
            style = MaterialTheme.typography.bodyLarge,
            fontSize = 16.sp * textSizeMultiplier,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )
        Text(
            text = value,
            style = MaterialTheme.typography.bodyLarge,
            fontWeight = FontWeight.Bold,
            fontSize = 16.sp * textSizeMultiplier,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )
    }
}