package com.example.myapplication.pantallas

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.myapplication.R
import androidx.compose.ui.text.input.PasswordVisualTransformation


data class User(val username: String, val password: String, val name: String, val age: Int)

val mockUsers = listOf(
    User("user1", "pass1", "John Doe", 30),
    User("user2", "pass2", "Jane Smith", 25),
    User("user3", "pass3", "Bob Johnson", 35)
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginScreen(navController: NavController) {
    var username by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var errorMessage by remember { mutableStateOf("") }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Login") },
                navigationIcon = {
                    IconButton(onClick = { navController.navigateUp() }) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back to Home")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary
                )
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Icon(
                painter = painterResource(id = R.drawable.logo),
                contentDescription = "App Logo",
                modifier = Modifier.size(120.dp).padding(bottom = 32.dp)
            )

            TextField(
                value = username,
                onValueChange = { username = it },
                label = { Text("Username") },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(8.dp))
            TextField(
                value = password,
                onValueChange = { password = it },
                label = { Text("Password") },
                modifier = Modifier.fillMaxWidth(),
                visualTransformation = PasswordVisualTransformation()  // Oculta el texto ingresado
            )
            Spacer(modifier = Modifier.height(16.dp))
            Button(onClick = {
                val user = mockUsers.find { it.username == username && it.password == password }
                if (user != null) {
                    navController.navigate("userAccount/${user.username}")
                } else {
                    errorMessage = "Invalid username or password"
                }
            }) {
                Text("Login")
            }
            if (errorMessage.isNotEmpty()) {
                Text(errorMessage, color = Color.Red)
            }
            Spacer(modifier = Modifier.height(8.dp))
            TextButton(onClick = { navController.navigate("recoverPassword") }) {
                Text("Forgot Password?")
            }
        }
    }
}