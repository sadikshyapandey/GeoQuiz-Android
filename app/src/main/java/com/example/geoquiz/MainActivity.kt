package com.example.geoquiz

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private lateinit var questionText: TextView
    private lateinit var scoreText: TextView
    private lateinit var progressText: TextView

    private lateinit var trueButton: Button
    private lateinit var falseButton: Button
    private lateinit var nextButton: Button
    private lateinit var cheatButton: Button

    private val questions = arrayOf(
        "The capital of France is Paris.",
        "The Pacific Ocean is the largest ocean on Earth.",
        "Australia is both a country and a continent.",
        "The Nile River is located in South America.",
        "Mount Everest is the tallest mountain above sea level.",
        "Canada is south of the United States."
    )

    private val answers = booleanArrayOf(
        true,
        true,
        true,
        false,
        true,
        false
    )

    private var currentQuestionIndex = 0
    private var score = 0
    private var answered = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)

        questionText = findViewById(R.id.question_text)
        scoreText = findViewById(R.id.score_text)
        progressText = findViewById(R.id.progress_text)

        trueButton = findViewById(R.id.true_button)
        falseButton = findViewById(R.id.false_button)
        nextButton = findViewById(R.id.next_button)
        cheatButton = findViewById(R.id.cheat_button)

        // Restore state after rotation
        if (savedInstanceState != null) {
            currentQuestionIndex =
                savedInstanceState.getInt("current_question", 0)

            score =
                savedInstanceState.getInt("score", 0)

            answered =
                savedInstanceState.getBoolean("answered", false)
        }

        updateQuestion()

        trueButton.setOnClickListener {
            checkAnswer(true)
        }

        falseButton.setOnClickListener {
            checkAnswer(false)
        }

        nextButton.setOnClickListener {
            moveToNextQuestion()
        }

        cheatButton.setOnClickListener {
            val intent = Intent(this, CheatActivity::class.java)

            intent.putExtra(
                "answer",
                answers[currentQuestionIndex]
            )

            startActivity(intent)
        }
    }

    private fun updateQuestion() {
        questionText.text = questions[currentQuestionIndex]

        progressText.text =
            "Question ${currentQuestionIndex + 1} of ${questions.size}"

        scoreText.text = "Score: $score"

        // Allow answering again for a new question
        trueButton.isEnabled = !answered
        falseButton.isEnabled = !answered

        if (currentQuestionIndex == questions.size - 1) {
            nextButton.text = "FINISH"
        } else {
            nextButton.text = "NEXT"
        }
    }

    private fun checkAnswer(userAnswer: Boolean) {

        if (answered) {
            return
        }

        answered = true

        if (userAnswer == answers[currentQuestionIndex]) {
            score++

            Toast.makeText(
                this,
                "Correct!",
                Toast.LENGTH_SHORT
            ).show()
        } else {
            Toast.makeText(
                this,
                "Incorrect!",
                Toast.LENGTH_SHORT
            ).show()
        }

        scoreText.text = "Score: $score"

        trueButton.isEnabled = false
        falseButton.isEnabled = false
    }

    private fun moveToNextQuestion() {

        if (currentQuestionIndex < questions.size - 1) {
            currentQuestionIndex++
            answered = false
            updateQuestion()
        } else {

            Toast.makeText(
                this,
                "Quiz complete! Final score: $score/${questions.size}",
                Toast.LENGTH_LONG
            ).show()
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)

        outState.putInt(
            "current_question",
            currentQuestionIndex
        )

        outState.putInt(
            "score",
            score
        )

        outState.putBoolean(
            "answered",
            answered
        )
    }
}
