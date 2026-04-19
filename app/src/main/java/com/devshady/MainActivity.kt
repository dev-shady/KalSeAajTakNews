package com.devshady

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.SystemBarStyle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.graphics.Color
import android.graphics.Color as JavaColor


class MainActivity: ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Required for statusBarsPadding() to work correctly
        enableEdgeToEdge(
            statusBarStyle = SystemBarStyle.dark(JavaColor.TRANSPARENT),
            navigationBarStyle = SystemBarStyle.dark(JavaColor.TRANSPARENT)
        )

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            window.isNavigationBarContrastEnforced = false
        }

        setContent {
            MainScreen()
        }
    }

    @Composable
    fun MainScreen() {
        Scaffold(containerColor = androidx.compose.ui.graphics.Color.Black) { innerPadding ->
            Surface(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding),
                color = Color.Black // Setting background to black so white text is visible
            ) {
                Box(
                    Modifier.padding(16.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text("Kal Se Aaj Tak", color = Color.White)
                }

            }
        }
    }
}