package com.project.mypersonalassistant.navigation.auth

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.project.mypersonalassistant.auth.*

@Composable
fun AuthNavGraph(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = AuthRoutes.Splash.route
    ) {
//        composable(AuthRoutes.Introduction.route) {
//            IntroductionScreen(
//                onLoginClick = { navController.navigate(AuthRoutes.Login.route) },
//                onRegisterClick = { navController.navigate(AuthRoutes.Register.route) }
//            )
//        }
        composable(AuthRoutes.Splash.route) {
            SplashScreen(
                goToLoginPage = { navController.navigate(AuthRoutes.Login.route) }
            )
        }

        composable(AuthRoutes.Login.route) {
            LoginPage(
                onRegisterClick = { navController.navigate(AuthRoutes.Register.route) }
            )
        }

        composable(AuthRoutes.Register.route) {
            RegistrationPage(
                onLoginClick = { navController.navigate(AuthRoutes.Login.route) }
            )
        }
    }
}
