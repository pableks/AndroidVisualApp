package com.example.myapplication

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.myapplication.pantallas.*

@Composable
fun AppNavigation() {
    val navController = rememberNavController()
    val textSizeViewModel: TextSizeViewModel = viewModel()

    NavHost(navController = navController, startDestination = "start") {
        composable("start") { HomeScreen(navController) }
        composable("login") { LoginScreen(navController, textSizeViewModel) }
        composable("recoverPassword") { RecoverPasswordScreen(navController) }
        composable("services/{username}") { backStackEntry ->
            val username = backStackEntry.arguments?.getString("username") ?: ""
            ServicesScreen(navController, username, textSizeViewModel)
        }
        composable(
            "userAccount/{username}",
            arguments = listOf(navArgument("username") { type = NavType.StringType })
        ) { backStackEntry ->
            val username = backStackEntry.arguments?.getString("username") ?: ""
            UserAccountScreen(
                navController = navController,
                username = username,
                viewModel = textSizeViewModel,
                getUserData = ::getUserData
            )
        }
        composable("objectRecognition") {
            ObjectRecognitionScreen(navController, textSizeViewModel)
        }
    }
}

// Add this function to get user data
fun getUserData(username: String): User? {
    return mockUsers.find { it.username == username }
}