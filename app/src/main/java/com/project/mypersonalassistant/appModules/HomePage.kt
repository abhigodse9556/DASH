package com.project.mypersonalassistant.appModules

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.*
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.*
import com.project.mypersonalassistant.appModules.common.Header
import com.project.mypersonalassistant.components.CustomButton

import com.project.mypersonalassistant.navigation.auth.AuthRoutes

@Composable
fun HomePage( navigateTo: (AuthRoutes) -> Unit ){
    Box(){

        Column(
            modifier = Modifier
                .fillMaxWidth(),
        ) {

            Header()
            Row {

                CustomButton(
                    text = "Logout",
                    onClick = {navigateTo(AuthRoutes.Login)}
                )

            }


        }
    }


    }