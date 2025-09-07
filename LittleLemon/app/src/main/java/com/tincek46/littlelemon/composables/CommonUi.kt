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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.tincek46.littlelemon.R
import com.tincek46.littlelemon.Profile // Import for Profile.route

@Composable
fun LittleLemonTopAppBar(modifier: Modifier = Modifier) {
    Image(
        painter = painterResource(id = R.drawable.logo),
        contentDescription = stringResource(R.string.cd_little_lemon_logo),
        contentScale = ContentScale.Fit,
        modifier = modifier
            .height(80.dp) // Maintain a fixed height
            .fillMaxWidth(0.65f) // Takes 65% of parent width, height will adjust by ContentScale.Fit
    )
}

@Composable
fun StandardAppHeader(navController: NavController) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 24.dp)
    ) {
        LittleLemonTopAppBar(modifier = Modifier.align(Alignment.Center))

        Image(
            painter = painterResource(id = R.drawable.profile),
            contentDescription = stringResource(R.string.cd_profile_icon),
            modifier = Modifier
                .size(48.dp)
                .align(Alignment.CenterEnd)
                .clickable { navController.navigate(Profile.route) } // Use type-safe route
        )
    }
}
