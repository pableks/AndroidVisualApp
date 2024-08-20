package com.example.myapplication

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.myapplication.pantallas.LoginScreen
import com.example.myapplication.pantallas.RecoverPasswordScreen
import com.example.myapplication.pantallas.HomeScreen
import com.example.myapplication.pantallas.UserAccountScreen

@Composable
fun AppNavigation() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = "start") {
        composable("start") { HomeScreen(navController) }
        composable("login") { LoginScreen(navController) }
        composable("recoverPassword") { RecoverPasswordScreen(navController) }
        composable(
            "userAccount/{username}",
            arguments = listOf(navArgument("username") { type = NavType.StringType })
        ) { backStackEntry ->
            val username = backStackEntry.arguments?.getString("username") ?: ""
            UserAccountScreen(navController, username)
        }
    }
}


