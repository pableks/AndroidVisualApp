package com.example.myapplication.pantallas

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController

@Composable
fun HomeScreen(navController: NavController) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Bienvenido a VisualAssist",
            style = MaterialTheme.typography.headlineSmall.copy( // Usa headlineSmall en lugar de h5
                fontSize = 48.sp,  // Aumenta el tamaño del texto si es necesario
                letterSpacing = 0.5.sp,  // Espaciado entre letras
                lineHeight = 48.sp,  // Espaciado entre líneas
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center  // Centra el texto
            ),
            modifier = Modifier.padding(bottom = 16.dp)  // Añade espacio debajo del texto
        )
        Spacer(modifier = Modifier.height(16.dp))
        Button(onClick = { navController.navigate("login") }) {
            Text("Ir a Login")
        }
    }
}
