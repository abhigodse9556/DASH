package com.project.mypersonalassistant.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.rememberNavController
import com.project.mypersonalassistant.navigation.auth.AuthNavGraph

@Composable
fun AppNavigator() {
    val navController = rememberNavController()
    AuthNavGraph(navController = navController)
}
