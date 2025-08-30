package com.tincek46.littlelemon.composables

import android.content.Context
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.core.content.edit
import androidx.navigation.NavController

@Composable
fun Profile(navController: NavController) {
    val context = LocalContext.current
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Welcome to the Profile Screen!",
            style = MaterialTheme.typography.headlineMedium
        )
        Button(onClick = { navController.popBackStack() }) {
            Text(text = "Go Back")
        }
        Button(onClick = {
            // Clear SharedPreferences to simulate logout
            val sharedPreferences = context.getSharedPreferences("LittleLemonPrefs", Context.MODE_PRIVATE)
            sharedPreferences.edit {
                clear()
            }
            navController.navigate("onboarding") {
                popUpTo("home") { inclusive = true }
            }
        }) {
            Text(text = "Logout")
        }
    }
}