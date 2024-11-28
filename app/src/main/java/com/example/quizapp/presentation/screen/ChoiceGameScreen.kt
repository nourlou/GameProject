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
import androidx.compose.runtime.saveable.rememberSaveable
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

// Palette de couleurs
val PrimaryColor = Color(0xFF6200EE)
val SecondaryColor = Color(0xFF03DAC5)
val LightBackground = Color(0xFFEDE7F6)
val GradientStart = Color(0xFF8E24AA)
val GradientEnd = Color(0xFFCE93D8)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChoiceGameScreen(navController: NavController) {
    // Liste des choix
    val choices = listOf(
        "تاخذ دينار كل ما تقابل إنسان" to "تاخذ 100 دينار كل يوم",
        "تمشي للبحر كل يوم" to "تسافر مرة في السنة",
        "تعمل فطور في الدار كل صباح" to "تفطر في المقهى كل صباح",
        "تحكم في الأشخاص" to "تحكم في الظروف",
        "تشرب تاي" to  "تشرب قهوة" ,
        "ما عادش تستعمل spotify" to "ما عادش تستعمل youtube" ,



    )
    var currentChoiceIndex by rememberSaveable { mutableStateOf(0) }
    var showPercentages by rememberSaveable { mutableStateOf(false) }
    var userSelection by rememberSaveable { mutableStateOf("") }
    var percentages by rememberSaveable { mutableStateOf(Pair(0, 0)) }

    Scaffold(
        content = { innerPadding ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Brush.verticalGradient(listOf(Color(0xFF3E6B89), Color(0xFF6DB1FF))))
                    .padding(innerPadding)
            ) {
                // Barre supérieure avec score
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    ScoreDisplay(score = "123180 🪙")
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(imageVector = Icons.Default.Close, contentDescription = "Fermer", tint = Color.White)
                    }
                }

                Spacer(modifier = Modifier.height(24.dp))

                // Titre
                Text(
                    text = "لو خيروك",
                    color = Color.White,
                    fontSize = 28.sp,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(24.dp))

                if (currentChoiceIndex < choices.size) {
                    val (choice1, choice2) = choices[currentChoiceIndex]

                    if (!showPercentages) {
                        // Affichage des choix
                        ChoiceCard(
                            text = choice1,
                            gradient = Brush.horizontalGradient(listOf(Color(0xFF7FC8F8), Color(0xFF4286F5))),
                            onClick = {
                                userSelection = choice1
                                percentages = calculatePercentages(choice1) // Calculate percentages dynamically
                                showPercentages = true
                            }
                        )
                        Spacer(modifier = Modifier.height(16.dp))
                        ChoiceCard(
                            text = choice2,
                            gradient = Brush.horizontalGradient(listOf(Color(0xFFF5A623), Color(0xFFFF7043))),
                            onClick = {
                                userSelection = choice2
                                percentages = calculatePercentages(choice2) // Calculate percentages dynamically
                                showPercentages = true
                            }
                        )

                    } else {
                        // Affichage des pourcentages
                        AnimatedPercentageDisplay(
                            choice1 = choice1,
                            choice2 = choice2,
                            percentages = percentages
                        ) {
                            showPercentages = false
                            currentChoiceIndex++
                        }
                    }
                } else {
                    // Fin des choix
                    Text(
                        text = "شكراً على المشاركة!",
                        color = Color.White,
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
fun ScoreDisplay(score: String) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .background(
                brush = Brush.horizontalGradient(
                    colors = listOf(PrimaryColor, SecondaryColor)
                ),
                shape = RoundedCornerShape(16.dp)
            )
            .padding(horizontal = 16.dp, vertical = 12.dp) // Plus d'espace autour du texte
            .shadow(8.dp, shape = RoundedCornerShape(16.dp)) // Ombre pour donner de la profondeur
    ) {
        Text(
            text = score,
            color = Color.White,
            fontSize = 20.sp, // Texte plus grand
            fontWeight = FontWeight.Medium
        )
    }
}

@Composable
fun ChoiceCard(text: String, gradient: Brush, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp)
            .clickable(onClick = onClick),
        colors = CardDefaults.cardColors(containerColor = Color.Transparent),
        elevation = CardDefaults.cardElevation(6.dp)
    ) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .background(gradient, shape = RoundedCornerShape(16.dp))
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
fun AnimatedPercentageDisplay(
    choice1: String,
    choice2: String,
    percentages: Pair<Int, Int>,
    onNext: () -> Unit
) {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        ChoiceCardWithPercentage(choice1, percentages.first, Color(0xFF7FC8F8))
        Spacer(modifier = Modifier.height(16.dp))
        CircularPercentageBar(percentages.first, percentages.second)
        Spacer(modifier = Modifier.height(16.dp))
        ChoiceCardWithPercentage(choice2, percentages.second, Color(0xFFFF7043))
        Spacer(modifier = Modifier.height(16.dp))
        Button(
            onClick = onNext,
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF4286F5))
        ) {
            Text("Suivant", color = Color.White, fontWeight = FontWeight.Bold)
        }
    }
}

@Composable
fun CircularPercentageBar(percent1: Int, percent2: Int) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 32.dp),
        horizontalArrangement = Arrangement.Center
    ) {
        CircularIndicator(percentage = percent1, color = Color(0xFF7FC8F8))
        Spacer(modifier = Modifier.width(16.dp))
        CircularIndicator(percentage = percent2, color = Color(0xFFFF7043))
    }
}

@Composable
fun CircularIndicator(percentage: Int, color: Color) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .size(100.dp)
            .clip(CircleShape)
            .background(color.copy(alpha = 0.1f))
    ) {
        Text("$percentage%", color = color, fontSize = 20.sp, fontWeight = FontWeight.Bold)
    }
}


@Composable
fun ChoiceCardWithPercentage(text: String, percentage: Int, color: Color) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        colors = CardDefaults.cardColors(containerColor = color)
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = text,
                color = Color.White,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "$percentage%",
                color = Color.White,
                fontSize = 18.sp
            )
        }
    }
}

fun calculatePercentages(choice: String, totalResponses: Int = 100): Pair<Int, Int> {
    // Simulate results for this example
    return if (choice == "تاخذ دينار كل ما تقابل إنسان") {
        Pair(60, 40) // 60% for the chosen option, 40% for the other
    } else {
        Pair(40, 60) // Adjust percentages based on user choice
    }
}
