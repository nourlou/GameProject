package com.example.quizapp

import com.example.quizapp.QuizData.Game1
import com.example.quizapp.domain.model.Question

object QuizQuestionData {
    val kotlinQuestions = listOf(
        Question("What is Kotlin?", listOf("A programming language", "A framework", "A library", "A database"), "A programming language"),
        Question("Who developed Kotlin?", listOf("JetBrains", "Google", "Apple", "Microsoft"), "JetBrains"),
        Question("What is the latest version of Kotlin?", listOf("1.5", "1.6", "1.7", "1.8"), "1.7"),
        Question("Which of these is a Kotlin feature?", listOf("Null safety", "Low-level programming", "Manual memory management", "None of the above"), "Null safety"),
        Question("Which JVM language is Kotlin interoperable with?", listOf("Java", "Ruby", "Scala", "JavaScript"), "Java")
    )

    val pythonQuestions = listOf(
        Question("What is Python?", listOf("A snake", "A programming language", "A library", "A tool"), "A programming language"),
        Question("Who developed Python?", listOf("Guido van Rossum", "James Gosling", "Bjarne Stroustrup", "Ken Thompson"), "Guido van Rossum"),
        Question("What is the latest version of Python?", listOf("3.8", "3.9", "3.10", "3.11"), "3.10"),
        Question("Which of the following is used for web development in Python?", listOf("Django", "React", "Spring", "Angular"), "Django"),
        Question("What does PEP stand for?", listOf("Python Enhancement Proposal", "Python Extension Protocol", "Python Executive Process", "Python Engine Proposal"), "Python Enhancement Proposal")
    )

    val javaQuestions = listOf(
        Question("What is Java?", listOf("A programming language", "A library", "A framework", "A database"), "A programming language"),
        Question("Who developed Java?", listOf("Sun Microsystems", "Microsoft", "Oracle", "Google"), "Sun Microsystems"),
        Question("What is the latest version of Java?", listOf("8", "9", "10", "11"), "11"),
        Question("Which of these is a Java framework?", listOf("Spring", "React", "Vue", "Flutter"), "Spring"),
        Question("What is the JVM?", listOf("Java Virtual Machine", "Java Visual Machine", "Java Verification Mechanism", "Java Version Management"), "Java Virtual Machine")
    )

    val historyQuestions = listOf(
        Question("Who was the first President of the United States?", listOf("George Washington", "Abraham Lincoln", "Thomas Jefferson", "John Adams"), "George Washington"),
        Question("When did World War I begin?", listOf("1914", "1915", "1916", "1917"), "1914"),
        Question("Who discovered America?", listOf("Christopher Columbus", "Marco Polo", "Vasco da Gama", "Ferdinand Magellan"), "Christopher Columbus"),
        Question("In which year did the Titanic sink?", listOf("1912", "1905", "1920", "1931"), "1912"),
        Question("Who was the leader of Nazi Germany?", listOf("Adolf Hitler", "Joseph Stalin", "Benito Mussolini", "Winston Churchill"), "Adolf Hitler")
    )

    fun getQuestions(category: String): List<Question> {
        return when (category) {

            "إختبرثقافتك" -> Game1

            else -> emptyList()
        }
    }
}
