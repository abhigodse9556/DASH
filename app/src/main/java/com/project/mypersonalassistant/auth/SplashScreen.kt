package com.project.mypersonalassistant.auth

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.delay
import com.project.mypersonalassistant.R

@Composable
fun SplashScreen(goToLoginPage: () -> Unit) {
    var animatedText by remember { mutableStateOf("") }
    val fullText = "Daily Assistant for Scheduling & Help"
    var showTick by remember { mutableStateOf(false) }

    // Launch animation and navigation
    LaunchedEffect(Unit) {
        // Typing effect
        for (i in fullText.indices) {
            animatedText = fullText.substring(0, i + 1)
            delay(50)
        }

        delay(500)
        showTick = true

        delay(1500)
        goToLoginPage()
    }

    // UI
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(32.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Image(
                painter = painterResource(id = R.drawable.ic_launcher_foreground),
                contentDescription = "Splash Icon",
                modifier = Modifier.size(100.dp)
            )
            Spacer(modifier = Modifier.height(16.dp))
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text("DASH", fontSize = 32.sp)
                if (showTick) {
                    Text(" ✅", fontSize = 28.sp)
                }
            }
            Spacer(modifier = Modifier.height(12.dp))
            Text(animatedText, fontSize = 16.sp)
        }
    }
}
