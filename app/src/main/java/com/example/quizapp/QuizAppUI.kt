package com.example.quizapp

import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.quizapp.presentation.screen.CategoriesScreen

import com.example.quizapp.domain.model.Question
import com.example.quizapp.presentation.screen.ChoiceGameScreen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun QuizAppUI() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "home") {
        // HomeScreen route
        composable("home") {
            HomeScreen(navController = navController) // Home screen as the starting point
        }

        // CategoriesScreen route
        composable("categories") {
            CategoriesScreen(navController = navController)
        }

        // QuizScreen route (based on category)
        composable("quiz/{category}") { backStackEntry ->
            val category = backStackEntry.arguments?.getString("category")
            category?.let {
                QuizScreen(category = it, navController = navController)
            } ?: Text("Invalid category")
        }

        // ChoiceGameScreen route
        composable("choiceGame") {
            ChoiceGameScreen(navController = navController)
        }
    }
}



fun getQuestionsForCategory(category: String): List<Question> {
    // Ensure each category has valid questions
    return when (category) {


        "إختبرثقافتك" -> QuizData.Game1

        else -> emptyList() // Default to an empty list for unknown categories
    }
}

