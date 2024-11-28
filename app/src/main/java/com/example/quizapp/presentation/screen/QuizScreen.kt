package com.example.quizapp

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.quizapp.domain.model.Question
import kotlinx.coroutines.delay

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun QuizScreen(category: String, navController: NavController) {
    val questions = getQuestionsForCategory(category)
    var currentQuestionIndex by rememberSaveable { mutableStateOf(0) }
    val totalQuestions = questions.size
    var score by rememberSaveable { mutableStateOf(0) }
    var selectedAnswerIndex by rememberSaveable { mutableStateOf<Int?>(null) }
    var showFinalScreen by rememberSaveable { mutableStateOf(false) }

    if (showFinalScreen) {
        // Final Score Screen
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xFFADD8E6)) // Light blue background
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            val message = if (score > 0) "صحيح" else "آسف"
            val scoreColor = if (score > 0) Color(0xFF4CAF50) else Color(0xFFF44336)

            Text(
                text = message,
                style = TextStyle(
                    fontSize = 36.sp,
                    fontWeight = FontWeight.Bold,
                    color = scoreColor
                ),
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "Score النهائي: $score/$totalQuestions",
                style = TextStyle(
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    color = scoreColor
                )
            )

            Spacer(modifier = Modifier.height(32.dp))

            // Continue Button
            Button(
                onClick = {
                    showFinalScreen = false
                    currentQuestionIndex = 0
                    score = 0
                    selectedAnswerIndex = null
                },
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFFC107)), // Yellow
                shape = RoundedCornerShape(16.dp),
                modifier = Modifier
                    .fillMaxWidth(0.6f)
                    .padding(vertical = 8.dp)
            ) {
                Text(
                    text = "واصل",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
            }

            // Exit Button
            Button(
                onClick = { navController.popBackStack() },
                colors = ButtonDefaults.buttonColors(containerColor = Color.White),
                shape = RoundedCornerShape(16.dp),
                modifier = Modifier
                    .fillMaxWidth(0.6f)
                    .padding(vertical = 8.dp)
            ) {
                Text(
                    text = "خروج",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black
                )
            }
        }
    } else {
        // Quiz Screen
        val currentQuestion = questions.getOrNull(currentQuestionIndex)
        val correctAnswerIndex = currentQuestion?.options?.indexOf(currentQuestion.correctAnswer) ?: -1

        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Brush.verticalGradient(listOf(Color(0xFF8E24AA), Color(0xFF673AB7))))
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Display Score Progress Line
            Text(
                text = "Score: $score",
                style = TextStyle(fontSize = 20.sp, fontWeight = FontWeight.Bold, color = Color.White),
                textAlign = TextAlign.Center
            )
            LinearProgressIndicator(
                progress = score / (totalQuestions * 10).toFloat(),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp)
                    .height(8.dp),
                color = Color(0xFF4CAF50),
                trackColor = Color(0xFFBDBDBD)
            )

            Spacer(modifier = Modifier.height(16.dp))

            currentQuestion?.let {
                Text(
                    text = it.text,
                    style = TextStyle(fontSize = 24.sp, fontWeight = FontWeight.Bold, color = Color.White),
                    textAlign = TextAlign.Center
                )

                it.options.forEachIndexed { index, answer ->
                    Button(
                        onClick = {
                            if (selectedAnswerIndex == null) {
                                selectedAnswerIndex = index
                                if (index == correctAnswerIndex) score += 10
                            }
                        },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = when {
                                selectedAnswerIndex == null -> Color(0xFF2196F3)
                                index == correctAnswerIndex -> Color(0xFF4CAF50)
                                index == selectedAnswerIndex -> Color(0xFFF44336)
                                else -> Color(0xFFBDBDBD)
                            }
                        ),
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 8.dp)
                            .height(55.dp),
                        shape = RoundedCornerShape(16.dp)
                    ) {
                        Text(
                            text = answer,
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.White
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.weight(1f))

            if (selectedAnswerIndex != null) {
                Button(
                    onClick = {
                        if (currentQuestionIndex < totalQuestions - 1) {
                            currentQuestionIndex++
                            selectedAnswerIndex = null
                        } else {
                            showFinalScreen = true
                        }
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF673AB7)),
                    shape = RoundedCornerShape(16.dp),
                    modifier = Modifier
                        .padding(16.dp)
                        .fillMaxWidth(0.85f)
                ) {
                    Text(
                        text = if (currentQuestionIndex < totalQuestions - 1) "التالي " else "Finish",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                }
            }
        }
    }
}
