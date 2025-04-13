package com.example.calculator

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private lateinit var resultText: TextView
    private var currentNumber = ""
    private var firstNumber = 0
    private var operator = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        resultText = findViewById(R.id.tvResult)

        // Gán sự kiện cho các nút số
        val numberIds = listOf(
            R.id.btn0, R.id.btn1, R.id.btn2, R.id.btn3, R.id.btn4,
            R.id.btn5, R.id.btn6, R.id.btn7, R.id.btn8, R.id.btn9
        )

        for (id in numberIds) {
            findViewById<Button>(id).setOnClickListener {
                val number = (it as Button).text.toString()
                currentNumber += number
                resultText.text = currentNumber
            }
        }

        // Các nút toán tử
        findViewById<Button>(R.id.btnAdd).setOnClickListener { handleOperator("+") }
        findViewById<Button>(R.id.btnSub).setOnClickListener { handleOperator("-") }
        findViewById<Button>(R.id.btnMul).setOnClickListener { handleOperator("*") }
        findViewById<Button>(R.id.btnDiv).setOnClickListener { handleOperator("/") }

        // Nút "="
        findViewById<Button>(R.id.btnEqual).setOnClickListener {
            calculateResult()
        }

        // Nút C - Clear tất cả
        findViewById<Button>(R.id.btnC).setOnClickListener {
            currentNumber = ""
            firstNumber = 0
            operator = ""
            resultText.text = "0"
        }

        // Nút CE - Clear chỉ số đang nhập
        findViewById<Button>(R.id.btnCE).setOnClickListener {
            currentNumber = ""
            resultText.text = "0"
        }

        // Nút BS - Xoá 1 ký tự
        findViewById<Button>(R.id.btnBS).setOnClickListener {
            if (currentNumber.isNotEmpty()) {
                currentNumber = currentNumber.dropLast(1)
                resultText.text = if (currentNumber.isEmpty()) "0" else currentNumber
            }
        }

        // Nút +/- đổi dấu
        findViewById<Button>(R.id.btnNeg).setOnClickListener {
            if (currentNumber.isNotEmpty()) {
                currentNumber = if (currentNumber.startsWith("-"))
                    currentNumber.substring(1)
                else
                    "-$currentNumber"
                resultText.text = currentNumber
            }
        }
    }

    private fun handleOperator(op: String) {
        if (currentNumber.isNotEmpty()) {
            firstNumber = currentNumber.toInt()
            operator = op
            currentNumber = ""
        }
    }

    private fun calculateResult() {
        if (currentNumber.isNotEmpty() && operator.isNotEmpty()) {
            val secondNumber = currentNumber.toInt()
            val result = when (operator) {
                "+" -> firstNumber + secondNumber
                "-" -> firstNumber - secondNumber
                "*" -> firstNumber * secondNumber
                "/" -> if (secondNumber != 0) firstNumber / secondNumber else "Error"
                else -> "Error"
            }
            resultText.text = result.toString()
            currentNumber = result.toString()
            operator = ""
        }
    }
}
