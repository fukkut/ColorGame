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

        squares.forEach { cell ->
            cell.setOnClickListener {
                changeColor(cell)
            }
        }
    }
    private fun changeColor(cell: TextView) {
        val currentColor = (cell.background as ColorDrawable).color

        val nextColor = when (currentColor) {
            myRed -> myYellow
            myYellow -> myGreen
            else -> myRed
        }
        cell.setBackgroundColor(nextColor)
    }
    private fun checkLevel1(): Boolean {
        val firstColor = (squares[0].background as ColorDrawable).color
        return squares.all {
            (it.background as ColorDrawable).color == firstColor
        }
    }
    private fun checkLevel2(): Boolean {
        for (i in squares.indices) {
            val column = i % 3
            val color = (squares[i].background as ColorDrawable).color
            when(column) {
                0 -> if (color != myYellow) return false
                1 -> if (color != myRed) return false
                2 -> if (color != myGreen) return false
            }
        }
        return true
    }
    private fun checkLevel3(): Boolean {
        for (row in 0 until 5) {
            var redCount = 0
            for (col in 0 until 3) {
                val index = row * 3 + col
                val color = (squares[index].background as ColorDrawable).color
                if (color == myRed) {
                    redCount++
                }
            }
            if (redCount != 1) return false
        }
        return true
    }
    private fun checkLevel4(): Boolean {
        for (i in squares.indices) {
            val currentColor = (squares[i].background as ColorDrawable).color
            if (i % 3 != 2) {
                val rightColor = (squares[i + 1].background as ColorDrawable).color
                if (currentColor == rightColor) return false
            }
            if (i < 12) {
                val bottomColor = (squares[i + 3].background as ColorDrawable).color
                if (currentColor == bottomColor) return false
            }
        }
        return true
    }
    private fun checkWin() {
        val win = when(level) {
            1 -> checkLevel1()
            2 -> checkLevel2()
            3 -> checkLevel3()
            4 -> checkLevel4()
            else -> false
        }
        if (win) {
            showLevelDialog()
        }
        squares.forEach { cell ->
            cell.setOnClickListener {
                changeColor(cell)
                checkWin()
            }
        }
    }
    private fun showLevelDialog() {
        AlertDialog.Builder(this)
            .setTitle("Рівень $level пройдено!")
            .setMessage("Хороша робота! Оберіть дію:")
            .setPositiveButton("Наступний рівень") { _, _ ->
                if (level < 4) {
                    level++
                    restartGame()
                }
            }
            .setNegativeButton("Спочатку") { _, _ ->
                level = 1
                restartGame()
            }
            .setNeutralButton("Вийти") { _, _ ->
                finish()
            }
            .show()
    }
}