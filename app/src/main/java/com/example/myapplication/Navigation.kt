package com.example.myapplication

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.myapplication.pantallas.*

@Composable
fun AppNavigation() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "start") {
        composable("start") { HomeScreen(navController) }
        composable("login") { LoginScreen(navController) }
        composable("recoverPassword") { RecoverPasswordScreen(navController) }
        composable("services/{username}") { backStackEntry ->
            val username = backStackEntry.arguments?.getString("username") ?: ""
            ServicesScreen(navController, username)
        }
        composable(
            "userAccount/{username}",
            arguments = listOf(navArgument("username") { type = NavType.StringType })
        ) { backStackEntry ->
            val username = backStackEntry.arguments?.getString("username") ?: ""
            UserAccountScreen(
                navController = navController,
                username = username,
                getUserData = ::getUserData
            )
        }
    }
}

// Add this function to get user data
fun getUserData(username: String): User? {
    return mockUsers.find { it.username == username }
}