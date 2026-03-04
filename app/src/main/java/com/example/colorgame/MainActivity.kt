package com.example.colorgame

import android.os.Bundle
import android.widget.TextView
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.appcompat.app.AlertDialog

class MainActivity : AppCompatActivity() {

    private lateinit var squares: List<TextView>
    private var level = 1

    private val myRed = Color.parseColor("#E53935")
    private val myYellow = Color.parseColor("#FDD835")
    private val myGreen = Color.parseColor("#43A047")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.mainLayout)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        squares = listOf(
            findViewById(R.id.square1),
            findViewById(R.id.square2),
            findViewById(R.id.square3),
            findViewById(R.id.square4),
            findViewById(R.id.square5),
            findViewById(R.id.square6),
            findViewById(R.id.square7),
            findViewById(R.id.square8),
            findViewById(R.id.square9),
            findViewById(R.id.square10),
            findViewById(R.id.square11),
            findViewById(R.id.square12),
            findViewById(R.id.square13),
            findViewById(R.id.square14),
            findViewById(R.id.square15)
        )
    }
    private fun randomColor(): Int {
        val colors = listOf(myRed, myYellow, myGreen)
        return colors.random()
    }
    private fun restartGame() {
        squares.forEach { cell ->
            cell.setBackgroundColor(randomColor())
        }
        restartGame()
    }
}