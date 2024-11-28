package com.example.quizapp.presentation.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
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
fun CategoriesScreen(navController: NavController, param: (Any) -> Unit) {
    val categories = listOf(
        Pair("إختبر ثقافتك", R.drawable.brain),
        Pair("لو خيروك", R.drawable.choose),
        Pair("puzzleركب ال", R.drawable.puzzle)
    )

    // Mappage des routes
    val categoryRoutes = mapOf(
        "لو خيروك" to "choiceGame",
        "puzzleركب ال" to "puzzleGame",
        "إختبر ثقافتك" to "quiz/إختبرثقافتك"
    )

    Scaffold(
        content = { innerPadding ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
            ) {
                // Image de fond
                Image(
                    painter = painterResource(id = R.drawable.game3),
                    contentDescription = "Background Image",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.fillMaxSize()
                )

                // Contenu de premier plan
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(horizontal = 16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    // Titre
                    Text(
                        text = "اختر لعبة",
                        style = TextStyle(
                            fontSize = 32.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.White
                        ),
                        modifier = Modifier.padding(bottom = 24.dp)
                    )

                    // Itération à travers les catégories
                    categories.forEach { (category, imageRes) ->
                        CategoryCard(
                            category = category,
                            imageRes = imageRes,
                            onClick = {
                                // Navigation dynamique basée sur categoryRoutes
                                val route = categoryRoutes[category] ?: "defaultRoute"
                                navController.navigate(route)
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
            .shadow(12.dp, RoundedCornerShape(16.dp)),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.Transparent // Transparence totale
        ),
        elevation = CardDefaults.cardElevation(8.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth()
        ) {
            // Image de la catégorie
            Image(
                painter = painterResource(id = imageRes),
                contentDescription = "$category Logo",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(100.dp) // Taille augmentée
                    .clip(RoundedCornerShape(50)) // Style arrondi
                    .padding(end = 16.dp)
            )

            // Texte de la catégorie
            Text(
                text = category,
                style = TextStyle(
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
            )
        }
    }
}
