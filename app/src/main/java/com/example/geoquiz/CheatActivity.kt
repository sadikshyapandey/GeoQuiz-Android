package com.example.geoquiz

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.activity.ComponentActivity

class CheatActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_cheat)

        val answerText =
            findViewById<TextView>(R.id.answerText)

        val backButton =
            findViewById<Button>(R.id.backButton)

        val answer =
            intent.getBooleanExtra("answer", false)

        if (answer) {

            answerText.text = "The answer is TRUE"

        } else {

            answerText.text = "The answer is FALSE"
        }

        backButton.setOnClickListener {
            finish()
        }
    }
}
