package com.example.quizapp.presentation.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.quizapp.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CategoriesScreen(navController: NavController) {
    val categories = listOf(
        Pair("إختبرثقافتك", R.drawable.brain), // Only category and imageRes
        Pair("لو خيروك", R.drawable.choose)
    )

    Scaffold(
        topBar = {
            // Add your top bar here if needed
        },
        content = { innerPadding ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
            ) {
                // Background image
                Image(
                    painter = painterResource(id = R.drawable.game3), // Replace with your image resource
                    contentDescription = "Background Image",
                    contentScale = ContentScale.Crop, // Crop to fit the screen
                    modifier = Modifier.fillMaxSize()
                )

                // Foreground content
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(horizontal = 16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Top
                ) {
                    Text(
                        text = "",
                        style = TextStyle(
                            fontSize = 28.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.White
                        ),
                        modifier = Modifier.padding(vertical = 20.dp)
                    )

                    categories.forEach { (category, imageRes) ->
                        CategoryCard(
                            category = category,
                            imageRes = imageRes,
                            onClick = {
                                if (category == "لو خيروك") {
                                    navController.navigate("choiceGame")
                                } else {
                                    navController.navigate("quiz/${category}")
                                }
                            }
                        )
                    }
                }
            }
        }
    )
}

@Composable
fun CategoryCard(
    category: String,
    imageRes: Int,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 12.dp)
            .clickable { onClick() }
            .shadow(8.dp, RoundedCornerShape(16.dp)),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color(0xFFF3E5F5)
        ),
        elevation = CardDefaults.cardElevation(12.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth()
        ) {
            Image(
                painter = painterResource(id = imageRes),
                contentDescription = "$category Logo",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(80.dp) // Adjusted image size
                    .clip(RoundedCornerShape(50)) // Rounded image style
                    .padding(end = 16.dp)
            )

            Text(
                text = category,
                style = TextStyle(
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF1A237E)
                )
            )
        }
    }
}
