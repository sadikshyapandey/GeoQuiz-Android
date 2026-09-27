package com.example.geoquiz

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.activity.ComponentActivity

class MainActivity : ComponentActivity() {

    private var currentIndex = 0
    private var score = 0

    private val questions = arrayOf(
        "The capital of France is Paris.",
        "The Pacific Ocean is larger than the Atlantic Ocean.",
        "Australia is both a country and a continent.",
        "The Nile River is in South America.",
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

    private var answered = BooleanArray(questions.size)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        currentIndex = savedInstanceState?.getInt("currentIndex") ?: 0
        score = savedInstanceState?.getInt("score") ?: 0
        answered = savedInstanceState?.getBooleanArray("answered")
            ?: BooleanArray(questions.size)

        val questionText = findViewById<TextView>(R.id.question_text)
        val trueButton = findViewById<Button>(R.id.true_button)
        val falseButton = findViewById<Button>(R.id.false_button)
        val nextButton = findViewById<Button>(R.id.next_button)
        val cheatButton = findViewById<Button>(R.id.cheat_button)

        questionText.text = questions[currentIndex]

        trueButton.setOnClickListener {
            checkAnswer(true)
        }

        falseButton.setOnClickListener {
            checkAnswer(false)
        }

        nextButton.setOnClickListener {
            if (currentIndex < questions.size - 1) {
                currentIndex++
                questionText.text = questions[currentIndex]
            } else {
                questionText.text =
                    "Quiz Complete!\n\nScore: $score / ${questions.size}"

                trueButton.isEnabled = false
                falseButton.isEnabled = false
                nextButton.isEnabled = false
            }
        }

        cheatButton.setOnClickListener {
            val intent = Intent(this, CheatActivity::class.java)

            intent.putExtra(
                "answer",
                answers[currentIndex]
            )

            startActivity(intent)
        }
    }

    private fun checkAnswer(userAnswer: Boolean) {

        if (answered[currentIndex]) {
            Toast.makeText(
                this,
                "You already answered this question.",
                Toast.LENGTH_SHORT
            ).show()
            return
        }

        answered[currentIndex] = true

        if (userAnswer == answers[currentIndex]) {
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
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)

        outState.putInt("currentIndex", currentIndex)
        outState.putInt("score", score)
        outState.putBooleanArray("answered", answered)
    }
}