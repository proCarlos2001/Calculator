package com.example.calculator

import android.annotation.SuppressLint
import android.os.Bundle
import android.os.Vibrator
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import java.text.DecimalFormat
import java.text.DecimalFormatSymbols
import java.util.*

class MainActivity : AppCompatActivity() {

    var firstNum: Double = 0.0
    var operation: String = ""
    lateinit var vibrator: Vibrator
    private val decimalFormat: DecimalFormat = DecimalFormat("#,###.########", DecimalFormatSymbols(Locale.getDefault()))

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        vibrator = getSystemService(VIBRATOR_SERVICE) as Vibrator
        decimalFormat.isGroupingUsed = true

        val num0: Button = findViewById(R.id.num0)
        val num1: Button = findViewById(R.id.num1)
        val num2: Button = findViewById(R.id.num2)
        val num3: Button = findViewById(R.id.num3)
        val num4: Button = findViewById(R.id.num4)
        val num5: Button = findViewById(R.id.num5)
        val num6: Button = findViewById(R.id.num6)
        val num7: Button = findViewById(R.id.num7)
        val num8: Button = findViewById(R.id.num8)
        val num9: Button = findViewById(R.id.num9)

        val on: Button = findViewById(R.id.on)
        val off: Button = findViewById(R.id.off)
        val ac: Button = findViewById(R.id.ac)
        val del: Button = findViewById(R.id.del)
        val div: Button = findViewById(R.id.div)
        val multi: Button = findViewById(R.id.multi)
        val resta: Button = findViewById(R.id.resta)
        val igual: Button = findViewById(R.id.igual)
        val suma: Button = findViewById(R.id.suma)
        val point: Button = findViewById(R.id.point)

        val screen: TextView = findViewById(R.id.screen)

        ac.setOnClickListener {
            vibrator.vibrate(50)
            firstNum = 0.0
            screen.text = "0"
        }
        off.setOnClickListener {
            vibrator.vibrate(50)
            screen.visibility = View.GONE
        }
        on.setOnClickListener {
            vibrator.vibrate(50)
            screen.visibility = View.VISIBLE
            screen.text = "0"
        }
        val nums = arrayListOf<Button>(num0, num1, num2, num3, num4, num5, num6, num7, num8, num9)

        for (b in nums) {
            b.setOnClickListener {
                vibrator.vibrate(50)
                val currentText = screen.text.toString()
                val newText = if (currentText != "0") {
                    currentText + b.text
                } else {
                    b.text.toString()
                }
                screen.text = formatNumber(newText)
            }
        }
        val opers = arrayListOf<Button>(div, multi, suma, resta)

        for (b in opers) {
            b.setOnClickListener {
                vibrator.vibrate(50)
                firstNum = parseScreenText(screen.text.toString())
                operation = b.text.toString()
                screen.text = "0"
            }
        }
        del.setOnClickListener {
            vibrator.vibrate(50)
            val num = screen.text.toString()
            when {
                num.length > 1 -> screen.text = formatNumber(num.substring(0, num.length - 1))
                num.length == 1 && num != "0" -> screen.text = "0"
            }
        }
        point.setOnClickListener {
            vibrator.vibrate(50)
            if (!screen.text.toString().contains(".")) {
                screen.text = "${screen.text}."
            }
        }
        igual.setOnClickListener {
            vibrator.vibrate(50)
            val secondNum = parseScreenText(screen.text.toString())
            val result: Double = when (operation) {
                "/" -> firstNum / secondNum
                "X" -> firstNum * secondNum
                "+" -> firstNum + secondNum
                "-" -> firstNum - secondNum
                else -> firstNum + secondNum
            }
            val formattedResult = formatNumber(result.toString())
            screen.text = formattedResult
            firstNum = result
        }
    }
    private fun formatNumber(number: String): String {
        val parsedNumber = number.replace(",", "") // Elimina comas existentes
        val parsedDouble = parsedNumber.toDoubleOrNull() ?: return number
        return decimalFormat.format(parsedDouble)
    }
    private fun parseScreenText(text: String): Double {
        val parsedNumber = text.replace(",", "")
        return parsedNumber.toDoubleOrNull() ?: 0.0
    }
}