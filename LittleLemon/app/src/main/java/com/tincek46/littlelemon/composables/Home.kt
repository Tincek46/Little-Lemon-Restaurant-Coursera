package com.tincek46.littlelemon.composables

import android.util.Log // <-- ADD THIS IMPORT IF NOT ALREADY PRESENT
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
// import androidx.compose.foundation.rememberScrollState // REMOVED - Unused import
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.tincek46.littlelemon.MenuItemEntity
import com.tincek46.littlelemon.R
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Home(navController: NavController, menuItems: List<MenuItemEntity>) {
    var selectedCategory by remember { mutableStateOf("all") }
    val allCategories = remember(menuItems) {
        listOf("all") + menuItems.map { it.category.lowercase(Locale.getDefault()) }.distinct().sorted()
    }

    val filteredMenuItems = remember(selectedCategory, menuItems) {
        if (selectedCategory.equals("all", ignoreCase = true)) {
            menuItems
        } else {
            menuItems.filter { it.category.equals(selectedCategory, ignoreCase = true) }
        }
    }

    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        StandardAppHeader(navController = navController)
        HeroSection()

        Column(modifier = Modifier.padding(horizontal = 16.dp)) {
            Text(
                text = "ORDER FOR DELIVERY!",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(vertical = 16.dp)
            )
            CategoryFilters(
                categories = allCategories,
                selectedCategory = selectedCategory,
                onCategorySelected = { category -> selectedCategory = category }
            )
        }
        MenuDisplay(menuItems = filteredMenuItems)
    }
}

@Composable
fun CategoryFilters(
    categories: List<String>,
    selectedCategory: String,
    onCategorySelected: (String) -> Unit
) {
    LazyRow(
        modifier = Modifier.fillMaxWidth().padding(bottom = 16.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(categories) { category ->
            val isSelected = category.equals(selectedCategory, ignoreCase = true)
            TextButton(
                onClick = { onCategorySelected(category) },
                shape = RoundedCornerShape(8.dp),
                colors = ButtonDefaults.textButtonColors(
                    containerColor = if (isSelected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.surfaceVariant,
                    contentColor = if (isSelected) MaterialTheme.colorScheme.onPrimary else MaterialTheme.colorScheme.onSurfaceVariant
                )
            ) {
                Text(
                    text = category.replaceFirstChar { if (it.isLowerCase()) it.titlecase(Locale.getDefault()) else it.toString() },
                    style = MaterialTheme.typography.labelLarge
                )
            }
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HeroSection() {
    Column(
        modifier = Modifier
            .background(MaterialTheme.colorScheme.primary)
            .padding(16.dp)
            .fillMaxWidth()
    ) {
        Text(
            text = "Little Lemon",
            style = MaterialTheme.typography.headlineLarge,
            color = MaterialTheme.colorScheme.onPrimary
        )
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(modifier = Modifier.weight(1f).padding(end = 8.dp)) {
                Text(
                    text = "Chicago",
                    style = MaterialTheme.typography.headlineSmall,
                    color = MaterialTheme.colorScheme.onPrimary,
                    modifier = Modifier.padding(bottom = 8.dp)
                )
                Text(
                    text = "We are a family-owned Mediterranean restaurant, focused on traditional recipes served with a modern twist.",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onPrimary,
                    modifier = Modifier.padding(bottom = 16.dp)
                )
            }
            Image(
                painter = painterResource(id = R.drawable.hero_image),
                contentDescription = "Little Lemon Hero Image",
                modifier = Modifier
                    .size(140.dp)
                    .aspectRatio(1f),
                contentScale = ContentScale.Crop
            )
        }
        TextField(
            value = "",
            onValueChange = {},
            label = { Text("Enter search phrase") },
            leadingIcon = { Icon(imageVector = Icons.Filled.Search, contentDescription = "Search") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp)
                .background(MaterialTheme.colorScheme.surface, RoundedCornerShape(8.dp)),
            singleLine = true,
            colors = TextFieldDefaults.colors( // CORRECTED PART
                focusedContainerColor = Color.Transparent,
                unfocusedContainerColor = Color.Transparent,
                disabledContainerColor = Color.Transparent,
                errorContainerColor = Color.Transparent, // Added for completeness
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                disabledIndicatorColor = Color.Transparent
            )
        )
    }
}

@Composable
fun MenuDisplay(menuItems: List<MenuItemEntity>) {
    LazyColumn(
        modifier = Modifier.padding(horizontal = 16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        items(menuItems) { menuItem ->
            SingleMenuItem(item = menuItem)
            HorizontalDivider(thickness = 1.dp, color = MaterialTheme.colorScheme.outlineVariant)
        }
    }
}

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun SingleMenuItem(item: MenuItemEntity) {
    // Log the title and image URL when this composable is invoked
    Log.d("SingleMenuItem", "Displaying item: ${item.title}, Image URL: ${item.image}")

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(
            modifier = Modifier.weight(1f).padding(end = 8.dp)
        ) {
            Text(
                text = item.title,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = item.description,
                style = MaterialTheme.typography.bodySmall,
                maxLines = 2,
                modifier = Modifier.padding(vertical = 4.dp)
            )
            Text(
                text = "$${item.price}",
                style = MaterialTheme.typography.bodyMedium,
                fontWeight = FontWeight.SemiBold
            )
        }
        GlideImage(
            model = item.image,
            contentDescription = "${item.title} image",
            modifier = Modifier.size(90.dp),
            contentScale = ContentScale.Crop
        )
    }
}

@Preview(showBackground = true)
@Composable
fun HomePreview() {
    val navController = rememberNavController()
    val dummyMenuItems = listOf(
        MenuItemEntity(1, "Greek Salad", "Crispy lettuce, peppers, olives.", "10.00", "", "starters"),
        MenuItemEntity(2, "Lemon Dessert", "Ricotta Cake.", "8.00", "", "desserts"),
        MenuItemEntity(3, "Bruschetta", "Oven-baked bread.", "7.00", "", "starters"),
        MenuItemEntity(4, "Pasta", "Penne with sauce.", "12.00", "", "mains")

    )
    // com.tincek46.littlelemon.ui.theme.LittleLemonTheme {
        Home(navController = navController, menuItems = dummyMenuItems)
    // }
}
