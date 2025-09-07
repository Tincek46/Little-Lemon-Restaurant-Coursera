package com.tincek46.littlelemon.composables

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.tincek46.littlelemon.R

@Composable
fun LittleLemonTopAppBar(modifier: Modifier = Modifier) {
    Image(
        painter = painterResource(id = R.drawable.logo),
        contentDescription = "Little Lemon Logo",
        contentScale = ContentScale.Fit,
        modifier = modifier
            .height(80.dp) 
            .fillMaxWidth(0.65f) 
    )
}

@Composable
fun StandardAppHeader(navController: NavController) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 24.dp) // Consistent padding from Home.kt header
    ) {
        LittleLemonTopAppBar(modifier = Modifier.align(Alignment.Center)) // Centered logo

        Image(
            painter = painterResource(id = R.drawable.profile),
            contentDescription = "Profile",
            modifier = Modifier
                .size(48.dp) // Consistent profile icon size
                .align(Alignment.CenterEnd) // Profile icon to the right
                .clickable { navController.navigate("profile") }
        )
    }
}
