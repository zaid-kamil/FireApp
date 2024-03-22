package com.digi.fireapp.ui

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

enum class Screen(val route: String) {
    Home("home"),
    Login("login"),
    Register("register"),
    NoteDetail("notes"),
    Document("documents"),
}

@Composable
fun AppNavigation() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = Screen.Login.route) {
        composable(Screen.Login.route) {

        }
        composable(Screen.Register.route) {

        }
        composable(Screen.Home.route) {

        }
        composable(Screen.NoteDetail.route) {

        }
        composable(Screen.Document.route) {

        }
    }
}