package com.example.myapplication.pantallas

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RecoverPasswordScreen(navController: NavController) {
    var email by remember { mutableStateOf("") }

    Column(
        modifier = Modifier.fillMaxSize().padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            "Recuperar Contraseña",
            style = MaterialTheme.typography.bodyMedium.copy(
                fontSize = 48.sp,
                lineHeight = 48.sp
            ),
            overflow = TextOverflow.Ellipsis,
            maxLines = 2
        )
        Spacer(modifier = Modifier.height(16.dp))
        TextField(
            value = email,
            onValueChange = { email = it },
            label = { Text("Email",
                style = MaterialTheme.typography.bodyMedium.copy(
                    fontSize = 16.sp ,
                    lineHeight = 20.sp
            ),) },
            modifier = Modifier.padding(vertical = 8.dp)
        )
        Spacer(modifier = Modifier.height(16.dp))
        Button(onClick = { /* Implement password recovery logic */ }) {
            Text("Enviar correo de recuperación")
        }
        Spacer(modifier = Modifier.height(8.dp))
        TextButton(onClick = { navController.navigateUp() }) {
            Text("Regresa al Login")
        }
    }
}