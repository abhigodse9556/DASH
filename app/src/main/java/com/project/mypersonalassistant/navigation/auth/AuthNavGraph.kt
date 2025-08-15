package com.project.mypersonalassistant.navigation.auth

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.project.mypersonalassistant.appModules.HomePage
import com.project.mypersonalassistant.auth.LoginPage
import com.project.mypersonalassistant.auth.RegistrationPage
import com.project.mypersonalassistant.auth.SplashScreen
import com.project.mypersonalassistant.auth.*

@Composable
fun AuthNavGraph(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = AuthRoutes.Splash.route
    ) {

        fun navigate(page: AuthRoutes) {
            navController.navigate(page.route)
        }

        composable(AuthRoutes.Splash.route) {
            SplashScreen(
                navigateTo = { route -> navigate(route) }
            )
        }

        composable(AuthRoutes.Login.route) {
            LoginPage(
                navigateTo = { route -> navigate(route) }
            )
        }

        composable(AuthRoutes.Register.route) {
            RegistrationPage(
                navigateTo = { route -> navigate(route) }
            )
        }

        composable(AuthRoutes.ResetPass.route) {
            ResetPasswordPage(
                navigateTo = { route -> navigate(route) }
            )
        }

        composable(AuthRoutes.Home.route) {
            HomePage(
                navigateTo = { route -> navigate(route) }
            )
        }
    }
}
