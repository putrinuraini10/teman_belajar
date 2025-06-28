package com.example.temanbelajar

data class Question(
    val questionText: String,
    val options: List<String>,
    val correctAnswerIndex: Int,
    val imageResId: Int? = null,
    val audioResId: Int? = null
)
