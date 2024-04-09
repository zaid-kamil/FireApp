package com.digi.fireapp.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.digi.fireapp.ui.screens.home.HomeScreen
import com.digi.fireapp.ui.screens.home.HomeViewModel
import com.digi.fireapp.ui.screens.login.LoginScreen
import com.digi.fireapp.ui.screens.login.LoginViewModel
import com.digi.fireapp.ui.screens.register.RegisterScreen
import com.digi.fireapp.ui.screens.register.RegisterViewModel
import com.digi.fireapp.ui.screens.upload.DocScreen
import com.digi.fireapp.ui.screens.upload.DocViewModel
import com.digi.fireapp.ui.screens.welcome.WelcomeScreen
import com.digi.fireapp.ui.screens.welcome.WelcomeViewModel

enum class Screen(val route: String) {
    Home("home"),
    Login("login"),
    Register("register"),
    Notes("notes"),
    Document("documents"),
    Welcome("welcome"),
    Profile("profile")
}

@Composable
fun AppNavigation() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = Screen.Welcome.route) {
        composable(Screen.Login.route) {
            val vm: LoginViewModel = viewModel()
            LoginScreen(
                state = vm.state.collectAsState().value,
                onEvent = vm::onEvent,
                onNavigateToRegister = {
                    navController.navigate(Screen.Register.route)
                },
                onNavigateToHome = {
                    navController.navigate(Screen.Home.route)
                })
        }
        composable(Screen.Register.route) {
            val vm: RegisterViewModel = viewModel()
            RegisterScreen(
                state = vm.state.collectAsState().value,
                onEvent = vm::onEvent,
                onBack = {
                    navController.popBackStack()
                })
        }
        composable(Screen.Home.route) {
            val vm: HomeViewModel = viewModel()
            HomeScreen(
                state = vm.state.collectAsState().value,
                onEvent = vm::onEvent,
                onNavigateToDocuments = { navController.navigate(Screen.Document.route) },
                onNavigateToNotes = { navController.navigate(Screen.Notes.route) },
                onLogout = {},
            )
        }
        composable(Screen.Notes.route) {

        }
        composable(Screen.Document.route) {
            val vm: DocViewModel = viewModel()
            DocScreen(
                state = vm.state.collectAsState().value,
                onEvent = vm::onEvent,
                onBack = { navController.popBackStack() }
            )
        }
        composable(Screen.Welcome.route) {
            val vm: WelcomeViewModel = viewModel()
            WelcomeScreen(
                state = vm.state.collectAsState().value,
                onNavigationToLogin = { navController.navigate(Screen.Login.route) },
                onNavigationToHome = { navController.navigate(Screen.Home.route) }
            )
        }
        composable(Screen.Profile.route) {}
    }
}