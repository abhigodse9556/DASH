package com.project.mypersonalassistant.navigation.auth

sealed class AuthRoutes(val route: String) {
    object Splash : AuthRoutes("splash")
    object Introduction : AuthRoutes("introduction")
    object Login : AuthRoutes("login")
    object Register : AuthRoutes("register")
}