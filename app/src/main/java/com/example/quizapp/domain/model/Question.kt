package com.example.quizapp.domain.model

data class Question(
    val text: String, // The question text
    val options: List<String>, // The possible options
    val correctAnswer: String // The correct answer
)
