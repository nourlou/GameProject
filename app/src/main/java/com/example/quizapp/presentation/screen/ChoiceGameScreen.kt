package com.example.quizapp.presentation.screen

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import kotlinx.coroutines.delay

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChoiceGameScreen(navController: NavController) {
    // Liste des choix
    val choices = listOf(
        "تاخذ دينار كل ما تقابل إنسان" to "تاخذ 100 دينار كل يوم",
        "تمشي للبحر كل يوم" to "تسافر مرة في السنة"
    )
    var currentChoiceIndex by remember { mutableStateOf(0) }
    var showPercentages by remember { mutableStateOf(false) }
    var userSelection by remember { mutableStateOf("") }
    var percentages by remember { mutableStateOf(Pair(0, 0)) }

    Scaffold(
        content = { innerPadding ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color(0xFFF0F4F8)) // Couleur d'arrière-plan plus douce
                    .padding(innerPadding)
            ) {
                // Barre supérieure avec score
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Box(
                        contentAlignment = Alignment.Center,
                        modifier = Modifier
                            .background(Color(0xFF42A5F5), shape = RoundedCornerShape(12.dp))
                            .padding(10.dp)
                            .shadow(4.dp, shape = RoundedCornerShape(12.dp))
                    ) {
                        Text(text = "123180 🪙", color = Color.White, fontSize = 16.sp)
                    }
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(imageVector = Icons.Default.Close, contentDescription = "Close")
                    }
                }

                Spacer(modifier = Modifier.height(24.dp))

                // Titre
                Text(
                    text = "لو خيروك",
                    color = Color.Black,
                    fontSize = 28.sp,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(24.dp))

                if (currentChoiceIndex < choices.size) {
                    val (choice1, choice2) = choices[currentChoiceIndex]

                    if (!showPercentages) {
                        // Afficher les choix
                        ChoiceCard(
                            text = choice1,
                            color = Color(0xFFFFA726),
                            onClick = {
                                userSelection = choice1
                                percentages = Pair(21, 79) // Remplacer avec des données réelles
                                showPercentages = true
                            }
                        )

                        Spacer(modifier = Modifier.height(20.dp))

                        ChoiceCard(
                            text = choice2,
                            color = Color(0xFF42A5F5),
                            onClick = {
                                userSelection = choice2
                                percentages = Pair(79, 21) // Remplacer avec des données réelles
                                showPercentages = true
                            }
                        )
                    } else {
                        // Afficher les pourcentages
                        PercentageDisplay(
                            choice1 = choice1,
                            choice2 = choice2,
                            percentages = percentages
                        ) {
                            // Passer au prochain choix
                            showPercentages = false
                            currentChoiceIndex++
                        }
                    }
                } else {
                    // Fin des choix
                    Text(
                        text = "شكراً على المشاركة!",
                        color = Color(0xFF42A5F5),
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold,
                        textAlign = TextAlign.Center,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp)
                    )
                }
            }
        }
    )
}

@Composable
fun ChoiceCard(text: String, color: Color, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 10.dp)
            .clickable(onClick = onClick)
            .animateContentSize(),
        elevation = CardDefaults.cardElevation(8.dp),
        colors = CardDefaults.cardColors(containerColor = color)
    ) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Text(
                text = text,
                color = Color.White,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center
            )
        }
    }
}

@Composable
fun PercentageDisplay(
    choice1: String,
    choice2: String,
    percentages: Pair<Int, Int>,
    onNext: () -> Unit
) {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Premier choix avec pourcentage
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            colors = CardDefaults.cardColors(containerColor = Color(0xFFFFA726))
        ) {
            Column(
                modifier = Modifier.padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = choice1,
                    color = Color.White,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "${percentages.first}%",
                    color = Color.White,
                    fontSize = 18.sp
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Barre de pourcentage
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 32.dp)
                .height(30.dp)
                .clip(RoundedCornerShape(16.dp))
                .background(Color(0xFFBDBDBD))
        ) {
            Row {
                Box(
                    modifier = Modifier
                        .weight(percentages.first.toFloat())
                        .fillMaxHeight()
                        .background(Color(0xFFFFA726))
                )
                Box(
                    modifier = Modifier
                        .weight(percentages.second.toFloat())
                        .fillMaxHeight()
                        .background(Color(0xFF42A5F5))
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Deuxième choix avec pourcentage
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            colors = CardDefaults.cardColors(containerColor = Color(0xFF42A5F5))
        ) {
            Column(
                modifier = Modifier.padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = choice2,
                    color = Color.White,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "${percentages.second}%",
                    color = Color.White,
                    fontSize = 18.sp
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Bouton suivant
        IconButton(onClick = onNext) {
            Icon(
                imageVector = Icons.Default.ArrowForward,
                contentDescription = "Next",
                tint = Color(0xFF42A5F5)
            )
        }
    }
}
