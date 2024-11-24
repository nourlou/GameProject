package com.example.quizapp.domain.usecase

import com.example.quizapp.QuizData
import com.example.quizapp.domain.model.Question

class GetQuestionsForCategory {
    fun execute(category: String): List<Question> {
        return when (category) {

            "إختبرثقافتك" -> QuizData.Game1
             // Ajoutez cela si vous avez des questions d'histoire
            else -> emptyList()
        }
    }
}

