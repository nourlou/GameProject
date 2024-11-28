package com.example.quizapp

import PuzzleScreen
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.quizapp.presentation.screen.CategoriesScreen
import com.example.quizapp.presentation.screen.ChoiceGameScreen
import com.example.quizapp.domain.model.Question

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun QuizAppUI() {
    val navController = rememberNavController()

    // Map des catégories et des routes correspondantes
    val categoryRoutes = mapOf(
        "لو خيروك" to "choiceGame",
        "puzzleركب ال" to "puzzleGame",
        "إختبر ثقافتك" to "quiz/إختبرثقافتك"
    )

    NavHost(navController = navController, startDestination = "home") {
        // Route HomeScreen
        composable("home") {
            HomeScreen(navController = navController) // Écran d'accueil
        }

        // Route CategoriesScreen
        composable("categories") {
            CategoriesScreen(navController = navController) { selectedCategory ->
                // Naviguer vers la route basée sur la catégorie
                val route = categoryRoutes[selectedCategory] ?: "defaultRoute"
                navController.navigate(route)
            }
        }

        // Route QuizScreen avec une catégorie dynamique
        composable(
            "quiz/{category}",
            arguments = listOf(navArgument("category") { type = NavType.StringType })
        ) { backStackEntry ->
            val category = backStackEntry.arguments?.getString("category")
            if (category != null) {
                QuizScreen(category = category, navController = navController)
            } else {
                Text("Catégorie invalide") // Gestion de cas d'erreur
            }
        }

        // Route ChoiceGameScreen
        composable("choiceGame") {
            ChoiceGameScreen(navController = navController)
        }

        // Route PuzzleGameScreen
        composable("puzzleGame") {
            PuzzleScreen(navController = navController)
        }

        // Gestion de la route par défaut
        composable("defaultRoute") {
            Text(
                text = "Page non trouvée",
                style = MaterialTheme.typography.headlineMedium,
                modifier = Modifier.padding(16.dp)
            )
        }
    }
}

// Exemple de gestion des questions pour une catégorie spécifique
fun getQuestionsForCategory(category: String): List<Question> {
    return when (category) {
        "إختبرثقافتك" -> QuizData.Game1
        else -> emptyList() // Retourne une liste vide pour les catégories inconnues
    }
}
