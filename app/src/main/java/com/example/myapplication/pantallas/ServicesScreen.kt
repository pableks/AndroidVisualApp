package com.example.myapplication.pantallas

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.lifecycle.viewmodel.compose.viewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ServicesScreen(navController: NavController, username: String, viewModel: TextSizeViewModel = viewModel()) {
    var textSizeMultiplier by remember { mutableFloatStateOf(viewModel.textSizeMultiplier.value) }

    LaunchedEffect(viewModel.textSizeMultiplier.value) {
        textSizeMultiplier = viewModel.textSizeMultiplier.value
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Servicios", fontSize = 18.sp * textSizeMultiplier) },
                navigationIcon = {
                    IconButton(onClick = { navController.navigate("login") }) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
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
                }
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            ServiceButton(
                text = "Cuenta de Usuario",
                onClick = { navController.navigate("userAccount/$username") },
                textSizeMultiplier = textSizeMultiplier
            )
            ServiceButton(
                text = "Voz a Texto",
                onClick = { /* Implement navigation to Voice to Text feature */ },
                textSizeMultiplier = textSizeMultiplier
            )
            ServiceButton(
                text = "Lectura Asistida",
                onClick = { /* Implement navigation to Assisted Reading feature */ },
                textSizeMultiplier = textSizeMultiplier
            )
            ServiceButton(
                text = "Reconocimiento de Objetos",
                onClick = { navController.navigate("objectRecognition") },
                textSizeMultiplier = textSizeMultiplier
            )
        }
    }
}

@Composable
fun ServiceButton(text: String, onClick: () -> Unit, textSizeMultiplier: Float) {
    Button(
        onClick = onClick,
        modifier = Modifier
            .fillMaxWidth()
            .height(80.dp)
    ) {
        Text(
            text = text,
            fontSize = 18.sp * textSizeMultiplier,
            textAlign = TextAlign.Center
        )
    }
}