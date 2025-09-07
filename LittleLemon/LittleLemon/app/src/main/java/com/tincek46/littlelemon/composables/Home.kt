package com.tincek46.littlelemon.composables

// import androidx.compose.foundation.Image // No longer directly used for header images
// import androidx.compose.ui.layout.ContentScale // No longer directly used for logo
// import androidx.compose.ui.res.painterResource // No longer directly used for header images

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
// R import is still needed for other resources if any, or will be optimized by IDE if not.
// import com.tincek46.littlelemon.R 

@Composable
fun Home(navController: NavController) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp), // Overall padding for the screen content
        horizontalAlignment = Alignment.CenterHorizontally // Centers content below the header
    ) {
        StandardAppHeader(navController = navController) // Use the standardized header

        // Welcome Text
        Text(
            text = "Welcome to Little Lemon!",
            style = MaterialTheme.typography.headlineMedium,
            modifier = Modifier.padding(top = 16.dp, bottom = 32.dp) // Adjust padding as needed
        )
    }
}

@Preview(showBackground = true)
@Composable
fun HomePreview() {
    val navController = rememberNavController()
    // Consider wrapping with your AppTheme if you have one:
    // YourAppTheme {
        Home(navController = navController)
    // }
}
