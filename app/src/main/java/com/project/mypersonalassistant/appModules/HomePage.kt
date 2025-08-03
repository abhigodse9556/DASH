package com.project.mypersonalassistant.appModules

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import com.project.mypersonalassistant.navigation.auth.AuthRoutes

@Composable
fun HomePage( navigateTo: (AuthRoutes) -> Unit ){
    Column {
        Text(text = "Welcome to DASH!")
        Button(onClick = { navigateTo(AuthRoutes.Login) }) {
            Text(text = "Logout")
        }
    }
}